package ru.netology.clientserverext;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        final String HOST = "netology.homework";
        final int PORT = 9090;

        try (Socket clientSocket = new Socket(HOST, PORT);
             PrintWriter sockOut = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader bufRead = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            // Processing server messages
            sockProcessingHandler(sockOut, bufRead);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void sockProcessingHandler(PrintWriter sockOut, BufferedReader sockRead) {
        while (true) {
            String cmd = read(sockRead);
            if (cmd.equals("Write your name")) send(sockOut, "User");
            else if (cmd.equals("Are you child? (yes/no)")) send(sockOut, "yes");
            else break;
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
