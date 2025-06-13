package tests;

import hints.HintProvider;

public class HintProviderStub implements HintProvider {
    private String fixedHint;
    private String fixedHintType;

    public HintProviderStub(String fixedHint, String fixedHintType) {
        this.fixedHint = fixedHint;
        this.fixedHintType = fixedHintType;
    }

    @Override
    public String getHint() {
        return fixedHint;
    }

    @Override
    public String getHintType() {
        return fixedHintType;
    }
}