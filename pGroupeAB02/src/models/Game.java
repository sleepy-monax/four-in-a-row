package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import message.GameTick;
import message.PlayerJoin;
import message.PlayerLeave;
import messageloop.MessageLoop;

public abstract class Game {
    private Deck deck;
    private Player players[];
    private int level, levelmax, ticktot, score;
    private Difficulty difficulty;
    private Question actualQuestion;
    private String actualTheme;
    private List<String> themeRand;

    private MessageLoop messageLoop;

    public Game(Deck deck) {
        setDeck(deck);
        messageLoop = new MessageLoop();
        level = 0;
        players = new Player[4];
        ticktot = 0;
    }

    public void letsPlay(Difficulty difficulty, int nbtheme){
        randomTheme(nbtheme);
        setDifficulty(difficulty);
    }

    public List<String> randomTheme(int nb) {
        List<String> themeDeBase = new ArrayList<>(deck.getListThemes());
        themeRand = new ArrayList<>();
        for (int i = 0; i < nb; i++) {
            int rand = new Random().nextInt(themeDeBase.size()-1);
            themeRand.add(themeDeBase.get(rand));
            themeDeBase.remove(rand);
        }
        return themeRand;
    }

    public boolean reply(String reply) {
        if (actualQuestion.toString() == reply) {
            setLevel(level++);
            if(level < 1){
                score +=5;
            }else if (level == 1){
                score+=10;
            }else if (level == 2){
                score+=20;
            }else if (level == 3){
                score+=30;
            }else if (level == 4){
                score+=40;
            }
            if (levelmax < level)
                level = levelmax;
            return true;
        }
        setLevel(0);
        return false;
    }

    public Player joinPlayer(String name) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null) {
                return joinPlayer(i, name);
            }
        }

        return null;
    }

    public Player joinPlayer(int id, String name) {
        if (players[id] == null) {
            Player newPlayer = new Player(id, name);
            System.out.println(newPlayer + " join the game");

            players[id] = newPlayer;

            messageLoop.post(new PlayerJoin(newPlayer));

            return newPlayer;
        }

        return null;
    }

    public void tick() {
        messageLoop.post(new GameTick());
        ticktot+=1;
        System.out.println(ticktot);
        if (ticktot == 60)
            shutdown();
    }

    public boolean removePlayer(Player player) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == player) {
                System.out.println(player + " leave the game");

                messageLoop.post(new PlayerLeave(player));

                players[i] = null;

                return true;
            }
        }

        return false;
    }

    public boolean removePlayer(int id) {
        return removePlayer(players[id]);
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        if (deck != null)
            this.deck = deck;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (level == 0 && level < 4)
            this.level = level;
    }

    public int getLevelmax() {
        return levelmax;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setTicktot(int ticktot) {
        if (ticktot <60 && ticktot >= 0 )
            this.ticktot = ticktot;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public List<Player> getPlayers() {
        ArrayList<Player> playerList = new ArrayList<>(players.length);

        for (Player player : players) {
            if (player != null) {
                playerList.add(player);
            }
        }

        return playerList;
    }

    public Player getPlayer(int id) {
        return players[id];
    }

    public MessageLoop getMessageLoop() {
        return messageLoop;
    }

    public abstract void shutdown();
}
