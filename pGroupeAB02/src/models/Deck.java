package models;

import models.Question;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private List<Question> lesquestions;


    public Deck() {
        lesquestions = new ArrayList<>();
    }

    public void addQuestion(Question q) throws CloneNotSupportedException {
        if (!lesquestions.contains(q)){
            System.out.println("ajout commencé");
            lesquestions.add((Question) q.clone());
            System.out.println("ajout fini");
        }
    }

    public void removeQuestion(Question questions){
        if (lesquestions.contains(questions)){
            lesquestions.remove(questions);
        }
    }

    public boolean updateQuestions(Question q1, Question q2) throws CloneNotSupportedException {
        if (lesquestions.contains(q1) && !lesquestions.contains(q2)) {
            lesquestions.remove(q1);
            lesquestions.add(q2.clone());
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        System.out.println("écriture");
        return "Deck{" +
                "lesquestions=" + lesquestions +
                '}';
    }

}
