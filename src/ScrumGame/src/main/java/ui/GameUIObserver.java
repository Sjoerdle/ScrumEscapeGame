package ui;

import player.PlayerObserver;

public class GameUIObserver implements PlayerObserver {
    private boolean debugMode;

    public GameUIObserver(boolean debugMode) {
        this.debugMode = debugMode;
    }

    @Override
    public void onPlayerHealthChanged(int newHealth) {
        if (debugMode) {
            System.out.println("[DEBUG] Health changed to: " + newHealth);
        }

        if (newHealth <= 0) {
            System.out.println("💀 You have died! Game Over!");
        } else if (newHealth <= 25) {
            System.out.println("⚠️  Warning: Low health (" + newHealth + "/100)!");
        }
    }

    @Override
    public void onPlayerScoreChanged(int newScore) {
        if (debugMode) {
            System.out.println("[DEBUG] Score changed to: " + newScore);
        }

        // Show milestone messages
        if (newScore > 0 && newScore % 100 == 0) {
            System.out.println("🎉 Score milestone reached: " + newScore + " points!");
        }
    }

    @Override
    public void onPlayerPositionChanged(int x, int y) {
        if (debugMode) {
            System.out.println("[DEBUG] Player moved to position: (" + x + ", " + y + ")");
        }
    }

    @Override
    public void onKeyCountChanged(int newKeyCount) {
        if (debugMode) {
            System.out.println("[DEBUG] Key count changed to: " + newKeyCount);
        }

        if (newKeyCount > 0) {
            System.out.println("🗝️ You now have " + newKeyCount + " key(s)");
        }
    }

    @Override
    public void onItemUsed(String itemName) {
        System.out.println("✨ Used item: " + itemName);
    }

    @Override
    public void onItemCollected(String itemName) {
        System.out.println("📦 Collected item: " + itemName);
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
}