package com.comment.insight.common.dto;

import jakarta.validation.constraints.NotBlank;

public class RequestUrlDto {

    @NotBlank(message = "url is required")
    private String url;

    public RequestUrlDto() {
    }

    public RequestUrlDto(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getYouTubeUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
