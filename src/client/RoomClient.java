/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

/**
 *
 * @author HUSAIN
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RoomClient extends JFrame {
    private String ip;
    private String username;
    private String targetClient;

    public RoomClient(String ip, String username, String targetClient) {
        this.ip = ip;
        this.username = username;
        this.targetClient = targetClient;

        setTitle("Chat with " + targetClient);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JTextField messageField = new JTextField();
        JButton sendButton = new JButton("Send");

        panel.add(messageField, BorderLayout.SOUTH);
        panel.add(sendButton, BorderLayout.EAST);

        add(panel);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageField.getText();
                if (!message.isEmpty()) {
                    chatArea.append(username + ": " + message + "\n");
                    messageField.setText("");
                }
            }
        });
    }
}
