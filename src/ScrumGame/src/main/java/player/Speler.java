package player;

import items.ItemInfo;
import items.Usable;
import jokers.Joker;
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
    private boolean hasDied = false; // NEW: Track if player has died this session

    // Flexibele inventory - kan alle soorten objecten bevatten
    private Map<String, Object> inventory = new HashMap<>();
    private List<PlayerObserver> observers = new ArrayList<>();

    // Score multiplier fields
    private int scoreMultiplier = 1;
    private long scoreMultiplierEndTime = 0;
    private boolean hasUsedKeyJoker = false;

    public Speler(String naam, int X, int Y) {
        this.naam = naam;
        this.X = X;
        this.Y = Y;
        this.hp = 100;
        this.deathCount = 0;
        this.hasDied = false;
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
        this.hasDied = true; // NEW: Mark that player has died
    }

    // NEW: Getter for death status
    public boolean hasDied() {
        return hasDied;
    }

    // NEW: Reset death status (for new games)
    public void resetDeathStatus() {
        this.hasDied = false;
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
        if (this.hp <= 0) {
            this.hp = 0;
            this.hasDied = true; // NEW: Mark death when HP reaches 0
            addDeath(1); // This will also set hasDied to true
        }
        notifyHealthChanged();
    }

    public void heal(int amount) {
        this.hp += amount;
        if (this.hp > 100) this.hp = 100;
        notifyHealthChanged();
    }

    // Inventory methods - aangepast voor ISP
    public void addItem(Object item) {
        if (item instanceof ItemInfo) {
            ItemInfo itemInfo = (ItemInfo) item;
            if (!inventory.containsKey(itemInfo.getName())) {
                inventory.put(itemInfo.getName(), item);
                notifyItemCollected(itemInfo.getName());
            }
        }
    }

    public boolean useItem(String itemName) {
        Object item = inventory.get(itemName);
        if (item instanceof Usable) {
            ((Usable) item).use(this);
            inventory.remove(itemName);
            notifyItemUsed(itemName);
            return true;
        }
        return false;
    }

    public void removeItem(String itemName) {
        inventory.remove(itemName);
    }

    public boolean hasItem(String itemName) {
        return inventory.containsKey(itemName);
    }

    public boolean hasJoker(String jokerName) {
        return inventory.values().stream()
                .anyMatch(item -> item instanceof Joker &&
                        ((Joker) item).getName().equalsIgnoreCase(jokerName));
    }

    public Joker getJoker(String jokerName) {
        for (Object item : inventory.values()) {
            if (item instanceof Joker &&
                    ((Joker) item).getName().equalsIgnoreCase(jokerName)) {
                return (Joker) item;
            }
        }
        return null;
    }

    // Key joker methods
    public boolean hasUsedKeyJoker() {
        return hasUsedKeyJoker;
    }

    public void setKeyJokerUsed() {
        this.hasUsedKeyJoker = true;
    }

    public Map<String, Object> getInventory() {
        return new HashMap<>(inventory); // Return a copy to prevent external modification
    }
}