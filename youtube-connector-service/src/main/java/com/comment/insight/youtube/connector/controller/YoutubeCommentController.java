package com.comment.insight.youtube.connector.controller;

import com.comment.insight.youtube.connector.dto.YoutubeCommentRequest;
import com.comment.insight.youtube.connector.dto.YoutubeCommentResponse;
import com.comment.insight.youtube.connector.service.YoutubeCommentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/youtube/v1")
public class YoutubeCommentController {

    private final YoutubeCommentService youtubeCommentService;

    public YoutubeCommentController(YoutubeCommentService youtubeCommentService) {
        this.youtubeCommentService = youtubeCommentService;
    }

    @GetMapping("/health")
    public String health() {
        return "YouTube Connector Service is running";
    }

    @PostMapping("/comments")
    public YoutubeCommentResponse fetchComments(@Valid @RequestBody YoutubeCommentRequest request) {
        System.out.println("YoutubeCommentController.fetchComments()" + request.toString());
        return youtubeCommentService.fetchComments(request.getUrl());
    }
}
