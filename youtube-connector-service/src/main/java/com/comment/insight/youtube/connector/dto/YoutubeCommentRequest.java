package com.comment.insight.youtube.connector.dto;

import jakarta.validation.constraints.NotBlank;

public class YoutubeCommentRequest {

    @NotBlank(message = "videoUrl is required")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setVideoUrl(String url) {
        this.url = url;
    }
}