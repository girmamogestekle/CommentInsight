package com.comment.insight.common.dto;

public class PlatformCommentResponse {

    private String source;
    private String sourceId;
    private String sourceUrl;
    private int totalComments;

    public PlatformCommentResponse() {
    }

    public PlatformCommentResponse(String source, String sourceId, String sourceUrl, int totalComments) {
        this.source = source;
        this.sourceId = sourceId;
        this.sourceUrl = sourceUrl;
        this.totalComments = totalComments;
    }

    public String getSource() {
        return source;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public int getTotalComments() {
        return totalComments;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public void setTotalComments(int totalComments) {
        this.totalComments = totalComments;
    }
}
