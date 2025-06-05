package player;

import items.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Speler {
    String naam;
    int score;
    int X;
    int Y;
    int hp;
    int deathCount;
    int keyCount = 0;
    private Map<String, Item> inventory = new HashMap<>();
    private List<PlayerObserver> observers = new ArrayList<>();

    public Speler(String naam, int X, int Y) {
        this.naam = naam;
        this.score = 0;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp () {
        return hp;
    }

    public int getDeathCount() {
        return deathCount;
    }

    public void addDeath(int deathCount) {
        this.deathCount += 1;
    }

    public int getKeyCount() { return keyCount; }
    public void setKeyCount(int keyCount) { this.keyCount = keyCount; }
    public void addKey() { this.keyCount++; }
    public void removeKey() { this.keyCount--; if (this.keyCount < 0) this.keyCount = 0; }
    public boolean hasKey() { return this.keyCount > 0; }

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

    public void takeDamage() {
        this.hp -= 33;
        notifyHealthChanged();
    }

    public void heal() {
        this.hp += 33;
        if (this.hp > 100) this.hp = 100;
        notifyHealthChanged();
    }

    public void addItem(Item item) {
        inventory.put(item.getName(), item);
        notifyItemCollected(item.getName());
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
}
