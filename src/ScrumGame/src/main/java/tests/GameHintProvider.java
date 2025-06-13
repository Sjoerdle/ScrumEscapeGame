package hints;

import java.util.HashMap;
import java.util.Map;

public class GameHintProvider implements HintProvider {
    private Map<String, String> hints;
    private Map<String, Integer> usageCount;

    public GameHintProvider() {
        this.hints = new HashMap<>();
        this.usageCount = new HashMap<>();
        loadDefaultHints();
    }

    private void loadDefaultHints() {
        hints.put("scrum1", "Denk aan de rollen in Scrum: Product Owner, Scrum Master, Development Team");
        hints.put("scrum2", "Sprint Planning is een belangrijk ceremonie in Scrum");
        hints.put("agile1", "Agile waardes staan in het Agile Manifesto");
        hints.put("kanban1", "Kanban gebruikt een pull-systeem");
    }

    @Override
    public String getHint(String questionId) {
        return hints.get(questionId);
    }

    @Override
    public boolean hasHint(String questionId) {
        return hints.containsKey(questionId);
    }

    @Override
    public void recordHintUsage(String questionId) {
        usageCount.put(questionId, usageCount.getOrDefault(questionId, 0) + 1);
    }

    public int getUsageCount(String questionId) {
        return usageCount.getOrDefault(questionId, 0);
    }
}