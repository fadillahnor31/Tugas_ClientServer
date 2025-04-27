package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private String ip;
    private int port;

    public LoginFrame(String ip, int port) {
        this.ip = ip;
        this.port = port;

        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        panel.add(loginButton);

        // Add Register Button
        registerButton = new JButton("Register");
        panel.add(registerButton);

        add(panel);

        // ActionListener for Login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Validate fields
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Please fill in all fields.");
                    return;
                }

                if (loginUser(username, password)) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Login Successful!");
                    // Open the main application frame or another screen
                    // new MainAppFrame(ip).setVisible(true); // Example, uncomment this line for a main app
                    dispose();  // Close login window
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Invalid credentials. Please try again.");
                }
            }
        });

        // ActionListener for Register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open RegisterFrame
                new RegisterFrame(ip, port).setVisible(true);
                dispose();  // Close current LoginFrame
            }
        });
    }

    // Method to perform login
    private boolean loginUser(String username, String password) {
        try (Socket socket = new Socket(ip, port);
             DataOutputStream output = new DataOutputStream(socket.getOutputStream());
             DataInputStream input = new DataInputStream(socket.getInputStream())) {

            // Send login request
            System.out.println("Sending login request...");
            output.writeUTF("LOGIN");
            output.writeUTF(username);
            output.writeUTF(password);

            // Get server response
            String response = input.readUTF();
            System.out.println("Server Response: " + response);

            return "Login Success".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        // Example IP and port, replace with actual values
        String serverIP = "127.0.0.1";
        int serverPort = 5555;
        new LoginFrame(serverIP, serverPort).setVisible(true);
    }
}
