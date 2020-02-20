package models;

public class Player {
    private int id;
    private String name;
    private int score, level, levelMax;

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
        if (level >= 0 || level <= 4)
            this.level = level;
    }

    public int getLevelMax() {
        return levelMax;
    }

    public void setLevelMax(int levelMax) {
        if (levelMax >= 0 || levelMax <= 4)
            this.levelMax = levelMax;
    }

}
