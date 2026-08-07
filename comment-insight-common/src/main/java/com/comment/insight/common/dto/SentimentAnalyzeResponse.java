package com.comment.insight.common.dto;

public class SentimentAnalyzeResponse {

    private String source;
    private String sourceId;
    private String sourceUrl;
    private int analyzedComments;
    private CategoryBreakdown categories;

    public SentimentAnalyzeResponse() {
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public int getAnalyzedComments() {
        return analyzedComments;
    }

    public void setAnalyzedComments(int analyzedComments) {
        this.analyzedComments = analyzedComments;
    }

    public CategoryBreakdown getCategories() {
        return categories;
    }

    public void setCategories(CategoryBreakdown categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "SentimentAnalyzeResponse{" +
                "source='" + source + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", analyzedComments=" + analyzedComments +
                ", categories=" + categories +
                '}';
    }
}
