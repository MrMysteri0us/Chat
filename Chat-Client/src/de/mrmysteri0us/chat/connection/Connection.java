package de.mrmysteri0us.chat.connection;

import de.mrmysteri0us.chat.ChatClient;
import de.mrmysteri0us.chat.packet.Packet;
import de.mrmysteri0us.chat.packet.Packet1Handshake;
import de.mrmysteri0us.chat.packet.Packet2KeepAlive;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by robin on 16/10/2014
 */
public class Connection extends Thread {
    private Socket              socket;
    private ObjectInputStream   inputStream;
    private ObjectOutputStream  outputStream;
    private ConnectionHandler   connectionHandler;
    private boolean             isAlive;

    public Connection(Socket socket) {
        this.socket = socket;
        this.connectionHandler = new ConnectionHandler(this);

        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.flush();
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        isAlive = true;
        long lastKASent = System.currentTimeMillis();
        long lastKAReceived = System.currentTimeMillis();
        ChatClient client = ChatClient.getClient();

        connectionHandler.sendPacket(new Packet1Handshake(client.getUsername()));
        System.out.println(">>> Connected!");

        while(isAlive) {
            try {
                long time = System.currentTimeMillis();

                if(time - lastKAReceived >= 10000) {
                    isAlive = false;
                    System.out.println(">>> Timed out!");
                }
                if(time - lastKASent >= 1000) {
                    connectionHandler.sendPacket(new Packet2KeepAlive());
                }

                if(inputStream.available() == -1) {
                    continue;
                }

                Packet packet = (Packet) inputStream.readObject();
                connectionHandler.handlePacket(packet);

                if(packet instanceof Packet2KeepAlive) {
                    lastKAReceived = time;
                }

                Thread.sleep(50L);
            } catch(Exception e) {
                isAlive = false;
                e.printStackTrace();
            }
        }

        System.out.println(">>> Disconnected!");
    }

    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    public void setStatus(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public boolean getStatus() {
        return isAlive;
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
