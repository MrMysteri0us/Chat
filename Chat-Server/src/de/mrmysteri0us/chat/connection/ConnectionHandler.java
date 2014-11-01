package de.mrmysteri0us.chat.connection;

import de.mrmysteri0us.chat.packet.Packet;
import de.mrmysteri0us.chat.packet.Packet1Handshake;
import de.mrmysteri0us.chat.packet.Packet3Chat;
import de.mrmysteri0us.chat.packet.Packet4Disconnect;
import de.mrmysteri0us.chat.user.User;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by robin on 16/10/2014
 */
public class ConnectionHandler {

    public void sendPacket(Connection connection, Packet packet) {
        try {
            ObjectOutputStream stream = connection.getOutputStream();
            stream.writeObject(packet);
            stream.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void handlePacket(Connection connection, Packet packet) {
        if(packet instanceof Packet1Handshake) {
            handleHandshake(connection, (Packet1Handshake) packet);
        }
        if(packet instanceof Packet3Chat) {
            handleChat(connection, (Packet3Chat) packet);
        }
        if(packet instanceof Packet4Disconnect) {
            handleDisconnect(connection, (Packet4Disconnect) packet);
        }
    }

    public void handleHandshake(Connection connection, Packet1Handshake packet) {
        connection.setUser(new User(connection, packet.getUsername()));
        System.out.println(packet.getUsername() + " connected.");
    }

    public void handleChat(Connection connection, Packet3Chat packet) {
        //TODO -> send to all users
        System.out.println(packet.getUsername() + "> " + packet.getMessage());
    }

    public void handleDisconnect(Connection connection, Packet4Disconnect packet) {
        connection.setStatus(false);
        System.out.println(packet.getUsername() + " disconnected.");
    }
}
