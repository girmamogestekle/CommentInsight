package com.comment.insight.sentiment.service;

import com.comment.insight.common.dto.CategoryBreakdown;
import com.comment.insight.common.dto.CategoryValueStat;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

@Component
public class CategoryAggregationService {

    private static final List<String> SENTIMENT_VALUES = List.of("POSITIVE", "NEGATIVE", "NEUTRAL", "MIXED");
    private static final List<String> EMOTION_VALUES = List.of(
            "ANGER", "JOY", "FEAR", "SADNESS", "DISGUST", "SURPRISE", "HOPE", "FRUSTRATION", "NONE"
    );
    private static final List<String> TOXICITY_VALUES = List.of("NONE", "LOW", "MEDIUM", "HIGH");
    private static final List<String> INTENT_VALUES = List.of(
            "SUPPORT", "CRITICISM", "QUESTION", "COMPLAINT", "AGREEMENT",
            "DISAGREEMENT", "CALL_TO_ACTION", "INFORMATION", "OTHER"
    );
    private static final List<String> STANCE_VALUES = List.of("SUPPORTIVE", "OPPOSED", "NEUTRAL", "UNCLEAR");
    private static final List<String> THREAT_VALUES = List.of("NONE", "DISCUSSION", "ADVOCACY", "THREAT");
    private static final List<String> PERSONAL_ATTACK_VALUES = List.of("YES", "NO");
    private static final List<String> SARCASM_VALUES = List.of("YES", "NO", "UNCERTAIN");
    private static final List<String> CONSTRUCTIVENESS_VALUES = List.of("CONSTRUCTIVE", "MIXED", "UNCONSTRUCTIVE");

    public CategoryBreakdown aggregate(List<ClassifiedComment> results) {
        int total = results == null ? 0 : results.size();
        CategoryBreakdown breakdown = new CategoryBreakdown();

        breakdown.setSentiment(aggregateSingle(results, total, SENTIMENT_VALUES, ClassifiedComment::getSentiment));
        breakdown.setEmotions(aggregateMulti(results, total, EMOTION_VALUES, ClassifiedComment::getEmotions));
        breakdown.setToxicity(aggregateSingle(results, total, TOXICITY_VALUES, ClassifiedComment::getToxicity));
        breakdown.setIntent(aggregateMulti(results, total, INTENT_VALUES, ClassifiedComment::getIntent));
        breakdown.setStance(aggregateSingle(results, total, STANCE_VALUES, ClassifiedComment::getStance));
        breakdown.setTopics(aggregateMulti(results, total, List.of(), ClassifiedComment::getTopics));
        breakdown.setThreatViolence(aggregateSingle(results, total, THREAT_VALUES, ClassifiedComment::getThreatViolence));
        breakdown.setPersonalAttack(aggregateSingle(results, total, PERSONAL_ATTACK_VALUES, ClassifiedComment::getPersonalAttack));
        breakdown.setSarcasm(aggregateSingle(results, total, SARCASM_VALUES, ClassifiedComment::getSarcasm));
        breakdown.setConstructiveness(aggregateSingle(results, total, CONSTRUCTIVENESS_VALUES, ClassifiedComment::getConstructiveness));

        return breakdown;
    }

    private static List<CategoryValueStat> aggregateSingle(
            List<ClassifiedComment> results,
            int total,
            List<String> orderedValues,
            Function<ClassifiedComment, String> extractor
    ) {
        Map<String, Integer> counts = seedCounts(orderedValues);

        if (results != null) {
            for (ClassifiedComment result : results) {
                String raw = extractor.apply(result);
                if (raw == null || raw.isBlank()) {
                    continue;
                }
                String key = normalize(raw);
                counts.put(key, counts.getOrDefault(key, 0) + 1);
            }
        }

        return toStats(counts, orderedValues, total);
    }

    private static List<CategoryValueStat> aggregateMulti(
            List<ClassifiedComment> results,
            int total,
            List<String> orderedValues,
            Function<ClassifiedComment, List<String>> extractor
    ) {
        Map<String, Integer> counts = seedCounts(orderedValues);

        if (results != null) {
            for (ClassifiedComment result : results) {
                List<String> values = extractor.apply(result);
                if (values == null || values.isEmpty()) {
                    continue;
                }
                for (String raw : values) {
                    if (raw == null || raw.isBlank()) {
                        continue;
                    }
                    String key = normalize(raw);
                    counts.put(key, counts.getOrDefault(key, 0) + 1);
                }
            }
        }

        return toStats(counts, orderedValues, total);
    }

    private static Map<String, Integer> seedCounts(List<String> orderedValues) {
        Map<String, Integer> counts = new LinkedHashMap<>();
        for (String value : orderedValues) {
            counts.put(value, 0);
        }
        return counts;
    }

    private static List<CategoryValueStat> toStats(
            Map<String, Integer> counts,
            List<String> preferredOrder,
            int total
    ) {
        List<String> order = new ArrayList<>(preferredOrder);
        for (String key : counts.keySet()) {
            if (!order.contains(key)) {
                order.add(key);
            }
        }

        List<CategoryValueStat> stats = new ArrayList<>();
        for (String value : order) {
            int count = counts.getOrDefault(value, 0);
            stats.add(new CategoryValueStat(value, count, percentage(count, total)));
        }
        return stats;
    }

    private static double percentage(int count, int total) {
        if (total <= 0) {
            return 0.0;
        }
        return BigDecimal.valueOf(count * 100.0 / total)
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private static String normalize(String value) {
        return value.trim().toUpperCase(Locale.ROOT).replace(' ', '_');
    }
}
