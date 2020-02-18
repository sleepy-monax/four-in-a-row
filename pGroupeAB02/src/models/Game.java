package models;

import java.util.List;

public class Game {

    private Deck deck;
    private Player p1;
    private List<Player> players;

    public Game (Deck deck, List<Player> players){
    
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


}
