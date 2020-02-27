package network;

public enum PacketType {
    LOGIN, ACCEPTED, PLAYER_JOIN, PLAYER_LEAVE;

    public static final PacketType[] fastValues = values();
}
