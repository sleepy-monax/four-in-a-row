package models;

import java.io.Serializable;
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

    public List<String> themes() {
        List<String> themes = new ArrayList<>();

        for (Question question : questions) {
            if (!themes.contains(question.getTheme())) {
                themes.add(question.getTheme());
            }
        }

        return themes;
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

    public String[] getRandomThemes(int count) {
        Random random = new Random();

        List<String> pool = themes();
        count = Math.min(count, pool.size());

        String[] result = new String[count];

        for (int i = 0; i < count; i++) {
            int index = random.nextInt(pool.size());

            result[i] = pool.get(index);
            pool.remove(index);
        }

        return result;
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
