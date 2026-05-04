package com.comment.insight.youtube.connector.controller;

import com.comment.insight.common.dto.PlatformCommentPageResponse;
import com.comment.insight.common.dto.PageRequestDto;
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

    @PostMapping("/comments/page")
    public PlatformCommentPageResponse fetchCommentsPage(@Valid @RequestBody PageRequestDto request) {
        return youtubeCommentService.fetchCommentsPage(request);
    }
}
