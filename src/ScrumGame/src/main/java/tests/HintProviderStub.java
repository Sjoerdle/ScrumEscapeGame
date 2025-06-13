package tests;

import hints.HintProvider;

/**
 * Stub implementatie van HintProvider voor testing
 * Een stub levert altijd voorspelbare, vaste waarden terug
 */
public class HintProviderStub implements HintProvider {
    private final String fixedHint;
    private final boolean hasHintValue;

    public HintProviderStub(String fixedHint, boolean hasHintValue) {
        this.fixedHint = fixedHint;
        this.hasHintValue = hasHintValue;
    }

    @Override
    public String getHint(String questionId) {
        return fixedHint;
    }

    @Override
    public boolean hasHint(String questionId) {
        return hasHintValue;
    }

    @Override
    public void recordHintUsage(String questionId) {
        // Stub doet niets met usage recording
    }
}