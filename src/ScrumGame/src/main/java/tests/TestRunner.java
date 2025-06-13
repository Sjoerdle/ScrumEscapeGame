package tests;

/**
 * TestRunner klasse om alle unit tests uit te voeren
 * Demonstreert het gebruik van Mocks en Stubs in unit testing
 */
public class TestRunner {

    public static void main(String[] args) {
        System.out.println("🚀 SCRUM GAME UNIT TESTS");
        System.out.println("========================");
        System.out.println();
        System.out.println("Deze tests demonstreren:");
        System.out.println("✓ Zelfgemaakte STUBS voor voorspelbare test data");
        System.out.println("✓ Zelfgemaakte MOCKS voor interactie verificatie");
        System.out.println("✓ Unit testing zonder externe frameworks");
        System.out.println();
        System.out.println("========================");
        System.out.println();

        // Voer HintManager tests uit (met Mocks en Stubs)
        System.out.println("🔧 HINT MANAGER TESTS (Met Mocks & Stubs)");
        System.out.println("------------------------------------------");
        HintManagerTest.main(args);

        System.out.println();
        System.out.println("🔧 GAME HINT PROVIDER TESTS (Concrete implementatie)");
        System.out.println("-----------------------------------------------------");
        GameHintProviderTest.main(args);

        System.out.println();
        System.out.println("🎯 SAMENVATTING MOCK VS STUB GEBRUIK:");
        System.out.println("=====================================");
        System.out.println("STUB (HintProviderStub):");
        System.out.println("- Levert altijd dezelfde vaste waarden terug");
        System.out.println("- Gebruikt voor het testen van gedrag van de klasse onder test");
        System.out.println("- Voorspelbaar en simpel");
        System.out.println();
        System.out.println("MOCK (HintProviderMock):");
        System.out.println("- Houdt bij welke methoden werden aangeroepen");
        System.out.println("- Controleert interacties tussen klassen");
        System.out.println("- Verificeert dat de juiste methoden met juiste parameters worden aangeroepen");
        System.out.println();
        System.out.println("✅ ALLE TESTS VOLTOOID!");
        System.out.println("📚 User Story 26 requirements vervuld:");
        System.out.println("   ✓ Zelfgemaakte mock en stub implementaties");
        System.out.println("   ✓ Tests tonen stub gebruik voor gedrag testing");
        System.out.println("   ✓ Tests tonen mock gebruik voor interactie controle");
        System.out.println("   ✓ Geen externe frameworks gebruikt");
    }
}