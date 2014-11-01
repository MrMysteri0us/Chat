package de.mrmysteri0us.chat;

import de.mrmysteri0us.chat.connection.Connection;
import de.mrmysteri0us.chat.connection.ConnectionHandler;
import de.mrmysteri0us.chat.packet.Packet3Chat;
import de.mrmysteri0us.chat.packet.Packet4Disconnect;

import java.net.Socket;
import java.util.Scanner;

/**
 * Created by robin on 16/10/2014
 */
public class ChatClient implements Runnable {
    private static ChatClient clientInstance;

    private Socket              clientSocket;
    private Connection          connection;
    private ConnectionHandler   connectionHandler;
    private String              username;

    public static void main(String[] args) {
        clientInstance = new ChatClient();
        clientInstance.run();
    }

    @Override
    public void run() {
        boolean isRunning = true;
        Scanner scanner = new Scanner(System.in);

        while (isRunning) {
            if (!scanner.hasNextLine()) {
                continue;
            }

            String input = scanner.nextLine();

            if(connectionHandler != null) {
                connectionHandler.sendPacket(new Packet3Chat(username, input));
            }

            String[] commandArray = input.split(" ");
            String command = commandArray[0];

            // !connect <username> <server-ip> <server-port>
            if (command.equals("!connect")) {
                if (commandArray.length == 4) {
                    username = commandArray[1];

                    try {
                        clientSocket = new Socket(
                                commandArray[2],
                                Integer.parseInt(commandArray[3])
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    connection = new Connection(clientSocket);
                    connection.start();
                    connectionHandler = connection.getConnectionHandler();
                } else {
                    System.out.println("Usage: !connect <username> <server-ip> <server-port>");
                }
            }

            // !disconnect
            if (command.equals("!disconnect")) {
                if (commandArray.length == 1) {
                    connectionHandler.sendPacket(new Packet4Disconnect(username));
                    isRunning = false;
                } else {
                    System.out.println("Usage: !disconnect");
                }
            }
        }

        connection.setStatus(false);

        try {
            connection.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        scanner.close();
    }

    public static ChatClient getClient() {
        return clientInstance;
    }

    public String getUsername() {
        return username;
    }
}
