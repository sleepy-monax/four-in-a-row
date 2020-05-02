package network;

public enum PacketType {
    LOGIN, ACCEPTED, TICK, PLAYER_JOIN, PLAYER_LEAVE, GAME_MESSAGE,

    PLAYER_SELECT_THEME,
    PLAYER_SELECT_MISTERY_THEME,
    PLAYER_ANSWER,
    PLAYER_PASS;

    public static final PacketType[] fastValues = values();
}
