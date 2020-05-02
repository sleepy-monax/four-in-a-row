package models;

import models.message.*;
import models.messageloop.MessageLoop;
import models.states.Finish;
import models.states.GameState;
import models.states.SelectTheme;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Deck deck;
    private int localPlayer = -1;
    private final Player[] players;
    private final MessageLoop messageLoop;
    private GameState state;
    private final Difficulty difficulty;
    private boolean stopped = false;

    public Game(Deck deck, Difficulty difficulty) {
        setDeck(deck);

        this.deck = deck;
        this.messageLoop = new MessageLoop();
        this.players = new Player[4];
        this.difficulty = difficulty;
    }

    public void start() {
        nextPlayer();
    }

    public void startWidthState(GameState gameState) {
        changeState(gameState);
    }

    public void enterLobby() {
        messageLoop.post(new OnGameEnterLobby());
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

            messageLoop.post(new OnPlayerEvent(newPlayer, PlayerEvent.JOIN));

            return newPlayer;
        }

        return null;
    }

    public boolean removePlayer(Player player) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == player) {
                System.out.println(player + " leave the game lolololololo");
                state.quit(player);
                messageLoop.post(new OnPlayerEvent(player, PlayerEvent.LEAVE));
                System.out.println(player + " leave the game");

                players[i] = null;

                return true;
            }
        }

        return false;
    }

    public void removePlayer(int id) {
        removePlayer(players[id]);
    }

    public void tick() {
        if (state != null) {
            // FIXME: use real elapsed time...
            state.onTick(1.0);
        }
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

    public List<Player> getPlayers() {
        ArrayList<Player> playerList = new ArrayList<>(players.length);

        for (Player player : players) {
            if (player != null) {
                playerList.add(player);
            }
        }

        return playerList;
    }

    public Player getLocalPlayer() {
        if (localPlayer != -1) {
            return players[localPlayer];
        }

        return null;
    }

    public void setLocalPlayer(Player player) {
        localPlayer = player.getId();
    }

    public Player getPlayer(int id) {
        return players[id];
    }

    public MessageLoop getMessageLoop() {
        return messageLoop;
    }

    public <GameStateType> void changeState(GameStateType state) {
        if (this.state != null) {
            this.state.onSwitchOut();
        }

        this.state = (GameState) state;

        if (this.state != null) {
            this.state.onSwitchIn();
        }
    }

    public void nextPlayer() {
        for (Player player : players) {
            if (player != null && !player.hasPlayed()) {
                changeState(new SelectTheme(this, player));
                return;
            }
        }

        messageLoop.post(new OnGameFinished());
        changeState(new Finish());
    }

    public void selectTheme(String theme) {
        state.selectTheme(theme);
    }

    public void selectMisteryTheme() {
        state.selectMisteryTheme();
    }

    public void answer(String answer) {
        state.answer(answer);
    }

    public void pass() {
        state.pass();
    }

    public void shutdown() {
        stopped = true;
        messageLoop.post(new GameShutdown());
    }
}
