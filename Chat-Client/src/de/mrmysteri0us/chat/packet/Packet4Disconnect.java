package de.mrmysteri0us.chat.packet;

/**
 * Created by robin on 16/10/2014
 */
public class Packet4Disconnect extends Packet {
    private String username;

    public Packet4Disconnect(String username) {
        super(4);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
