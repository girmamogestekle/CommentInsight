package com.comment.insight.connector.service;

import com.comment.insight.common.dto.PlatformCommentResponse;
import com.comment.insight.common.dto.RequestUrlDto;
import com.comment.insight.common.enums.SourceType;
import com.comment.insight.common.exception.ConnectorCommunicationException;
import com.comment.insight.common.exception.UnsupportedSourceException;
import com.comment.insight.connector.dto.PlatformCommentRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
public class ConnectorService {

    private final RestClient restClient;
    @Value("${connector.youtube-service-url}")
    private String youtubeConnectorServiceUrl;

    public ConnectorService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    // ================================
    // Fetch Comments (existing)
    // ================================
    public PlatformCommentResponse fetchComments(PlatformCommentRequest request) {

        if (SourceType.YOUTUBE.name().equalsIgnoreCase(request.getSource())) {
            try{
                return restClient.post()
                        .uri(youtubeConnectorServiceUrl.concat("/v1/comments"))
                        .body(new RequestUrlDto(request.getUrl()))
                        .retrieve()
                        .body(PlatformCommentResponse.class);
            } catch(RestClientException ex){
                throw new ConnectorCommunicationException(
                        "Failed to communicate with YouTube Connector Service: " + ex.getMessage()
                );
            }
        }

        throw new UnsupportedSourceException(
                "Unsupported source: " + request.getSource() + ". Currently only YOUTUBE is supported."
        );
    }

    // ================================
    // NEW: Health Check
    // ================================
    public String checkYoutubeHealth() {

        try {
            return restClient.get()
                    .uri(youtubeConnectorServiceUrl.concat("/v1/health"))
                    .retrieve()
                    .body(String.class);
        } catch (RestClientException ex) {
            throw new ConnectorCommunicationException(
                    "YouTube Connector Service is unavailable: " + ex.getMessage()
            );
        }
    }
}
