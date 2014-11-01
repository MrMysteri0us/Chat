package de.mrmysteri0us.chat.packet;

/**
 * Created by robin on 16/10/2014
 */
public class Packet5Kick extends Packet {
    private String kickReason;

    public Packet5Kick(String kickReason) {
        super(5);
    }

    public String getKickReason() {
        return kickReason;
    }
}
