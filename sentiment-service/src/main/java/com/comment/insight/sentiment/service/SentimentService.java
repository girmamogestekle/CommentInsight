package com.comment.insight.sentiment.service;

import com.comment.insight.common.dto.CommentAnalyzeRequest;
import com.comment.insight.common.dto.SentimentAnalyzeResponse;
import com.comment.insight.common.dto.PlatformCommentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class SentimentService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public SentimentService(ChatClient.Builder chatClientBuilder, ObjectMapper objectMapper) {
        this.chatClient = chatClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    public SentimentAnalyzeResponse analyze(CommentAnalyzeRequest request) {
        if (request.getComments() == null || request.getComments().isEmpty()) {
            throw new IllegalArgumentException("comments are required for sentiment analysis");
        }

        String commentsText = request.getComments()
                .stream()
                .map(PlatformCommentDto::getText)
                .filter(text -> text != null && !text.isBlank())
                .collect(Collectors.joining("\n"));

        String prompt = """
                You are a professional AI sentiment analysis engine. Your task is to analyze the following comments and return ONLY valid JSON.
                
                                Analysis Requirements:
                                - Analyze each comment individually and classify it as positive, negative, or neutral
                                - Count all comments in each category
                                - Provide clear, concise summaries for each sentiment category
                                - Write an overall audience reaction summary
                                - Provide a recommendation on whether the video is worth watching with reasoning
                                - Summarize the basic content/topic of the video based on comment analysis
                
                                Required JSON format:
                                {
                                  "analyzedComments": number,
                                  "positive": {
                                    "count": number,
                                    "summary": "summary of positive comments"
                                  },
                                  "negative": {
                                    "count": number,
                                    "summary": "summary of negative comments"
                                  },
                                  "neutral": {
                                    "count": number,
                                    "summary": "summary of neutral comments"
                                  },
                                  "overallSummary": "overall audience reaction summary",
                                  "recommendation": "is this video worth watching and why?",
                                  "videoContentSummary": "what is the basic content/topic of the video based on comments?"
                                }
                
                                Rules:
                                - Count every comment as positive, negative, or neutral
                                - Keep summaries clear, professional, and focused
                                - Do not include markdown formatting in any response
                                - Do not include any explanation or text outside the JSON structure
                                - Return only valid JSON that can be parsed without errors
                
                                Comments to analyze:
                                %s
                """.formatted(request.getComments().size(), commentsText);

        String aiResponse = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        try {
            SentimentAnalyzeResponse response =
                    objectMapper.readValue(aiResponse, SentimentAnalyzeResponse.class);

            response.setSource(request.getSource());
            response.setSourceId(request.getSourceId());
            response.setSourceUrl(request.getSourceUrl());
            response.setAnalyzedComments(request.getComments().size());

            return response;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to parse AI sentiment response: " + ex.getMessage());
        }
    }
}
