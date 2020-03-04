package models;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Question> questions = null;

    public Deck() {
        questions = new ArrayList<>();
    }

    public static Deck fromJSON(String json){
        JsonReader jsonReader = new JsonReader(new StringReader(json));
        jsonReader.setLenient(true);
        Gson gson = new Gson();
        Deck deck = null;

        deck = gson.fromJson(json, Deck.class);
        if (deck != null){
            List<Question> questList = deck.getAllQuestions();
            deck.clear();
            deck.addQuestions(questList);
        }

        return deck;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }

    public boolean add(Question question) {
        if (question.isValid() && !questions.contains(question)) {
            return questions.add(question.clone());
        } else {
            return false;
        }
    }

    public void addQuestions(List<Question> questionsList){
        if ( questionsList != null){
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
