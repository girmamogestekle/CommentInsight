package com.comment.insight.youtube.connector.dto;

public class YoutubeCommentDto {

    private String author;
    private String text;
    private Integer likeCount;
    private String publishedAt;

    public YoutubeCommentDto() {
    }

    public YoutubeCommentDto(String author, String text, Integer likeCount, String publishedAt) {
        this.author = author;
        this.text = text;
        this.likeCount = likeCount;
        this.publishedAt = publishedAt;
    }

    public String getAuthor() {
        return author;
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

    public void setAuthor(String author) {
        this.author = author;
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
}
