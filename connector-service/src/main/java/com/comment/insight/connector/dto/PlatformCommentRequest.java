package com.comment.insight.connector.dto;

import jakarta.validation.constraints.NotBlank;

public class PlatformCommentRequest {

    @NotBlank(message = "source is required")
    private String source;

    @NotBlank(message = "url is required")
    private String url;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
