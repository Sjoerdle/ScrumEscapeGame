package org.game;

public class Speler {
    String naam;
    int score;
    int X;
    int Y;
    int hp;
    int deathCount;
    int keyCount = 0;

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

    public void heal() {
        this.hp += 33;
    }

    public void takeDamage() {
        this.hp -= 33;
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
}
