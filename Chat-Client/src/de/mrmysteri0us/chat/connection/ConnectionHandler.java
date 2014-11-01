package de.mrmysteri0us.chat.connection;

import de.mrmysteri0us.chat.packet.Packet;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by robin on 16/10/2014
 */
public class ConnectionHandler {
    private Connection connection;

    public ConnectionHandler(Connection connection) {
        this.connection = connection;
    }

    public void sendPacket(Packet packet) {
        try {
            ObjectOutputStream stream = connection.getOutputStream();
            stream.writeObject(packet);
            stream.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void handlePacket(Packet packet) {
        //TODO -> handle packets (Packet3Chat, ...)
    }
}
