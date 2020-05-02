package models.scores;

import utils.Serialization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListScore implements Serializable {
    private List<Score> lesScores;

    private ListScore() {
        lesScores = new ArrayList<>();
    }

    public void ajouterScore(Score score){
        lesScores.add(score);
    }

    public static ListScore cacheScore = null;

    public static ListScore get(){
        if (cacheScore == null)
            Serialization.readFromJsonFile("scores.json", Score.class);

        if (cacheScore == null) {
            cacheScore = new ListScore();
        }

        return cacheScore;
    }

    public static void saveScore(){
        Serialization.writeToJsonFile("scores.json", cacheScore);
    }
}
