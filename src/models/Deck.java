package modele;

import models.Question;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private List<Question> lesquestions;


    public Deck() {
        lesquestions = new ArrayList<>();
    }

    public void ajouterQuestion(Question q) throws CloneNotSupportedException {
        if (!lesquestions.contains(q)){
            System.out.println("ajout commencé");
            lesquestions.add((Question) q.clone());
            System.out.println("ajout fini");
        }
    }

    public void supprimerQuestion(Question questions){
        if (lesquestions.contains(questions)){
            lesquestions.remove(questions);
        }
    }

    @Override
    public String toString() {
        System.out.println("écriture");
        return "Deck{" +
                "lesquestions=" + lesquestions +
                '}';
    }

}
