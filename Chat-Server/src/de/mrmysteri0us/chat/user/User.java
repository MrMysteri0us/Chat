package de.mrmysteri0us.chat.user;

import de.mrmysteri0us.chat.ChatServer;
import de.mrmysteri0us.chat.connection.Connection;

/**
 * Created by robin on 16/10/2014
 */
public class User {
    private Connection  connection;
    private String      name;

    public User(Connection connection, String name) {
        this.connection = connection;
        this.name = name;
        ChatServer.getServer().getUsers().add(this);
    }

    public Connection getConnection() {
        return connection;
    }

    public String getName() {
        return name;
    }
}
