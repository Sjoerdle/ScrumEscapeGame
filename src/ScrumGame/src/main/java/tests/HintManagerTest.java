package tests;

import hints.HintManager;

/**
 * Unit tests voor HintManager klasse
 * Demonstreert gebruik van zelfgemaakte Stubs en Mocks
 */
public class HintManagerTest {

    /**
     * Test met STUB: Controleert het gedrag van HintManager
     * wanneer er een hint beschikbaar is
     */
    public static void testProvideHint_WithAvailableHint_UsingStub() {
        System.out.println("=== TEST 1: Stub Test - Hint beschikbaar ===");

        // Arrange: Maak een stub die altijd dezelfde waarden teruggeeft
        String expectedHint = "Dit is een test hint";
        HintProviderStub stub = new HintProviderStub(expectedHint, true);
        HintManager hintManager = new HintManager(stub);

        // Act: Vraag een hint op
        String actualHint = hintManager.provideHint("test-question");

        // Assert: Controleer of de verwachte hint werd geretourneerd
        if (expectedHint.equals(actualHint)) {
            System.out.println("✓ GESLAAGD: Hint werd correct geretourneerd");
            System.out.println("  Verwacht: " + expectedHint);
            System.out.println("  Gekregen: " + actualHint);
        } else {
            System.out.println("✗ GEFAALD: Hint was niet correct");
            System.out.println("  Verwacht: " + expectedHint);
            System.out.println("  Gekregen: " + actualHint);
        }

        // Aanvullende test: Controleer of usage count werd bijgehouden
        int usageCount = hintManager.getHintUsageCount("test-question");
        if (usageCount == 1) {
            System.out.println("✓ GESLAAGD: Usage count werd correct bijgehouden (1)");
        } else {
            System.out.println("✗ GEFAALD: Usage count was incorrect. Verwacht: 1, Gekregen: " + usageCount);
        }

        System.out.println();
    }

    /**
     * Test met STUB: Controleert het gedrag van HintManager
     * wanneer er geen hint beschikbaar is
     */
    public static void testProvideHint_WithNoHint_UsingStub() {
        System.out.println("=== TEST 2: Stub Test - Geen hint beschikbaar ===");

        // Arrange: Maak een stub die aangeeft dat er geen hint is
        HintProviderStub stub = new HintProviderStub(null, false);
        HintManager hintManager = new HintManager(stub);

        // Act: Vraag een hint op
        String actualHint = hintManager.provideHint("non-existent-question");

        // Assert: Controleer of de standaard "geen hint" boodschap werd geretourneerd
        String expectedMessage = "Geen hint beschikbaar voor deze vraag.";
        if (expectedMessage.equals(actualHint)) {
            System.out.println("✓ GESLAAGD: Correcte 'geen hint' boodschap geretourneerd");
            System.out.println("  Boodschap: " + actualHint);
        } else {
            System.out.println("✗ GEFAALD: Incorrecte boodschap");
            System.out.println("  Verwacht: " + expectedMessage);
            System.out.println("  Gekregen: " + actualHint);
        }

        System.out.println();
    }

    /**
     * Test met MOCK: Controleert of de juiste methoden worden aangeroepen
     * wanneer een hint wordt opgevraagd
     */
    public static void testProvideHint_VerifyMethodCalls_UsingMock() {
        System.out.println("=== TEST 3: Mock Test - Methode aanroepen verificatie ===");

        // Arrange: Maak een mock die bijhoudt welke methoden werden aangeroepen
        String testHint = "Mock hint";
        HintProviderMock mock = new HintProviderMock(testHint, true);
        HintManager hintManager = new HintManager(mock);

        // Act: Vraag een hint op
        String questionId = "mock-test-question";
        hintManager.provideHint(questionId);

        // Assert: Verificeer dat de juiste methoden werden aangeroepen
        boolean allTestsPassed = true;

        // Test 1: hasHint werd aangeroepen
        if (mock.wasHasHintCalled()) {
            System.out.println("✓ GESLAAGD: hasHint() werd aangeroepen");
        } else {
            System.out.println("✗ GEFAALD: hasHint() werd NIET aangeroepen");
            allTestsPassed = false;
        }

        // Test 2: hasHint werd aangeroepen met de juiste parameter
        if (mock.wasHasHintCalledWith(questionId)) {
            System.out.println("✓ GESLAAGD: hasHint() werd aangeroepen met correct questionId");
        } else {
            System.out.println("✗ GEFAALD: hasHint() werd NIET aangeroepen met correct questionId");
            allTestsPassed = false;
        }

        // Test 3: getHint werd aangeroepen
        if (mock.wasGetHintCalled()) {
            System.out.println("✓ GESLAAGD: getHint() werd aangeroepen");
        } else {
            System.out.println("✗ GEFAALD: getHint() werd NIET aangeroepen");
            allTestsPassed = false;
        }

        // Test 4: getHint werd aangeroepen met de juiste parameter
        if (mock.wasGetHintCalledWith(questionId)) {
            System.out.println("✓ GESLAAGD: getHint() werd aangeroepen met correct questionId");
        } else {
            System.out.println("✗ GEFAALD: getHint() werd NIET aangeroepen met correct questionId");
            allTestsPassed = false;
        }

        // Test 5: recordHintUsage werd aangeroepen
        if (mock.wasRecordUsageCalled()) {
            System.out.println("✓ GESLAAGD: recordHintUsage() werd aangeroepen");
        } else {
            System.out.println("✗ GEFAALD: recordHintUsage() werd NIET aangeroepen");
            allTestsPassed = false;
        }

        // Test 6: recordHintUsage werd aangeroepen met de juiste parameter
        if (mock.wasRecordUsageCalledWith(questionId)) {
            System.out.println("✓ GESLAAGD: recordHintUsage() werd aangeroepen met correct questionId");
        } else {
            System.out.println("✗ GEFAALD: recordHintUsage() werd NIET aangeroepen met correct questionId");
            allTestsPassed = false;
        }

        if (allTestsPassed) {
            System.out.println("🎉 ALLE MOCK VERIFICATIES GESLAAGD!");
        }

        System.out.println();
    }

    /**
     * Test met MOCK: Controleert dat methoden NIET worden aangeroepen
     * wanneer er geen hint beschikbaar is
     */
    public static void testProvideHint_NoHintAvailable_VerifyLimitedCalls_UsingMock() {
        System.out.println("=== TEST 4: Mock Test - Beperkte aanroepen bij geen hint ===");

        // Arrange: Mock die zegt dat er geen hint is
        HintProviderMock mock = new HintProviderMock(null, false);
        HintManager hintManager = new HintManager(mock);

        // Act: Probeer een hint op te vragen
        String questionId = "no-hint-question";
        String result = hintManager.provideHint(questionId);

        // Assert: Verificeer dat alleen hasHint werd aangeroepen, niet getHint of recordUsage
        boolean allTestsPassed = true;

        // Test 1: hasHint werd aangeroepen (dit moet altijd gebeuren)
        if (mock.wasHasHintCalledWith(questionId)) {
            System.out.println("✓ GESLAAGD: hasHint() werd correct aangeroepen");
        } else {
            System.out.println("✗ GEFAALD: hasHint() werd niet aangeroepen");
            allTestsPassed = false;
        }

        // Test 2: getHint werd NIET aangeroepen (omdat er geen hint is)
        if (!mock.wasGetHintCalled()) {
            System.out.println("✓ GESLAAGD: getHint() werd correct NIET aangeroepen");
        } else {
            System.out.println("✗ GEFAALD: getHint() werd onverwacht wel aangeroepen");
            allTestsPassed = false;
        }

        // Test 3: recordUsage werd NIET aangeroepen (omdat er geen hint was)
        if (!mock.wasRecordUsageCalled()) {
            System.out.println("✓ GESLAAGD: recordHintUsage() werd correct NIET aangeroepen");
        } else {
            System.out.println("✗ GEFAALD: recordHintUsage() werd onverwacht wel aangeroepen");
            allTestsPassed = false;
        }

        // Test 4: Controleer de geretourneerde boodschap
        String expectedMessage = "Geen hint beschikbaar voor deze vraag.";
        if (expectedMessage.equals(result)) {
            System.out.println("✓ GESLAAGD: Correcte 'geen hint' boodschap geretourneerd");
        } else {
            System.out.println("✗ GEFAALD: Incorrecte boodschap geretourneerd");
            allTestsPassed = false;
        }

        if (allTestsPassed) {
            System.out.println("🎉 ALLE MOCK VERIFICATIES VOOR 'GEEN HINT' GESLAAGD!");
        }

        System.out.println();
    }

    /**
     * Uitgebreide test met MOCK: Test meerdere hint aanvragen
     */
    public static void testMultipleHintRequests_UsingMock() {
        System.out.println("=== TEST 5: Mock Test - Meerdere hint aanvragen ===");

        // Arrange
        HintProviderMock mock = new HintProviderMock("Test hint", true);
        HintManager hintManager = new HintManager(mock);

        // Act: Vraag meerdere hints op
        hintManager.provideHint("question1");
        hintManager.provideHint("question2");
        hintManager.provideHint("question1"); // Dezelfde vraag nogmaals

        // Assert: Verificeer aantallen
        boolean allTestsPassed = true;

        if (mock.getGetHintCallCount() == 3) {
            System.out.println("✓ GESLAAGD: getHint() werd 3x aangeroepen");
        } else {
            System.out.println("✗ GEFAALD: getHint() werd " + mock.getGetHintCallCount() + "x aangeroepen, verwacht: 3");
            allTestsPassed = false;
        }

        if (mock.getRecordUsageCallCount() == 3) {
            System.out.println("✓ GESLAAGD: recordHintUsage() werd 3x aangeroepen");
        } else {
            System.out.println("✗ GEFAALD: recordHintUsage() werd " + mock.getRecordUsageCallCount() + "x aangeroepen, verwacht: 3");
            allTestsPassed = false;
        }

        // Controleer specifieke aanroepen
        if (mock.wasGetHintCalledWith("question1") && mock.wasGetHintCalledWith("question2")) {
            System.out.println("✓ GESLAAGD: getHint() werd aangeroepen met beide question IDs");
        } else {
            System.out.println("✗ GEFAALD: getHint() werd niet aangeroepen met beide question IDs");
            allTestsPassed = false;
        }

        // Controleer interne HintManager state
        if (hintManager.getHintUsageCount("question1") == 2) {
            System.out.println("✓ GESLAAGD: HintManager houdt usage count correct bij voor question1 (2x)");
        } else {
            System.out.println("✗ GEFAALD: HintManager usage count incorrect voor question1");
            allTestsPassed = false;
        }

        if (hintManager.getHintUsageCount("question2") == 1) {
            System.out.println("✓ GESLAAGD: HintManager houdt usage count correct bij voor question2 (1x)");
        } else {
            System.out.println("✗ GEFAALD: HintManager usage count incorrect voor question2");
            allTestsPassed = false;
        }

        if (allTestsPassed) {
            System.out.println("🎉 ALLE MEERDERE HINT TESTS GESLAAGD!");
        }

        System.out.println();
    }

    /**
     * Main methode om alle tests uit te voeren
     */
    public static void main(String[] args) {
        System.out.println("🧪 STARTING HINT MANAGER UNIT TESTS");
        System.out.println("===================================");
        System.out.println();

        // Voer alle tests uit
        testProvideHint_WithAvailableHint_UsingStub();
        testProvideHint_WithNoHint_UsingStub();
        testProvideHint_VerifyMethodCalls_UsingMock();
        testProvideHint_NoHintAvailable_VerifyLimitedCalls_UsingMock();
        testMultipleHintRequests_UsingMock();

        System.out.println("===================================");
        System.out.println("🏁 ALLE TESTS VOLTOOID");
        System.out.println();
        System.out.println("UITLEG:");
        System.out.println("- STUB: Levert altijd dezelfde vaste waarden terug voor predictable testing");
        System.out.println("- MOCK: Houdt bij welke methoden werden aangeroepen en kan dit verificeren");
        System.out.println("- Deze tests tonen aan dat HintManager correct werkt met verschillende dependencies");
    }
}