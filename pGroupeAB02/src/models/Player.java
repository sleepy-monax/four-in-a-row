package models;

public class Player {

    private String name;
    private int score, level, levelMax;

    public Player(String name){
        setName(name);
        this.level = 0;
        this.levelMax = 0;
        this.score = 0;
    }

    public Player(String name, int score, int level, int levelMax){
        setName(name);
        setScore(score);
        setLevel(level);
        setLevelMax(levelMax);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        if (score >= 0 )
            this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (level >=0 || level <= 4)
            this.level = level;
    }

    public int getLevelMax() {
        return levelMax;
    }

    public void setLevelMax(int levelMax) {
        if (levelMax >=0 || levelMax <= 4)
            this.levelMax = levelMax;
    }

}
