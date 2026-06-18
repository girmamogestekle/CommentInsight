package com.comment.insight.connector.service;

import com.comment.insight.common.dto.AnalyzeCommentsRequest;
import com.comment.insight.common.dto.PlatformCommentPageResponse;
import com.comment.insight.common.dto.PageRequestDto;
import com.comment.insight.common.dto.SentimentAnalyzeResponse;
import com.comment.insight.common.exception.ConnectorCommunicationException;
import com.comment.insight.common.exception.UnsupportedSourceException;
import com.comment.insight.connector.dto.CommentFetchPageRequest;
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
    // Dispatcher - Fetch Comments
    // ================================
    public PlatformCommentPageResponse fetchCommentsPage(CommentFetchPageRequest request) {

        if (isYoutubeUrl(request.getUrl())) {
            return fetchYoutubeCommentsPage(request);
        }

        throw new UnsupportedSourceException(
                "Unsupported URL: " + request.getUrl() + ". Currently only YouTube URLs are supported."
        );
    }


    // ================================
    // Dispatcher - Analyze Comments
    // ================================
    public SentimentAnalyzeResponse analyzeComments(AnalyzeCommentsRequest  request) {
        if (isYoutubeUrl(request.getVideoUrl())) {
            return analyzeYoutubeComments(request);
        }

        throw new UnsupportedSourceException(
                "Unsupported URL: " + request.getVideoUrl() + ". Currently only YouTube URLs are supported."
        );
    }

    // ================================
    // YouTube - Comments
    // ================================
    private PlatformCommentPageResponse fetchYoutubeCommentsPage(CommentFetchPageRequest request) {

        try {
            PageRequestDto pageRequest = new PageRequestDto();
            pageRequest.setUrl(request.getUrl());
            pageRequest.setPageSize(request.getPageSize());
            pageRequest.setPageToken(request.getPageToken());

            return restClient.post()
                    .uri(youtubeConnectorServiceUrl.concat("/v1/comments/page"))
                    .body(pageRequest)
                    .retrieve()
                    .body(PlatformCommentPageResponse.class);
        } catch (RestClientException ex) {
            throw new ConnectorCommunicationException(
                    "Failed to communicate with YouTube Connector Service: " + ex.getMessage()
            );
        }
    }

    // ================================
    // Analyze - YouTube - Comments
    // ================================
    private SentimentAnalyzeResponse analyzeYoutubeComments(AnalyzeCommentsRequest  request) {
        try {
            return restClient.post()
                    .uri(youtubeConnectorServiceUrl.concat("/v1/analyze"))
                    .body(request)
                    .retrieve()
                    .body(SentimentAnalyzeResponse.class);
        } catch (RestClientException ex) {
            throw new ConnectorCommunicationException(
                    "Failed to communicate with YouTube Connector Service: " + ex.getMessage()
            );
        }
    }

    private boolean isYoutubeUrl(String url) {
        return url != null && (url.contains("youtube.com") || url.contains("youtu.be"));
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
