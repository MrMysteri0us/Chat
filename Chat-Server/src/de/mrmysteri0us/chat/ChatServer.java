package de.mrmysteri0us.chat;

import de.mrmysteri0us.chat.config.Config;
import de.mrmysteri0us.chat.connection.Connection;
import de.mrmysteri0us.chat.connection.ConnectionHandler;
import de.mrmysteri0us.chat.user.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by robin on 16/10/2014
 */
public class ChatServer extends Thread {
    private static ChatServer serverInstance;

    private ServerSocket        serverSocket;
    private Config              serverConfig;
    private ConnectionHandler   connectionHandler;
    private List<Connection>    connectionList;
    private List<User>          userList;

    public static void main(String[] args) {
        serverInstance = new ChatServer();
        serverInstance.start();
    }

    public ChatServer() {
        serverConfig = new Config("server.cfg");
        connectionHandler = new ConnectionHandler();
        connectionList = new ArrayList<Connection>();
        userList = new ArrayList<User>();
    }

    @Override
    public void run() {
        boolean isRunning = true;

        serverConfig.addDefault("server-name", "Default Server");
        serverConfig.addDefault("server-port", "9001");

        try {
            serverConfig.load();
            serverSocket = new ServerSocket(serverConfig.getInt("server-port"));
        } catch(IOException e) {
            e.printStackTrace();
        }

        //TODO -> fix isRunning
        while(isRunning) {
            try {
                Socket socket = serverSocket.accept();

                if(socket != null) {
                    new Connection(socket).start();
                }

                for(Connection c : connectionList) {
                    if(c.getStatus()) {
                        continue;
                    }

                    c.join();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static ChatServer getServer() {
        return serverInstance;
    }

    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    public List<Connection> getConnections() {
        return connectionList;
    }

    public List<User> getUsers() {
        return userList;
    }
}
