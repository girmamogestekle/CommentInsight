package com.comment.insight.common.dto;

import java.util.List;

public class PlatformCommentPageResponse {

    private String source;
    private String sourceId;
    private String sourceUrl;
    private int totalComments;
    private int fetchedCount;
    private String nextPageToken;
    private boolean hasNextPage;
    private List<PlatformCommentDto> comments;

    public PlatformCommentPageResponse() {
    }

    public PlatformCommentPageResponse(String source,
                                   String sourceId,
                                   String sourceUrl,
                                   int totalComments,
                                   String nextPageToken,
                                   List<PlatformCommentDto> comments) {
        this.source = source;
        this.sourceId = sourceId;
        this.sourceUrl = sourceUrl;
        this.totalComments = totalComments;
        this.nextPageToken = nextPageToken;
        this.hasNextPage = nextPageToken != null && !nextPageToken.isBlank();
        this.comments = comments;
        this.fetchedCount = comments == null ? 0 : comments.size();
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

    public int getFetchedCount() {
        return fetchedCount;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
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

    public void setTotalComments(int totalComments) {
        this.totalComments = totalComments;
    }

    public void setFetchedCount(int fetchedCount) {
        this.fetchedCount = fetchedCount;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
        this.hasNextPage = nextPageToken != null && !nextPageToken.isBlank();
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public void setComments(List<PlatformCommentDto> comments) {
        this.comments = comments;
        this.fetchedCount = comments == null ? 0 : comments.size();
    }
}
