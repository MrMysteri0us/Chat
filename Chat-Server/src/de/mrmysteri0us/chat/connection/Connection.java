package de.mrmysteri0us.chat.connection;

import de.mrmysteri0us.chat.ChatServer;
import de.mrmysteri0us.chat.packet.Packet;
import de.mrmysteri0us.chat.packet.Packet2KeepAlive;
import de.mrmysteri0us.chat.user.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by robin on 16/10/2014
 */
public class Connection extends Thread {
    private User                user;
    private Socket              socket;
    private ObjectInputStream   inputStream;
    private ObjectOutputStream  outputStream;
    private ConnectionHandler   connectionHandler;
    private boolean             isAlive;

    public Connection(Socket socket) {
        ChatServer server = ChatServer.getServer();
        this.user = null;
        this.socket = socket;
        this.connectionHandler = server.getConnectionHandler();
        server.getConnections().add(this);
    }

    @Override
    public void run() {
        isAlive = true;
        long lastKASent = System.currentTimeMillis();
        long lastKAReceived = System.currentTimeMillis();

        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.flush();
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch(IOException e) {
            e.printStackTrace();
        }

        System.out.println(socket.getInetAddress() + " -> connected!");

        while(isAlive) {
            try {
                long time = System.currentTimeMillis();

                if(time - lastKAReceived >= 10000) {
                    isAlive = false;
                }
                if(time - lastKASent >= 1000) {
                    connectionHandler.sendPacket(this, new Packet2KeepAlive());
                }

                if(inputStream.available() == -1) {
                    continue;
                }

                Packet packet = (Packet) inputStream.readObject();
                connectionHandler.handlePacket(this, packet);

                if(packet instanceof Packet2KeepAlive) {
                    lastKAReceived = time;
                }

                Thread.sleep(50L);
            } catch(Exception e) {
                isAlive = false;
                e.printStackTrace();
            }
        }

        System.out.println(socket.getInetAddress() + " -> disconnected!");
    }

    public void setStatus(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean getStatus() {
        return isAlive;
    }

    public User getUser() {
        return user;
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }
}
