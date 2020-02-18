package models;

import java.util.List;

public class Game {

    private Deck deck;
    private Player p1;
    private List<Player> players;

    public Game (Deck deck, List<Player> players){
        setDeck(deck);
        setPlayers(players);
    }

    public boolean addPlayer(Player p1){
        if(!players.contains(p1)) {
            players.add(p1);
            return true;
        }
        return false;
    }

    public boolean removePlayer(Player p1){
        if (players.contains(p1)){
            players.remove(p1);
            return true;
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
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
