package com.comment.insight.common.dto;

public class SentimentSummary {

    private int count;
    private String summary;

    public SentimentSummary() {
    }

    public SentimentSummary(int count, String summary) {
        this.count = count;
        this.summary = summary;
    }

    public int getCount() {
        return count;
    }

    public String getSummary() {
        return summary;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

}
