package com.comment.insight.connector.dto;

import java.util.Map;

public class PlatformCommentDto {

    private String commentId;
    private String authorName;
    private String text;
    private Integer likeCount;
    private String publishedAt;

    // Extra fields for YouTube, Reddit, Amazon, etc.
    private Map<String, Object> metadata;

    public PlatformCommentDto() {
    }

    public PlatformCommentDto(String commentId, String authorName, String text,
                              Integer likeCount, String publishedAt,
                              Map<String, Object> metadata) {
        this.commentId = commentId;
        this.authorName = authorName;
        this.text = text;
        this.likeCount = likeCount;
        this.publishedAt = publishedAt;
        this.metadata = metadata;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getText() {
        return text;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}
