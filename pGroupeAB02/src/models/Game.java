package models;

import java.util.ArrayList;
import java.util.List;

import message.OnGameFinished;
import message.OnPlayerAnswer;
import message.OnThemeSelected;
import message.OnGameEnterLobby;
import message.PlayerJoin;
import message.PlayerLeave;
import messageloop.MessageLoop;
import states.Finish;
import states.GameState;
import states.Passive;
import states.Round;
import states.SelectTheme;

public class Game {
    private Deck deck;
    private Player players[];
    private MessageLoop messageLoop;
    private GameState state;
    private Difficulty difficulty;

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

    public void startPassive() {
        changeState(new Passive());
    }

    public void finish() {
        messageLoop.post(new OnGameFinished());
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

        changeState(new Finish());
    }

    public void selectTheme(String theme) {
        messageLoop.post(new OnThemeSelected(theme));

        if (state instanceof SelectTheme) {
            ((SelectTheme) state).pickTheme(theme);
        }
    }

    public void answer(String answer) {
        messageLoop.post(new OnPlayerAnswer(answer));

        if (state instanceof Round) {
            ((Round) state).answer(answer);
        }
    }

    public void enterLobby() {
        messageLoop.post(new OnGameEnterLobby());
    }
}
