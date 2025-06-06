package player;

import items.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Speler {
    String naam;
    int X;
    int Y;
    int hp;
    int deathCount;
    int keyCount = 0;
    private Map<String, Item> inventory = new HashMap<>();
    private List<PlayerObserver> observers = new ArrayList<>();

    // Score multiplier fields
    private int scoreMultiplier = 1;
    private long scoreMultiplierEndTime = 0;
    private boolean skipNextMonster = false;

    public Speler(String naam, int X, int Y) {
        this.naam = naam;
        this.X = X;
        this.Y = Y;
        this.hp = 100;
        this.deathCount = 0;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public void setLocation(int X, int Y) {
        this.X = X;
        this.Y = Y;
        notifyPositionChanged();
    }

    public void setHp(int hp) {
        this.hp = hp;
        notifyHealthChanged();
    }

    public int getHp() {
        return hp;
    }

    public int getDeathCount() {
        return deathCount;
    }

    public void addDeath(int deathCount) {
        this.deathCount += 1;
    }

    public int getKeyCount() {
        return keyCount;
    }

    public void setKeyCount(int keyCount) {
        this.keyCount = keyCount;
        notifyKeyCountChanged();
    }

    public void addKey() {
        this.keyCount++;
        notifyKeyCountChanged();
    }

    public void removeKey() {
        this.keyCount--;
        if (this.keyCount < 0) this.keyCount = 0;
        notifyKeyCountChanged();
    }

    public boolean hasKey() {
        return this.keyCount > 0;
    }

    // Score multiplier methods
    public void activateScoreMultiplier(int multiplier, int durationSeconds) {
        this.scoreMultiplier = multiplier;
        this.scoreMultiplierEndTime = System.currentTimeMillis() + (durationSeconds * 1000L);
    }

    public boolean hasActiveScoreMultiplier() {
        return System.currentTimeMillis() < scoreMultiplierEndTime;
    }

    public int getCurrentScoreMultiplier() {
        if (hasActiveScoreMultiplier()) {
            return scoreMultiplier;
        }
        return 1;
    }

    // Skip monster methods
    public void activateMonsterSkip() {
        this.skipNextMonster = true;
    }

    public boolean canSkipMonster() {
        return skipNextMonster;
    }

    public void useMonsterSkip() {
        this.skipNextMonster = false;
    }

    // Observer pattern methods
    public void addObserver(PlayerObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(PlayerObserver observer) {
        observers.remove(observer);
    }

    private void notifyHealthChanged() {
        for (PlayerObserver observer : observers) {
            observer.onPlayerHealthChanged(this.hp);
        }
    }

    private void notifyPositionChanged() {
        for (PlayerObserver observer : observers) {
            observer.onPlayerPositionChanged(this.X, this.Y);
        }
    }

    private void notifyKeyCountChanged() {
        for (PlayerObserver observer : observers) {
            observer.onKeyCountChanged(this.keyCount);
        }
    }

    private void notifyItemUsed(String itemName) {
        for (PlayerObserver observer : observers) {
            observer.onItemUsed(itemName);
        }
    }

    private void notifyItemCollected(String itemName) {
        for (PlayerObserver observer : observers) {
            observer.onItemCollected(itemName);
        }
    }

    public void takeDamage() {
        this.hp -= 20;
        if (this.hp < 0) this.hp = 0;
        notifyHealthChanged();
    }

    public void heal(int amount) {
        this.hp += amount;
        if (this.hp > 100) this.hp = 100;
        notifyHealthChanged();
    }

    // Inventory methods
    public void addItem(Item item) {
        // Check if we already have this type of item, if so don't add duplicate
        if (!inventory.containsKey(item.getName())) {
            inventory.put(item.getName(), item);
            notifyItemCollected(item.getName());
        }
    }

    public boolean useItem(String itemName) {
        Item item = inventory.get(itemName);
        if (item != null) {
            item.use(this);
            inventory.remove(itemName);
            notifyItemUsed(itemName);
            return true;
        }
        return false;
    }

    public boolean hasItem(String itemName) {
        return inventory.containsKey(itemName);
    }

    public Map<String, Item> getInventory() {
        return new HashMap<>(inventory); // Return a copy to prevent external modification
    }
}