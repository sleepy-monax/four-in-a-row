package models;

import exceptions.DoublonException;
import utils.Serialization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck implements Serializable {
    static Deck cachedDeck = null;
    private final List<Question> questions;

    public Deck() {
        questions = new ArrayList<>();
    }

    public static Deck get() {
        if (cachedDeck == null) {
            cachedDeck = Serialization.readFromJsonFile("questions.json", Deck.class);

            if (cachedDeck == null) {
                System.out.println("DECK: WARNING: Falling back on the builtin deck.");

                cachedDeck = Serialization.readFromJsonFileInJar("/assets/questions.json", Deck.class);
            } else {
                System.out.println("DECK: Using the deck from the data folder");
            }
        }

        return cachedDeck;
    }

    public static void save() {
        Serialization.writeToJsonFile("questions.json", cachedDeck);
    }

    public boolean add(Question question) throws DoublonException {
        if (question.isValid() && !questions.contains(question)) {
            return questions.add(question.clone());
        } else {
            throw new DoublonException();
        }
    }

    public void addQuestions(List<Question> questionsList) {
        this.questions.addAll(questions);
    }

    public boolean replace(Question oldQuestion, Question newQuestion) {
        if (newQuestion.isValid() && questions.contains(oldQuestion) && !questions.contains(newQuestion)) {
            questions.remove(oldQuestion);
            try {
                add(newQuestion);
            } catch (DoublonException e) {
                e.printStackTrace();
            }
            return true;
        }

        return false;
    }

    public boolean remove(Question question) {
        return questions.remove(question);
    }

    public Question remove(int index) {
        return questions.remove(index);
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
        List<Question> result = new ArrayList<>();
        questions.forEach(question -> {
            if (question.getTheme().equals(theme)) {
                result.add(question);
            }
        });
        return result;
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
