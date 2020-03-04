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

    public void clear() {
        questions.clear();
    }

    public int size() {
        return questions.size();
    }

}
