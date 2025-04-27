/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

/**
 *
 * @author HUSAIN
 */
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.JTextArea;

public class Server implements Runnable {
    private int port;
    private ServerSocket serverSocket;
    private Map<String, String> users = new HashMap<>();
    private JTextArea logArea;
    private boolean running;

    public Server(int port, JTextArea logArea) {
        this.port = port;
        this.logArea = logArea;
        this.running = false;
    }

    @Override
    public void run() {
        try {
            if (running) {
                logArea.append("Server is already running...\n");
                return;
            }

            serverSocket = new ServerSocket(port);
            running = true;
            logArea.append("Server started on port " + port + "\n");

            while (running) {
                Socket clientSocket = serverSocket.accept();
                logArea.append("New client connected: " + clientSocket.getInetAddress() + "\n");
                new ClientHandler(clientSocket, users, logArea).start();
            }
        } catch (IOException e) {
            if (running) {
                logArea.append("Error starting server: " + e.getMessage() + "\n");
            }
        }
    }

    public void stopServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                running = false;
                logArea.append("Server stopped.\n");
            }
        } catch (IOException e) {
            logArea.append("Error stopping server: " + e.getMessage() + "\n");
        }
    }
}

class ClientHandler extends Thread {
    private Socket clientSocket;
    private DataInputStream input;
    private DataOutputStream output;
    private Map<String, String> users;
    private JTextArea logArea;

    public ClientHandler(Socket clientSocket, Map<String, String> users, JTextArea logArea) {
        this.clientSocket = clientSocket;
        this.users = users;
        this.logArea = logArea;
    }

    @Override
    public void run() {
        try {
            input = new DataInputStream(clientSocket.getInputStream());
            output = new DataOutputStream(clientSocket.getOutputStream());

            String messageType = input.readUTF();
            if ("REGISTER".equals(messageType)) {
                handleRegistration();
            }
            // Implement login or other actions similarly
        } catch (IOException e) {
            logArea.append("Error handling client: " + e.getMessage() + "\n");
        }
    }

    private void handleRegistration() throws IOException {
        String username = input.readUTF();
        String password = input.readUTF();
        String displayName = input.readUTF();

        if (users.containsKey(username)) {
            output.writeUTF("Username already exists");
        } else {
            users.put(username, password);
            output.writeUTF("Registration Success");
        }
    }
}
