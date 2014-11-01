package de.mrmysteri0us.chat.packet;

/**
 * Created by robin on 17/10/2014
 */
public class Packet3Chat extends Packet {
    private String username;
    private String message;

    public Packet3Chat(String username, String message) {
        super(3);
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}
