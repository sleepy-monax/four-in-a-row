package models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Deck {
    private final List<Question> questions;

    public Deck() {
        questions = new ArrayList<>();
    }

    public boolean add(Question question) {
        /*
         * Si la question fournie en paramètre et non nulle, valide et qu'elle n'est pas
         * déjà présente dans "questions" ==> retourne true et ajoute un clone de la
         * question. Dans les autres cas ==> retourne false.
         */
        if (question.isValid() && !questions.contains(question)) {
            return questions.add(question.clone());
        } else {
            return false;
        }
    }

    public void add(List<Question> questions) {
        questions.forEach(this::add);
    }

    public List<Question> questions() {
        return questions.stream().map(question -> question.clone()).collect(Collectors.toList());
    }

    public boolean replace(Question oldQuestion, Question newQuestion) {

        /*
         * Si la nouvelle question est valide, non nulle et que l'ancienne question est
         * présente dans la liste et que la nouvelle question peut être ajoutée alors
         * ==>retourne true Sinon==>retourne false
         */

        if (newQuestion.isValid() && questions.contains(oldQuestion) && !questions.contains(newQuestion)) {
            questions.remove(oldQuestion);
            add(newQuestion);
            return true;
        }

        return false;
    }

    public boolean remove(Question question) {
        /*
         * Une instance de Question est crée pour pouvoir la fournir en tant que
         * paramètre dans la méthode .remove .remove renvoie true si la suppression à eu
         * lieu, sinon false;
         */
        return questions.remove(question);
    }

    public void clear() {
        questions.clear();
    }

    public int size() {
        return questions.size();
    }

}
