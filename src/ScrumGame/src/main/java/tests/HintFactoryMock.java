package tests;

import hints.HintFactory;
import hints.HintProvider;

public class HintFactoryMock extends HintFactory {
    private boolean createHintProviderCalled = false;
    private String lastHelpHint = null;
    private String lastFunnyHint = null;
    private HintProvider providerToReturn;

    public HintFactoryMock(HintProvider providerToReturn) {
        this.providerToReturn = providerToReturn;
    }

    @Override
    public HintProvider createHintProvider(String helpHint, String funnyHint) {
        createHintProviderCalled = true;
        lastHelpHint = helpHint;
        lastFunnyHint = funnyHint;
        return providerToReturn;
    }

    public boolean wasCreateHintProviderCalled() {
        return createHintProviderCalled;
    }

    public String getLastHelpHint() {
        return lastHelpHint;
    }

    public String getLastFunnyHint() {
        return lastFunnyHint;
    }
}