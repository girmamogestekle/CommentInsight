package com.comment.insight.connector.dto;

import jakarta.validation.constraints.NotBlank;

public class CommentFetchPageRequest {

    @NotBlank(message = "source is required")
    private String source;

    @NotBlank(message = "url is required")
    private String url;

    private Integer pageSize;
    private String pageToken;

    public String getSource() {
        return source;
    }

    public String getUrl() {
        return url;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public String getPageToken() {
        return pageToken;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageToken(String pageToken) {
        this.pageToken = pageToken;
    }

}
