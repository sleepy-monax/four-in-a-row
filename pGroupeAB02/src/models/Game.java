package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import message.GameTick;
import message.PlayerJoin;
import message.PlayerLeave;
import message.StateChanged;
import messageloop.MessageLoop;
import states.Finish;
import states.GameState;
import states.SelectTheme;

public abstract class Game {
    private Deck deck;
    private Player players[];
    private MessageLoop messageLoop;
    private GameState state;
    private Difficulty difficulty;

    public Game(Deck deck) {
        setDeck(deck);
        messageLoop = new MessageLoop();
        players = new Player[4];
    }

    public void letsPlay(Difficulty difficulty, int nbtheme) {
        randomTheme(nbtheme);
        setDifficulty(difficulty);
    }

    public List<String> randomTheme(int nb) {
        List<String> themeDeBase = new ArrayList<>(deck.getListThemes());
        List<String> themeRand = new ArrayList<>();
        for (int i = 0; i < nb; i++) {
            int rand = new Random().nextInt(themeDeBase.size() - 1);
            themeRand.add(themeDeBase.get(rand));
            themeDeBase.remove(rand);
        }
        return themeRand;
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

        if (state != null) {
            // FIXME: use real elapsed time...
            state.onTick(1.0);
        }
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

    public <GameStateType> void changeState(GameStateType state) {

        this.state = (GameState) state;

        messageLoop.post(new StateChanged<GameStateType>(state));
    }

    public void nextPlayer() {
        for (Player player : players) {
            if (!player.hasPlayed()) {
                changeState(new SelectTheme(this, player));
                return;
            }
        }

        changeState(new Finish());
    }
}
