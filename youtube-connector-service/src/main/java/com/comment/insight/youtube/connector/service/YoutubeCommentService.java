package com.comment.insight.youtube.connector.service;

import com.comment.insight.youtube.connector.dto.YoutubeCommentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class YoutubeCommentService {

    private final RestClient restClient;

    @Value("${youtube.api.key}")
    private String youtubeApiKey;

    public YoutubeCommentService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("https://www.googleapis.com/youtube/v3")
                .build();
    }

    public YoutubeCommentResponse fetchComments(String videoUrl) {

        String videoId = extractVideoId(videoUrl);

        int totalComments = getTotalCommentCount(videoId);

        return new YoutubeCommentResponse(
                "YOUTUBE",
                videoId,
                videoUrl,
                totalComments
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
        if (videoUrl.contains("watch?v=")) {
            return videoUrl.substring(videoUrl.indexOf("watch?v=") + 8).split("&")[0];
        }

        if (videoUrl.contains("youtu.be/")) {
            return videoUrl.substring(videoUrl.indexOf("youtu.be/") + 9).split("\\?")[0];
        }

        throw new IllegalArgumentException("Invalid YouTube video URL");
    }
}
