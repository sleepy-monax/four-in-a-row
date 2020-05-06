package tests;

import exceptions.DoublonException;
import models.Deck;
import models.Question;
import org.junit.Assert;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DeckTest {
    private Deck deck;
    private List<Question> questions;
    private Question q1, q2;

    @org.junit.Before
    public void setUp() throws Exception {
        questions = new ArrayList<>();
        q1 = new Question("Moi","America","Trump");
        q1.addClues("Who's the actual president?");
        q2 = new Question("Moi","America","Trump");
        q2.addClues("Who's the actual president?");
        deck = new Deck();
        Field fList = deck.getClass().getDeclaredField("questions");
        fList.setAccessible(true);
        questions = (List<Question>) fList.get(deck);
    }

    @org.junit.After
    public void tearDown() throws Exception {
        questions = null;
        deck = null;
        q1=null;
        q2=null;
    }

    @org.junit.Test
    public void add() throws DoublonException {
        Assert.assertFalse(questions.contains(q1));
        int size = questions.size();
        deck.add(q1);
        Assert.assertTrue(questions.contains(q1));
        Assert.assertEquals(questions.size(), size+1);
        Assert.assertNotSame(q1,questions.get(0));
    }

    @org.junit.Test (expected = DoublonException.class)
    public void addDoublon() throws DoublonException {
        Assert.assertFalse(questions.contains(q1));
        deck.add(q1);
        Assert.assertTrue(questions.contains(q1));
        deck.add(q1);
    }

    @org.junit.Test (expected = DoublonException.class)
    public void addNull() throws DoublonException {
        int size = questions.size();
        deck.add(null);
        Assert.assertEquals(questions.size(), size);
    }

}