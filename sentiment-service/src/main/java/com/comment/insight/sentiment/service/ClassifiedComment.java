package com.comment.insight.sentiment.service;

import java.util.List;

/**
 * Internal AI parse model used only to aggregate category stats.
 * Not part of the public API response.
 */
class ClassifiedComment {

    private String commentId;
    private String commentText;
    private String sentiment;
    private List<String> emotions;
    private String toxicity;
    private List<String> intent;
    private String stance;
    private List<String> topics;
    private String threatViolence;
    private String personalAttack;
    private String sarcasm;
    private String constructiveness;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public List<String> getEmotions() {
        return emotions;
    }

    public void setEmotions(List<String> emotions) {
        this.emotions = emotions;
    }

    public String getToxicity() {
        return toxicity;
    }

    public void setToxicity(String toxicity) {
        this.toxicity = toxicity;
    }

    public List<String> getIntent() {
        return intent;
    }

    public void setIntent(List<String> intent) {
        this.intent = intent;
    }

    public String getStance() {
        return stance;
    }

    public void setStance(String stance) {
        this.stance = stance;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public String getThreatViolence() {
        return threatViolence;
    }

    public void setThreatViolence(String threatViolence) {
        this.threatViolence = threatViolence;
    }

    public String getPersonalAttack() {
        return personalAttack;
    }

    public void setPersonalAttack(String personalAttack) {
        this.personalAttack = personalAttack;
    }

    public String getSarcasm() {
        return sarcasm;
    }

    public void setSarcasm(String sarcasm) {
        this.sarcasm = sarcasm;
    }

    public String getConstructiveness() {
        return constructiveness;
    }

    public void setConstructiveness(String constructiveness) {
        this.constructiveness = constructiveness;
    }
}
