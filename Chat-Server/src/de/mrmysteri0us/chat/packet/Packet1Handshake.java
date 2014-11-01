package de.mrmysteri0us.chat.packet;

/**
 * Created by robin on 16/10/2014
 */
public class Packet1Handshake extends Packet {
    private String username;

    public Packet1Handshake(String username) {
        super(1);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
