package player;

public interface PlayerObserver {
    void onPlayerHealthChanged(int newHealth);
    void onPlayerPositionChanged(int x, int y);
    void onKeyCountChanged(int newKeyCount);
    void onItemUsed(String itemName);
    void onItemCollected(String itemName);
}