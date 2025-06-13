package hints;

public class HelpHintProvider implements HintProvider {
    private String helpHint;

    public HelpHintProvider(String helpHint) {
        this.helpHint = helpHint;
    }

    @Override
    public String getHint() {
        return helpHint.isEmpty() ? "Probeer nog eens na te denken over de vraag." : helpHint;
    }

    @Override
    public String getHintType() {
        return "Help";
    }
}