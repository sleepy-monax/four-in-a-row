package models;

import java.util.ArrayList;
import java.util.List;

import message.GameTick;
import message.PlayerJoin;
import message.PlayerLeave;
import messageloop.MessageLoop;

public abstract class Game {
    private Deck deck;
    private Player players[];
    private int level, levelmax;
    private Difficulty difficulty;
    private Question actualQuestion;

    private MessageLoop messageLoop;

    public Game(Deck deck) {
        setDeck(deck);
        setDifficulty(Difficulty.EASY);
        messageLoop = new MessageLoop();

        level = 0;
        players = new Player[4];
    }

    public boolean reply(String reply) {
        if (actualQuestion.toString() == reply) {
            level++;
            if (levelmax < level)
                level = levelmax;
            return true;
        }

        level = 0;
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
