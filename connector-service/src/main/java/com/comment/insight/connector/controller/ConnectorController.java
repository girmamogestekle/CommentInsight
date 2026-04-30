package com.comment.insight.connector.controller;

import com.comment.insight.common.dto.PlatformCommentResponse;
import com.comment.insight.connector.dto.PlatformCommentRequest;
import com.comment.insight.connector.service.ConnectorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/connectors/v1")
public class ConnectorController {

    private final ConnectorService connectorService;

    public ConnectorController(ConnectorService connectorService) {
        this.connectorService = connectorService;
    }

    @PostMapping("/comments")
    public PlatformCommentResponse fetchComments(@Valid @RequestBody PlatformCommentRequest request) {
        return connectorService.fetchComments(request);
    }

    @GetMapping("/health/youtube")
    public String youtubeHealth() {
        return connectorService.checkYoutubeHealth();
    }

    @GetMapping("/health")
    public String health() {
        return "Connector Service is running";
    }
}
