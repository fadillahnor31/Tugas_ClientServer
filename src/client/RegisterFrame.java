package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField displayNameField;
    private JButton registerButton, backButton;
    private String ip;
    private int port;

    public RegisterFrame(String ip, int port) {
        this.ip = ip;
        this.port = port;

        setTitle("Register");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on screen

        JPanel panel = new JPanel(new GridLayout(5, 1));

        panel.add(new JLabel("Display Name:"));
        displayNameField = new JTextField();
        panel.add(displayNameField);

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        registerButton = new JButton("Register");
        panel.add(registerButton);

        // Add Back Button
        backButton = new JButton("Kembali");
        panel.add(backButton);

        add(panel);

        // ActionListener for Register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String displayName = displayNameField.getText();

                if (username.isEmpty() || password.isEmpty() || displayName.isEmpty()) {
                    JOptionPane.showMessageDialog(RegisterFrame.this, "Please fill in all fields.");
                    return;
                }

                if (registerUser(username, password, displayName)) {
                    JOptionPane.showMessageDialog(RegisterFrame.this, "Registration Successful!");
                    new LoginFrame(ip, port).setVisible(true); // After registration, open login screen
                    dispose(); // Close current register frame
                } else {
                    JOptionPane.showMessageDialog(RegisterFrame.this, "Username already exists.");
                }
            }
        });

        // ActionListener for Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrame(ip, port).setVisible(true); // Go back to LoginFrame
                dispose(); // Close current register frame
            }
        });
    }

    // Method to register user
    private boolean registerUser(String username, String password, String displayName) {
        try (Socket socket = new Socket(ip, port);
             DataOutputStream output = new DataOutputStream(socket.getOutputStream());
             DataInputStream input = new DataInputStream(socket.getInputStream())) {

            // Send registration request
            output.writeUTF("REGISTER");

            // Send user details
            output.writeUTF(username);
            output.writeUTF(password);
            output.writeUTF(displayName);

            // Get response from server
            String response = input.readUTF();
            return "Registration Success".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Return false if there is any issue during registration process
        }
    }

    public static void main(String[] args) {
        // Example IP and port, replace with actual values
        String serverIP = "127.0.0.1";
        int serverPort = 5555;
        new RegisterFrame(serverIP, serverPort).setVisible(true);
    }
}
