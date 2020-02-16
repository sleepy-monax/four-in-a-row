package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Question {

    private String author;
    private List<String> clues;
    private String theme;
    private String answer;

    public Question(String author, String theme, String answer) {
        setAuthor(author);
        clues = new ArrayList<String>();
        setAnswer(answer);
        setTheme(theme);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void ajouterClues(String a) {
        if (clues.size() <= 3) {
            if (!clues.contains(a)) {
                clues.add(a);
            }
        }
    }

    public void supprimerClues(String a) {
        if (clues.contains(a)) {
            clues.remove(a);
        }
    }

    public boolean updateQuestions(Question q1, Question q2) throws CloneNotSupportedException {
            /*if (clues.contains(q1) && !clues.contains(q2)) {
                clues.set(clues.indexOf(q1),q2);
                return true;
            }*/
        return false;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question questions = (Question) o;
        return Objects.equals(author, questions.author) &&
                Objects.equals(clues, questions.clues) &&
                Objects.equals(theme, questions.theme) &&
                Objects.equals(answer, questions.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, clues, theme, answer);
    }

    @Override
    public String toString() {
        return "Questions{" +
                "author='" + author + '\'' +
                ", clues=" + clues +
                ", theme='" + theme + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }

    public Question clone() {
        Question q = new Question(author, theme, answer);
        for (int i = 0; i < clues.size(); i++) {
            q.ajouterClues(clues.get(i));
        }
        return q;
    }
}
