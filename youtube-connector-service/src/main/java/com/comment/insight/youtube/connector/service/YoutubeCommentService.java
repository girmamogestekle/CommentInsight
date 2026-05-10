package com.comment.insight.youtube.connector.service;

import com.comment.insight.common.dto.*;
import com.comment.insight.common.enums.SourceType;
import com.comment.insight.common.exception.InvalidUrlException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class YoutubeCommentService {

    private static final int YOUTUBE_MAX_PAGE_SIZE = 100;
    private final RestClient youtubeRestClient;
    private final String youtubeApiKey;
    private final RestClient loadBalancedRestClient;

    public YoutubeCommentService(RestClient.Builder restClientBuilder,
                                 @Qualifier("loadBalancedRestClientBuilder") RestClient.Builder loadBalancedRestClientBuilder,
                                 @Value("${youtube.api.base-url}") String youtubeApiBaseUrl,
                                 @Value("${youtube.api.key}") String youtubeApiKey) {
        this.youtubeRestClient = restClientBuilder
                .baseUrl(youtubeApiBaseUrl)
                .build();

        this.loadBalancedRestClient = loadBalancedRestClientBuilder.build();
        this.youtubeApiKey = youtubeApiKey;
    }

    public SentimentAnalyzeResponse analyzeYoutubeComments(
            String videoUrl,
            int requestedComments
    ) {
        String videoId = extractVideoId(videoUrl);

        int safeRequestedComments = Math.max(requestedComments, 1);

        List<PlatformCommentDto> comments = fetchRequestedComments(
                videoId,
                safeRequestedComments
        );

        CommentAnalyzeRequest analyzeRequest = new CommentAnalyzeRequest(
                SourceType.YOUTUBE.name(),
                videoId,
                videoUrl,
                comments
        );

        return loadBalancedRestClient.post()
                .uri("http://sentiment-service/api/sentiment/v1/analyze")
                .body(analyzeRequest)
                .retrieve()
                .body(SentimentAnalyzeResponse.class);
    }

    public PlatformCommentPageResponse fetchCommentsPage(PageRequestDto request) {

        String videoId = extractVideoId(request.getUrl());

        int pageSize = request.getPageSize() == null ? 20 : request.getPageSize();
        pageSize = Math.min(pageSize, 100);

        int totalComments = getTotalCommentCount(videoId);

        int finalPageSize = pageSize;
        Map response = youtubeRestClient.get()
                .uri(uriBuilder -> {
                    var builder = uriBuilder
                            .path("/commentThreads")
                            .queryParam("part", "snippet")
                            .queryParam("videoId", videoId)
                            .queryParam("maxResults", finalPageSize)
                            .queryParam("textFormat", "plainText")
                            .queryParam("key", youtubeApiKey);

                    if (request.getPageToken() != null && !request.getPageToken().isBlank()) {
                        builder.queryParam("pageToken", request.getPageToken());
                    }

                    return builder.build();
                })
                .retrieve()
                .body(Map.class);

        List<PlatformCommentDto> comments = parseYouTubeComments(response);

        String nextPageToken = response == null ? null : (String) response.get("nextPageToken");

        return new PlatformCommentPageResponse(
                SourceType.YOUTUBE.name(),
                videoId,
                request.getUrl(),
                totalComments,
                nextPageToken,
                comments
        );
    }

    private List<PlatformCommentDto> fetchRequestedComments(
            String videoId,
            int requestedComments
    ) {
        List<PlatformCommentDto> allComments = new ArrayList<>();

        String nextPageToken = null;

        while (allComments.size() < requestedComments) {
            int remaining = requestedComments - allComments.size();
            int pageSize = Math.min(remaining, YOUTUBE_MAX_PAGE_SIZE);

            PlatformCommentPageResponse pageResponse = fetchCommentsPageByVideoId(
                    videoId,
                    pageSize,
                    nextPageToken
            );

            if (pageResponse.getComments() == null || pageResponse.getComments().isEmpty()) {
                break;
            }

            allComments.addAll(pageResponse.getComments());
            nextPageToken = pageResponse.getNextPageToken();

            if (nextPageToken == null || nextPageToken.isBlank()) {
                break;
            }
        }

        if (allComments.size() > requestedComments) {
            return allComments.subList(0, requestedComments);
        }

        return allComments;
    }

    public PlatformCommentPageResponse fetchCommentsPageByVideoId(
            String videoId,
            int pageSize,
            String pageToken
    ) {
        int safePageSize = Math.min(Math.max(pageSize, 1), YOUTUBE_MAX_PAGE_SIZE);
        int totalComments = getTotalCommentCount(videoId);

        Map response = youtubeRestClient.get()
                .uri(uriBuilder -> {
                    var builder = uriBuilder
                            .path("/commentThreads")
                            .queryParam("part", "snippet")
                            .queryParam("videoId", videoId)
                            .queryParam("maxResults", safePageSize)
                            .queryParam("order", "time")
                            .queryParam("textFormat", "plainText")
                            .queryParam("key", youtubeApiKey);

                    if (pageToken != null && !pageToken.isBlank()) {
                        builder.queryParam("pageToken", pageToken);
                    }

                    return builder.build();
                })
                .retrieve()
                .body(Map.class);

        List<PlatformCommentDto> comments = parseYouTubeComments(response);
        String nextPageToken = response == null ? null : (String) response.get("nextPageToken");

        return new PlatformCommentPageResponse(
                SourceType.YOUTUBE.name(),
                videoId,
                "https://www.youtube.com/watch?v=" + videoId,
                totalComments,
                nextPageToken,
                comments
        );
    }

    private int getTotalCommentCount(String videoId) {
        Map response = youtubeRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/videos")
                        .queryParam("part", "statistics")
                        .queryParam("id", videoId)
                        .queryParam("key", youtubeApiKey)
                        .build())
                .retrieve()
                .body(Map.class);

        List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");

        if (items == null || items.isEmpty()) {
            return 0;
        }

        Map<String, Object> statistics = (Map<String, Object>) items.get(0).get("statistics");

        return Integer.parseInt((String) statistics.get("commentCount"));
    }

    private String extractVideoId(String videoUrl) {
        if (videoUrl == null || videoUrl.isBlank()) {
            throw new InvalidUrlException("YouTube video URL is required");
        }

        if (videoUrl.contains("watch?v=")) {
            return videoUrl.substring(videoUrl.indexOf("watch?v=") + 8).split("&")[0];
        }

        if (videoUrl.contains("youtu.be/")) {
            return videoUrl.substring(videoUrl.indexOf("youtu.be/") + 9).split("\\?")[0];
        }

        throw new InvalidUrlException("Invalid YouTube video URL");
    }

    private List<PlatformCommentDto> parseYouTubeComments(Map response) {

        List<PlatformCommentDto> comments = new ArrayList<>();

        if (response == null || response.get("items") == null) {
            return comments;
        }

        List<Map<String, Object>> items =
                (List<Map<String, Object>>) response.get("items");

        for (Map<String, Object> item : items) {
            String commentId = (String) item.get("id");

            Map<String, Object> snippet =
                    (Map<String, Object>) item.get("snippet");

            Map<String, Object> topLevelComment =
                    (Map<String, Object>) snippet.get("topLevelComment");

            Map<String, Object> commentSnippet =
                    (Map<String, Object>) topLevelComment.get("snippet");

            String authorName = (String) commentSnippet.get("authorDisplayName");
            String text = (String) commentSnippet.get("textDisplay");
            Integer likeCount = (Integer) commentSnippet.get("likeCount");
            String publishedAt = (String) commentSnippet.get("publishedAt");

            comments.add(new PlatformCommentDto(
                    commentId,
                    authorName,
                    text,
                    likeCount,
                    publishedAt,
                    Map.of("platform", "youtube")
            ));
        }

        return comments;
    }

}
