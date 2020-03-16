package network;

public enum PacketType {
    LOGIN, ACCEPTED, TICK, PLAYER_JOIN, PLAYER_LEAVE, GAME_MESSAGE;

    public static final PacketType[] fastValues = values();
}
