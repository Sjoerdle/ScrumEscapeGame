package tests;

import hints.GameHintProvider;

/**
 * Unit tests voor GameHintProvider klasse
 * Deze tests testen de concrete implementatie (zonder mocks)
 */
public class GameHintProviderTest {

    public static void testHasHint_WithValidQuestionId() {
        System.out.println("=== TEST: hasHint met geldige question ID ===");

        // Arrange
        GameHintProvider provider = new GameHintProvider();

        // Act & Assert
        if (provider.hasHint("scrum1")) {
            System.out.println("✓ GESLAAGD: hasHint retourneert true voor scrum1");
        } else {
            System.out.println("✗ GEFAALD: hasHint retourneert false voor scrum1");
        }

        System.out.println();
    }

    public static void testHasHint_WithInvalidQuestionId() {
        System.out.println("=== TEST: hasHint met ongeldige question ID ===");

        // Arrange
        GameHintProvider provider = new GameHintProvider();

        // Act & Assert
        if (!provider.hasHint("nonexistent")) {
            System.out.println("✓ GESLAAGD: hasHint retourneert false voor nonexistent");
        } else {
            System.out.println("✗ GEFAALD: hasHint retourneert true voor nonexistent");
        }

        System.out.println();
    }

    public static void testGetHint_ReturnsCorrectHint() {
        System.out.println("=== TEST: getHint retourneert correcte hint ===");

        // Arrange
        GameHintProvider provider = new GameHintProvider();

        // Act
        String hint = provider.getHint("scrum1");

        // Assert
        if (hint != null && hint.contains("Scrum")) {
            System.out.println("✓ GESLAAGD: getHint retourneert hint met 'Scrum'");
            System.out.println("  Hint: " + hint);
        } else {
            System.out.println("✗ GEFAALD: getHint retourneert geen geldige Scrum hint");
            System.out.println("  Gekregen: " + hint);
        }

        System.out.println();
    }

    public static void testRecordHintUsage_TracksUsage() {
        System.out.println("=== TEST: recordHintUsage houdt usage bij ===");

        // Arrange
        GameHintProvider provider = new GameHintProvider();
        String questionId = "scrum1";

        // Act
        provider.recordHintUsage(questionId);
        provider.recordHintUsage(questionId);

        // Assert
        int usageCount = provider.getUsageCount(questionId);
        if (usageCount == 2) {
            System.out.println("✓ GESLAAGD: Usage count is correct (2)");
        } else {
            System.out.println("✗ GEFAALD: Usage count is incorrect. Verwacht: 2, Gekregen: " + usageCount);
        }

        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("🧪 STARTING GAME HINT PROVIDER TESTS");
        System.out.println("====================================");
        System.out.println();

        testHasHint_WithValidQuestionId();
        testHasHint_WithInvalidQuestionId();
        testGetHint_ReturnsCorrectHint();
        testRecordHintUsage_TracksUsage();

        System.out.println("====================================");
        System.out.println("🏁 GAME HINT PROVIDER TESTS VOLTOOID");
    }
}