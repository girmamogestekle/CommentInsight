package com.comment.insight.common.dto;

public class SentimentAnalyzeResponse {

    private String source;
    private String sourceId;
    private String sourceUrl;
    private int analyzedComments;

    private SentimentSummary positive;
    private SentimentSummary negative;
    private SentimentSummary neutral;

    private String overallSummary;
    private String recommendation;
    private String videoContentSummary;

    public SentimentAnalyzeResponse() {
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

    public int getAnalyzedComments() {
        return analyzedComments;
    }

    public SentimentSummary getPositive() {
        return positive;
    }

    public SentimentSummary getNegative() {
        return negative;
    }

    public SentimentSummary getNeutral() {
        return neutral;
    }

    public String getOverallSummary() {
        return overallSummary;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public String getVideoContentSummary() {
        return videoContentSummary;
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

    public void setAnalyzedComments(int analyzedComments) {
        this.analyzedComments = analyzedComments;
    }

    public void setPositive(SentimentSummary positive) {
        this.positive = positive;
    }

    public void setNegative(SentimentSummary negative) {
        this.negative = negative;
    }

    public void setNeutral(SentimentSummary neutral) {
        this.neutral = neutral;
    }

    public void setOverallSummary(String overallSummary) {
        this.overallSummary = overallSummary;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public void setVideoContentSummary(String videoContentSummary) {
        this.videoContentSummary = videoContentSummary;
    }

    @Override
    public String toString() {
        return "SentimentAnalyzeResponse{" +
                "source='" + source + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", analyzedComments=" + analyzedComments +
                ", positive=" + positive +
                ", negative=" + negative +
                ", neutral=" + neutral +
                ", overallSummary='" + overallSummary + '\'' +
                ", recommendation='" + recommendation + '\'' +
                ", videoContentSummary='" + videoContentSummary + '\'' +
                '}';
    }
}