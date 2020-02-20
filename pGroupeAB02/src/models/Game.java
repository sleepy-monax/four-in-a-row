package models;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Deck deck;
    private Player players[];

    private PendingGameListener playerListener;

    public Game(Deck deck) {
        setDeck(deck);

        players = new Player[4];
    }

    public Player joinPlayer(String name) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null) {
                Player newPlayer = new Player(i, name);
                players[i] = newPlayer;

                if (playerListener != null) {
                    playerListener.onPlayerJoin(i, name);
                }

                return newPlayer;
            }
        }

        return null;
    }

    public boolean removePlayer(Player player) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == player) {
                players = null;

                if (playerListener != null) {
                    playerListener.onPlayerLeave(i, player.getName());
                }

                return true;
            }
        }

        return false;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        if (deck != null)
            this.deck = deck;
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

    public void setPlayerListener(PendingGameListener playerListener) {
        this.playerListener = playerListener;
    }
}
