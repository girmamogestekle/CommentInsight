package com.comment.insight.common.dto;

import java.util.List;

public class CategoryBreakdown {

    private List<CategoryValueStat> sentiment;
    private List<CategoryValueStat> emotions;
    private List<CategoryValueStat> toxicity;
    private List<CategoryValueStat> intent;
    private List<CategoryValueStat> stance;
    private List<CategoryValueStat> topics;
    private List<CategoryValueStat> threatViolence;
    private List<CategoryValueStat> personalAttack;
    private List<CategoryValueStat> sarcasm;
    private List<CategoryValueStat> constructiveness;

    public CategoryBreakdown() {
    }

    public List<CategoryValueStat> getSentiment() {
        return sentiment;
    }

    public void setSentiment(List<CategoryValueStat> sentiment) {
        this.sentiment = sentiment;
    }

    public List<CategoryValueStat> getEmotions() {
        return emotions;
    }

    public void setEmotions(List<CategoryValueStat> emotions) {
        this.emotions = emotions;
    }

    public List<CategoryValueStat> getToxicity() {
        return toxicity;
    }

    public void setToxicity(List<CategoryValueStat> toxicity) {
        this.toxicity = toxicity;
    }

    public List<CategoryValueStat> getIntent() {
        return intent;
    }

    public void setIntent(List<CategoryValueStat> intent) {
        this.intent = intent;
    }

    public List<CategoryValueStat> getStance() {
        return stance;
    }

    public void setStance(List<CategoryValueStat> stance) {
        this.stance = stance;
    }

    public List<CategoryValueStat> getTopics() {
        return topics;
    }

    public void setTopics(List<CategoryValueStat> topics) {
        this.topics = topics;
    }

    public List<CategoryValueStat> getThreatViolence() {
        return threatViolence;
    }

    public void setThreatViolence(List<CategoryValueStat> threatViolence) {
        this.threatViolence = threatViolence;
    }

    public List<CategoryValueStat> getPersonalAttack() {
        return personalAttack;
    }

    public void setPersonalAttack(List<CategoryValueStat> personalAttack) {
        this.personalAttack = personalAttack;
    }

    public List<CategoryValueStat> getSarcasm() {
        return sarcasm;
    }

    public void setSarcasm(List<CategoryValueStat> sarcasm) {
        this.sarcasm = sarcasm;
    }

    public List<CategoryValueStat> getConstructiveness() {
        return constructiveness;
    }

    public void setConstructiveness(List<CategoryValueStat> constructiveness) {
        this.constructiveness = constructiveness;
    }
}
