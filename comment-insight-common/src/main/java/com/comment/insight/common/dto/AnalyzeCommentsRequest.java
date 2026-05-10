package com.comment.insight.common.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class AnalyzeCommentsRequest {

    @NotBlank(message = "videoUrl is required")
    private String videoUrl;

    @Min(value = 1, message = "comments must be greater than 0")
    private int comments = 40;

    public AnalyzeCommentsRequest() {
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public int getComments() {
        return comments;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}