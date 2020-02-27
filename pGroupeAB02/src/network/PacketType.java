package network;

public enum PacketType {
    LOGIN, ACCEPTED, PLAYER_JOIN;

    public static final PacketType[] fastValues = values();
}
