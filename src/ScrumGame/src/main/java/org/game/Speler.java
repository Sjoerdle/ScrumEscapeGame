package org.game;

public class Speler {
    String naam;
    int score;
    int locatie;
    int deathCount;

    public Speler(String naam) {
        this.naam = naam;
        this.score = 0;
        this.locatie = 0;
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

    public int getLocatie() {
        return locatie;
    }

    public void setLocatie(int locatie) {
        this.locatie = locatie;
    }

    public int getDeathCount() {
        return deathCount;
    }

    public void addDeath(int deathCount) {
        this.deathCount += 1;
    }
}
