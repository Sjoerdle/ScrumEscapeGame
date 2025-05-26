package org.game;

public interface PlayerObserver {
    void onPlayerHealthChanged(int newHealth);
    void onPlayerScoreChanged(int newScore);
    void onPlayerPositionChanged(int x, int y);
    void onKeyCountChanged(int newKeyCount);
    void onItemUsed(String itemName);
    void onItemCollected(String itemName);
}