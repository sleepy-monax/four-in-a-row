package models;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck implements Serializable {
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

    public void addQuestions(List<Question> questionsList) {
        if (questionsList != null) {
            questionsList.forEach(this::add);
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

    public List<String> getListThemes() {
        List<String> var = new ArrayList<>();
        questions.forEach(question -> var.add(question.getTheme()));
        return var;
    }

    public List<Question> getQuestionsByTheme(String theme) {
        List<Question> var = new ArrayList<>();
        questions.forEach(question -> {
            if (question.getTheme() == theme)
                var.add(question);
        });
        return var;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public List<String> getRandomTheme(int nb) {
        List<String> themeDeBase = new ArrayList<>(getListThemes());
        List<String> themeRand = new ArrayList<>();

        for (int i = 0; i < Math.min(nb, themeDeBase.size()); i++) {
            int rand = new Random().nextInt(themeDeBase.size() - 1);
            themeRand.add(themeDeBase.get(rand));
            themeDeBase.remove(rand);
        }

        return themeRand;
    }

    public void clear() {
        questions.clear();
    }

    public int size() {
        return questions.size();
    }

    @Override
    public String toString() {
        return "{" + " questions='" + getQuestions() + "'" + "}";
    }
}
