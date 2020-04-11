package models;

import java.util.Objects;

public class Player {
    public static final int MAX_LEVEl = 4;

    private int id;
    private String name;
    private int score;
    private int level;
    private int levelMax;
    private boolean hasPlayed = false;

    public Player(int id, String name) {
        this(id, name, 0, 0, 0);
    }

    public Player(int id, String name, int score, int level, int levelMax) {
        this.id = id;
        this.name = name;

        setScore(score);
        setLevel(level);
        setLevelMax(levelMax);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        if (score >= 0)
            this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (level >= 0 && level <= 4)
            this.level = level;
    }

    public int getLevelMax() {
        return levelMax;
    }

    public void setLevelMax(int levelMax) {
        if (levelMax >= 0 && levelMax <= 4)
            this.levelMax = levelMax;
    }

    @Override
    public String toString() {
        return "Player{" + " id='" + id + "'" + ", name='" + name + "'" + ", score='" + score + "'" + ", level='"
                + level + "'" + ", levelMax='" + levelMax + "'" + "}";
    }

    public void played() {
        hasPlayed = true;
    }

    public void answerCorrect() {
        nextLevel();

        score += level * 10;
    }

    public void nextLevel() {
        level++;

        if (level > MAX_LEVEl) {
            level = MAX_LEVEl;
        }

        levelMax = Math.max(levelMax, level);
    }

    public void failed() {
        level = 0;
    }

    public boolean hasPlayed() {
        return this.hasPlayed;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Player)) {
            return false;
        }
        Player player = (Player) o;
        return id == player.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
