package com.comment.insight.api.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gateway/v1")
public class APIGatewayController {

    @GetMapping("/health")
    public String health() {
        return "API Gateway Service is running";
    }
}
