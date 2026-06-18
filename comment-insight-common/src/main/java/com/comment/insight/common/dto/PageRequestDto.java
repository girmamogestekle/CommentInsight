package com.comment.insight.common.dto;

public class PageRequestDto {

    private String url;
    private Integer pageSize;
    private String pageToken;

    public PageRequestDto() {
    }

    public PageRequestDto(String url) {
        this.url = url;
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
