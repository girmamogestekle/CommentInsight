package com.comment.insight.connector.dto;

import jakarta.validation.constraints.NotBlank;

public class CommentFetchPageRequest {

    @NotBlank(message = "url is required")
    private String url;

    private Integer pageSize;
    private String pageToken;

    public String getUrl() {
        return url;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public String getPageToken() {
        return pageToken;
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
