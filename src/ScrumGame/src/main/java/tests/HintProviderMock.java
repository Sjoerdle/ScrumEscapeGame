package tests;

import hints.HintProvider;
import java.util.ArrayList;
import java.util.List;

/**
 * Mock implementatie van HintProvider voor testing
 * Een mock houdt bij welke methoden werden aangeroepen en met welke parameters
 */
public class HintProviderMock implements HintProvider {
    private List<String> getHintCalls = new ArrayList<>();
    private List<String> hasHintCalls = new ArrayList<>();
    private List<String> recordUsageCalls = new ArrayList<>();

    private String hintToReturn;
    private boolean hasHintToReturn;

    public HintProviderMock(String hintToReturn, boolean hasHintToReturn) {
        this.hintToReturn = hintToReturn;
        this.hasHintToReturn = hasHintToReturn;
    }

    @Override
    public String getHint(String questionId) {
        getHintCalls.add(questionId);
        return hintToReturn;
    }

    @Override
    public boolean hasHint(String questionId) {
        hasHintCalls.add(questionId);
        return hasHintToReturn;
    }

    @Override
    public void recordHintUsage(String questionId) {
        recordUsageCalls.add(questionId);
    }

    // Verification methods voor de mock
    public boolean wasGetHintCalled() {
        return !getHintCalls.isEmpty();
    }

    public boolean wasGetHintCalledWith(String questionId) {
        return getHintCalls.contains(questionId);
    }

    public int getGetHintCallCount() {
        return getHintCalls.size();
    }

    public boolean wasHasHintCalled() {
        return !hasHintCalls.isEmpty();
    }

    public boolean wasHasHintCalledWith(String questionId) {
        return hasHintCalls.contains(questionId);
    }

    public boolean wasRecordUsageCalled() {
        return !recordUsageCalls.isEmpty();
    }

    public boolean wasRecordUsageCalledWith(String questionId) {
        return recordUsageCalls.contains(questionId);
    }

    public int getRecordUsageCallCount() {
        return recordUsageCalls.size();
    }

    public List<String> getAllGetHintCalls() {
        return new ArrayList<>(getHintCalls);
    }

    public List<String> getAllRecordUsageCalls() {
        return new ArrayList<>(recordUsageCalls);
    }
}