package com.comment.insight.common.dto;

public class CategoryValueStat {

    private String value;
    private int count;
    private double percentage;

    public CategoryValueStat() {
    }

    public CategoryValueStat(String value, int count, double percentage) {
        this.value = value;
        this.count = count;
        this.percentage = percentage;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
