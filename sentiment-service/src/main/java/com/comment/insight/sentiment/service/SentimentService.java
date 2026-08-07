package com.comment.insight.sentiment.service;

import com.comment.insight.common.dto.CommentAnalyzeRequest;
import com.comment.insight.common.dto.PlatformCommentDto;
import com.comment.insight.common.dto.SentimentAnalyzeResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SentimentService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;
    private final CategoryAggregationService categoryAggregationService;

    public SentimentService(
            ChatClient.Builder chatClientBuilder,
            ObjectMapper objectMapper,
            CategoryAggregationService categoryAggregationService
    ) {
        this.chatClient = chatClientBuilder.build();
        this.objectMapper = objectMapper;
        this.categoryAggregationService = categoryAggregationService;
    }

    public SentimentAnalyzeResponse analyze(CommentAnalyzeRequest request) {
        if (request.getComments() == null || request.getComments().isEmpty()) {
            throw new IllegalArgumentException("comments are required for sentiment analysis");
        }

        List<PlatformCommentDto> comments = request.getComments();
        String commentsText = buildNumberedComments(comments);

        String prompt = """
                You are a social media comment analysis system.

                Analyze EACH comment separately and classify it across multiple dimensions.

                Base your classification ONLY on the text of that comment. Do not assume facts, political affiliation, identity, motivation, or context that is not expressed or reasonably inferable from the text.

                Do not judge whether claims in the comment are factually true or false. Your task is to classify the language, emotion, intent, stance, topic, safety characteristics, and conversational quality.

                Use the following categories and allowed values for EVERY comment.

                1. SENTIMENT
                Allowed values: POSITIVE | NEGATIVE | NEUTRAL | MIXED

                2. EMOTION
                Allowed values (multiple allowed): ANGER | JOY | FEAR | SADNESS | DISGUST | SURPRISE | HOPE | FRUSTRATION | NONE

                3. TOXICITY
                Allowed values: NONE | LOW | MEDIUM | HIGH

                4. INTENT
                Allowed values (multiple allowed): SUPPORT | CRITICISM | QUESTION | COMPLAINT | AGREEMENT | DISAGREEMENT | CALL_TO_ACTION | INFORMATION | OTHER

                5. STANCE
                Allowed values: SUPPORTIVE | OPPOSED | NEUTRAL | UNCLEAR

                6. TOPIC
                Return short general topic labels (multiple allowed), e.g. POLITICS, VIOLENCE, PERSON, POLICY, MEDIA, GOVERNMENT, ELECTIONS, LAW, RELIGION, ECONOMY, TECHNOLOGY, HEALTH, ENTERTAINMENT, SPORTS, OTHER

                7. THREAT_VIOLENCE
                Allowed values: NONE | DISCUSSION | ADVOCACY | THREAT

                8. PERSONAL_ATTACK
                Allowed values: YES | NO

                9. SARCASM
                Allowed values: YES | NO | UNCERTAIN

                10. CONSTRUCTIVENESS
                Allowed values: CONSTRUCTIVE | MIXED | UNCONSTRUCTIVE

                IMPORTANT RULES:
                - Return one classification object per input comment, in the same order.
                - Copy commentId and commentText exactly from each input comment.
                - Do not skip comments.
                - Do not merge comments into one classification.
                - Return valid JSON only.
                - Do not include Markdown.
                - Do not include explanations outside the JSON.

                Comments to analyze:
                %s

                Return exactly this JSON structure:
                {
                  "results": [
                    {
                      "commentId": "string",
                      "commentText": "string",
                      "sentiment": "POSITIVE | NEGATIVE | NEUTRAL | MIXED",
                      "emotions": ["ANGER | JOY | FEAR | SADNESS | DISGUST | SURPRISE | HOPE | FRUSTRATION | NONE"],
                      "toxicity": "NONE | LOW | MEDIUM | HIGH",
                      "intent": ["SUPPORT | CRITICISM | QUESTION | COMPLAINT | AGREEMENT | DISAGREEMENT | CALL_TO_ACTION | INFORMATION | OTHER"],
                      "stance": "SUPPORTIVE | OPPOSED | NEUTRAL | UNCLEAR",
                      "topics": ["TOPIC"],
                      "threatViolence": "NONE | DISCUSSION | ADVOCACY | THREAT",
                      "personalAttack": "YES | NO",
                      "sarcasm": "YES | NO | UNCERTAIN",
                      "constructiveness": "CONSTRUCTIVE | MIXED | UNCONSTRUCTIVE"
                    }
                  ]
                }
                """.formatted(commentsText);

        String aiResponse = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        try {
            JsonNode root = objectMapper.readTree(stripMarkdownFence(aiResponse));
            JsonNode resultsNode = root.has("results") ? root.get("results") : root;

            List<ClassifiedComment> classified = objectMapper.convertValue(
                    resultsNode,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, ClassifiedComment.class)
            );

            SentimentAnalyzeResponse response = new SentimentAnalyzeResponse();
            response.setSource(request.getSource());
            response.setSourceId(request.getSourceId());
            response.setSourceUrl(request.getSourceUrl());
            response.setAnalyzedComments(comments.size());
            response.setCategories(categoryAggregationService.aggregate(classified));
            return response;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to parse AI sentiment response: " + ex.getMessage());
        }
    }

    private static String buildNumberedComments(List<PlatformCommentDto> comments) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < comments.size(); i++) {
            PlatformCommentDto comment = comments.get(i);
            builder.append("Comment ").append(i + 1).append('\n')
                    .append("commentId: ").append(nullToEmpty(comment.getCommentId())).append('\n')
                    .append("commentText: ").append(nullToEmpty(comment.getText())).append('\n')
                    .append("---\n");
        }
        return builder.toString();
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    private static String stripMarkdownFence(String content) {
        if (content == null) {
            return "";
        }
        String trimmed = content.trim();
        if (trimmed.startsWith("```")) {
            trimmed = trimmed.replaceFirst("^```(?:json)?\\s*", "");
            trimmed = trimmed.replaceFirst("\\s*```$", "");
        }
        return trimmed.trim();
    }
}
