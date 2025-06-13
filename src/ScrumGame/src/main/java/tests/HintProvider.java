package hints;

public interface HintProvider {
    String getHint(String questionId);
    boolean hasHint(String questionId);
    void recordHintUsage(String questionId);
}