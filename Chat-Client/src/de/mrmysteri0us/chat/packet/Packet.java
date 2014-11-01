package de.mrmysteri0us.chat.packet;

import java.io.Serializable;

/**
 * Created by robin on 16/10/2014
 */
public class Packet implements Serializable {
    protected int packetId;
    protected long packetTimestamp;

    public Packet(int packetId) {
        this.packetId = packetId;
        this.packetTimestamp = System.currentTimeMillis();
    }

    public int getPacketId() {
        return packetId;
    }

    public long getPacketTimestamp() {
        return packetTimestamp;
    }
}
