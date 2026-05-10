package com.comment.insight.sentiment.controller;

import com.comment.insight.common.dto.CommentAnalyzeRequest;
import com.comment.insight.common.dto.SentimentAnalyzeResponse;
import com.comment.insight.sentiment.service.SentimentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sentiment/v1")
public class SentimentController {

    private final SentimentService sentimentService;

    public SentimentController(SentimentService sentimentService) {
        this.sentimentService = sentimentService;
    }

    @GetMapping("/health")
    public String health() {
        return "Sentiment Service is running";
    }

    @PostMapping("/analyze")
    public SentimentAnalyzeResponse analyze(@Valid @RequestBody CommentAnalyzeRequest request) {
        return sentimentService.analyze(request);
    }
}
