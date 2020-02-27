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

    public Player joinPlayer(int id, String name) {
        if (players[id] == null) {
            System.out.println("Player " + id + " named " + name + " join the game");

            Player newPlayer = new Player(id, name);
            players[id] = newPlayer;

            if (playerListener != null) {
                playerListener.onPlayerJoin(id, name);
            } else {
                System.out.println("Event lost!");
            }

            return newPlayer;
        }

        return null;
    }

    public boolean removePlayer(Player player) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == player) {
                System.out.println("Player " + player.getId() + " named " + player.getName() + " leave the game");

                if (playerListener != null) {
                    playerListener.onPlayerLeave(i, player.getName());
                }

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

    public void attachListener(PendingGameListener playerListener) {
        this.playerListener = playerListener;
    }

    public void tick() {
        if (playerListener != null) {
            playerListener.onGameTick();
        }
    }
}
