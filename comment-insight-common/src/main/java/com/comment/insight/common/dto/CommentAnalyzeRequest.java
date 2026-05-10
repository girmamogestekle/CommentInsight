package com.comment.insight.common.dto;

import java.util.List;

public class CommentAnalyzeRequest {

    private String source;
    private String sourceId;
    private String sourceUrl;
    private List<PlatformCommentDto> comments;

    public CommentAnalyzeRequest() {
    }

    public CommentAnalyzeRequest(String source, String sourceId, String sourceUrl, List<PlatformCommentDto> comments) {
        this.source = source;
        this.sourceId = sourceId;
        this.sourceUrl = sourceUrl;
        this.comments = comments;
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

    public List<PlatformCommentDto> getComments() {
        return comments;
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

    public void setComments(List<PlatformCommentDto> comments) {
        this.comments = comments;
    }
}