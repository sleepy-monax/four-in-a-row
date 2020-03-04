package models;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Question> questions = null;

    public Deck() {
        questions = new ArrayList<>();
    }

    public boolean add(Question question) {
        if (question.isValid() && !questions.contains(question)) {
            return questions.add(question.clone());
        } else {
            return false;
        }
    }

    public boolean replace(Question oldQuestion, Question newQuestion) {
        if (newQuestion.isValid() && questions.contains(oldQuestion) && !questions.contains(newQuestion)) {
            questions.remove(oldQuestion);
            add(newQuestion);
            return true;
        }

        return false;
    }

    public boolean remove(Question question) {
        return questions.remove(question);
    }

    public List<Question> getQuestion(Difficulty diff) {
        List<Question> filteredQuestions = new ArrayList<>();
        questions.forEach(question -> {
            if (question.getDifficuly() == diff)
                filteredQuestions.add(question);
        });

        return filteredQuestions;
    }

    public List<String> getListThemes(){
        List<String> var = new ArrayList<>();
        questions.forEach(question -> var.add(question.getTheme()));
        return var;
    }

    public List<Question> getQuestionsByTheme(String theme){
        List<Question> var = new ArrayList<>();
        questions.forEach(question -> {
            if(question.getTheme() == theme)
                var.add(question);
        });
        return var;
    }

    public List<Question> getAllQuestions(){
        return questions;
    }

    public void clear() {
        questions.clear();
    }

    public int size() {
        return questions.size();
    }

}
