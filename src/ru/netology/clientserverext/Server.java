package ru.netology.clientserverext;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static String userName = "";

    public static void main(String[] args) {
        System.out.println("Server started!");
        final int PORT = 9090;

        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(PORT);
                 Socket clientSocket = serverSocket.accept();
                 PrintWriter sockOut = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader bufRead = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                if (clientSocket.isConnected()) {
                    //sockOut.println("Hi %s, your port is: " + clientSocket.getPort());
                    System.out.println("New connection accepted");
                    sockOut.println("Write your name");
                }

                // Processing client messages
                sockProcessingHandler(sockOut, bufRead);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static void sockProcessingHandler(PrintWriter sockOut, BufferedReader sockRead) {
        String cmd = read(sockRead);
        if (userName.isEmpty()) {
            userName = cmd;
            send(sockOut, "Are you child? (yes/no)");
        }

        cmd = read(sockRead);
        if (cmd.equals("yes")) {
            send(sockOut, "Welcome to the kids area, " + userName + "! Let's play!");
            userName = "";
        } else if (cmd.equals("no")) {
            send(sockOut, "Welcome to the adult zone, " + userName + "! Have a good rest, or a good working day!");
            userName = "";
        }
    }

    private static String read(BufferedReader sockRead) {
        String result = null;
        try {
            result = sockRead.readLine();
            System.out.println(result);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    private static void send(PrintWriter sockOut, String message) {
        sockOut.println(message);
    }
}
