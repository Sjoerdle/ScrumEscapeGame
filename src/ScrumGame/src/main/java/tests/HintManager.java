package hints;

import java.util.HashMap;
import java.util.Map;

public class HintManager {
    private HintProvider hintProvider;
    private Map<String, Integer> hintUsageCount;

    public HintManager(HintProvider hintProvider) {
        this.hintProvider = hintProvider;
        this.hintUsageCount = new HashMap<>();
    }

    public String provideHint(String questionId) {
        if (hintProvider.hasHint(questionId)) {
            String hint = hintProvider.getHint(questionId);
            hintProvider.recordHintUsage(questionId);

            // Track usage internally
            hintUsageCount.put(questionId, hintUsageCount.getOrDefault(questionId, 0) + 1);

            return hint;
        }
        return "Geen hint beschikbaar voor deze vraag.";
    }

    public int getHintUsageCount(String questionId) {
        return hintUsageCount.getOrDefault(questionId, 0);
    }

    public boolean hasUsedHint(String questionId) {
        return hintUsageCount.containsKey(questionId) && hintUsageCount.get(questionId) > 0;
    }
}