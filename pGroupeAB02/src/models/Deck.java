package models;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import models.Question;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Deck {
    private final List<Question> questions;

    public Deck() {
        questions = new ArrayList<>();
    }

    public boolean add(Question q) {

        /*
        Si la question fournie en paramètre et non nulle, valide et qu'elle n'est pas déjà présente dans "questions"
        ==> retourne true et ajoute un clone de la question.
        Dans les autres cas==> retourne false.
         */
        if (q != null && q.isValid() && !questions.contains(q)) {
            questions.add(q.clone());
            return true;
        }
        return false;
    }


    public boolean remove(String statement) {
        /*
        Une instance de Question est crée pour pouvoir la fournir en tant que paramètre dans la méthode .remove
        .remove renvoie true si la suppression à eu lieu, sinon false;
         */
        return questions.remove(statement);
    }


    public boolean update(Question oldQuestion, Question newQuestion) {

        /*
        Si la nouvelle question est valide, non nulle et que l'ancienne question est présente dans la liste et que
         la nouvelle question peut être ajoutée alors
        ==>retourne true
        Sinon==>retourne false
         */

        if (newQuestion != null && newQuestion.isValid() && questions.contains(oldQuestion) && !questions.contains(newQuestion)) {
            questions.remove(oldQuestion);
            add(newQuestion);
            return true;
        }
        return false;
    }



    public Question getQuestion(String statement) {
        /*
        Permet de recuperer une instance de Question grâce à son intitulé

        récupère l'index d'une instance de Question possédant le même intitulé(deux question sont les mêmes si elles
        possèdent le même intitulé). Si l'index est different de -1 alors la question est présente dans la liste et un clone de celle-ci est retourné
        Sinon retourne null;
         */
        int ind = questions.indexOf(statement);
        if (ind != -1) {
            return questions.get(ind).clone();
        }
        return null;
    }


    public String toJSON() {

        /*
        renvoie une chaîne de caractère respectant la syntaxe d'un fichier JSON
         */
        return new Gson().toJson(this);
    }

    public static Deck fromJSON(String json) {

        /*
            renvoie une instance de Deck charger à partir d'une chaîne de caractère respectant la syntaxe JSON
            Si l'instance a pu être chargée et est non nulle==> la liste de ses question stockée dans
            une nouvelle liste, elle est ensuite vidée et la nouvelle liste est ajoutée avec la méthode
            .add(Liste<Question>) qui va éviter les doublons.

         */


        //Gson gson= new Gson();
        JsonReader gsonReader = new JsonReader(new StringReader(json));
        gsonReader.setLenient(true);
        Gson gson = new Gson();
        Deck ret = null;


        ret = gson.fromJson(json, Deck.class);
        if (ret != null) {
            List<Question> questionList = ret.getQuestions();
            ret.clear();
            ret.addQuestions(questionList);
        }


        /*
        try {
            ret = gson.fromJson(gsonReader, Deck.class);
            //ret=gson.fr
            if (ret!=null){
                List<Question> questionList= ret.getQuestions();
                ret.clear();
                ret.addQuestions(questionList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        */

        return ret;
    }

    public void clear() {

        /*
        vide la liste de question
         */
        questions.clear();
    }

    public List<Question> getQuestions() {

        /*
        retourne une liste de questions clonées
         */

        List<Question> ret = new ArrayList<>();

        questions.forEach(question -> ret.add(question.clone()));

        return ret;

    }

    public void addQuestions(List<Question> otherQuestions) {

        /*
        Si la liste fournie est non nulle==>
        la Liste de liste de question est parcourue et la méthode add est appelée sur chacun de ses éléments
         */
        if (otherQuestions != null) {
            otherQuestions.forEach(this::add);
        }
    }


    public int size() {
        /*
        renvoie le nombre de questions contenues dans la liste questions
         */
        return questions.size();
    }


}
