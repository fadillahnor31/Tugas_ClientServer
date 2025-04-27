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

public class DashboardClient extends JFrame {
    private String ip;
    private String username;

    public DashboardClient(String ip, String username) {
        this.ip = ip;
        this.username = username;

        setTitle("Dashboard - " + username);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        String[] clients = {"Client 1", "Client 2", "Client 3"};
        JList<String> clientList = new JList<>(clients);
        JScrollPane scrollPane = new JScrollPane(clientList);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton chatButton = new JButton("Start Chat");
        JButton logoutButton = new JButton("Logout");

        panel.add(chatButton, BorderLayout.SOUTH);
        panel.add(logoutButton, BorderLayout.NORTH);

        add(panel);

        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedClient = clientList.getSelectedValue();
                if (selectedClient != null) {
                    new RoomClient(ip, username, selectedClient).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(DashboardClient.this, "Please select a client.");
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ConnectionServer().setVisible(true);
            }
        });
    }
}
