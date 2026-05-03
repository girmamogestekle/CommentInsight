package com.comment.insight.api.gateway.controller;

import com.comment.insight.common.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/fallback")
public class FallbackController {

    @GetMapping("/connector-service")
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Mono<ErrorResponse> connectorServiceFallback() {
        return Mono.just(new ErrorResponse(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "Service Unavailable",
                "Connector Service is temporarily unavailable. Please try again later.",
                "/api/connectors"
        ));
    }

    @GetMapping("/youtube-connector-service")
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Mono<ErrorResponse> youtubeConnectorServiceFallback() {
        return Mono.just(new ErrorResponse(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "Service Unavailable",
                "YouTube Connector Service is temporarily unavailable. Please try again later.",
                "/api/youtube"
        ));
    }
}
