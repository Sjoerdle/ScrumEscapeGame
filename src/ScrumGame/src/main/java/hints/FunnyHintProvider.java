package hints;

public class FunnyHintProvider implements HintProvider {
    private String funnyHint;

    public FunnyHintProvider(String funnyHint) {
        this.funnyHint = funnyHint;
    }

    @Override
    public String getHint() {
        return funnyHint.isEmpty() ? "Voortaan niet hele avonden gamen, studeren is beter voor je!" : funnyHint;
    }

    @Override
    public String getHintType() {
        return "Funny";
    }
}