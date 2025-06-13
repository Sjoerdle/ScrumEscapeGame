package hints;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HintFactory {
    private Random random = new Random();

    public HintProvider createHintProvider(String helpHint, String funnyHint) {
        List<HintProvider> providers = new ArrayList<>();

        // Voeg alleen providers toe die daadwerkelijk hints hebben
        if (!helpHint.isEmpty()) {
            providers.add(new HelpHintProvider(helpHint));
        }
        if (!funnyHint.isEmpty()) {
            providers.add(new FunnyHintProvider(funnyHint));
        }

        // Als geen hints beschikbaar zijn, geef standaard help provider
        if (providers.isEmpty()) {
            providers.add(new HelpHintProvider(""));
        }

        // Selecteer willekeurig een provider
        return providers.get(random.nextInt(providers.size()));
    }
}