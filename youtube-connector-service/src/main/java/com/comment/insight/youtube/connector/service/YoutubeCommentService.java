package com.comment.insight.youtube.connector.service;

import com.comment.insight.common.dto.PageRequestDto;
import com.comment.insight.common.dto.PlatformCommentDto;
import com.comment.insight.common.dto.PlatformCommentPageResponse;
import com.comment.insight.common.enums.SourceType;
import com.comment.insight.common.exception.InvalidUrlException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class YoutubeCommentService {

    private final RestClient restClient;
    private final String youtubeApiKey;

    public YoutubeCommentService(RestClient.Builder restClientBuilder,
                                 @Value("${youtube.api.base-url}") String youtubeApiBaseUrl,
                                 @Value("${youtube.api.key}") String youtubeApiKey) {
        this.restClient = restClientBuilder
                .baseUrl(youtubeApiBaseUrl)
                .build();

        this.youtubeApiKey = youtubeApiKey;
    }

    public PlatformCommentPageResponse fetchCommentsPage(PageRequestDto request) {

        String videoId = extractVideoId(request.getUrl());

        int pageSize = request.getPageSize() == null ? 20 : request.getPageSize();
        pageSize = Math.min(pageSize, 100);

        int totalComments = getTotalCommentCount(videoId);

        int finalPageSize = pageSize;
        Map response = restClient.get()
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

        List<PlatformCommentDto> comments = parsePlatformComments(response);

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

    private int getTotalCommentCount(String videoId) {
        Map response = restClient.get()
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

    private List<PlatformCommentDto> parsePlatformComments(Map response) {

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
