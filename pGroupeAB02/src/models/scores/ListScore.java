package models.scores;

import utils.Serialization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListScore implements Serializable {
    private List<Score> scores;

    private ListScore() {
        scores = new ArrayList<>();
    }

    public void addScore(Score score){
        scores.add(score);
    }

    public static ListScore cacheScore = null;

    public static ListScore get(){
        if (cacheScore == null)
            cacheScore = Serialization.readFromJsonFile("scores.json", ListScore.class);

        if (cacheScore == null) {
            cacheScore = new ListScore();
        }

        return cacheScore;
    }

    public static void saveScore(){
        Serialization.writeToJsonFile("scores.json", cacheScore);
    }
}
