package com.comment.insight.connector.service;

import com.comment.insight.connector.dto.PlatformCommentRequest;
import com.comment.insight.connector.dto.PlatformCommentResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ConnectorService {

    private final RestClient restClient;

    public ConnectorService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    // ================================
    // Fetch Comments (existing)
    // ================================
    public PlatformCommentResponse fetchComments(PlatformCommentRequest request) {

        if ("YOUTUBE".equalsIgnoreCase(request.getSource())) {
            return restClient.post()
                    .uri("http://youtube-connector-service/api/youtube/v1/comments")
                    .body(new RequestURL(request.getUrl()))
                    .retrieve()
                    .body(PlatformCommentResponse.class);
        }

        throw new IllegalArgumentException("Currently only YOUTUBE source is supported");
    }

    // ================================
    // NEW: Health Check
    // ================================
    public String checkYoutubeHealth() {

        return restClient.get()
                .uri("http://youtube-connector-service/api/youtube/v1/health")
                .retrieve()
                .body(String.class);
    }

    private record RequestURL(String url) {
    }
}
