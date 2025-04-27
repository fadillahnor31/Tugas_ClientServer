package server;

import client.MainClient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ServerFrame extends JFrame {

    private JTextField ipField, portField;
    private JTextArea logArea;
    private JButton startButton, stopButton, openAppButton;
    private boolean isServerRunning = false;
    private Server server;

    public ServerFrame() {
        setTitle("Server Application");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel untuk IP dan Port
        JPanel connectionPanel = new JPanel(new GridLayout(3, 2));
        connectionPanel.add(new JLabel("Server IP:"));
        ipField = new JTextField(getLocalIPAddress()); // Menampilkan IP lokal yang terhubung
        ipField.setEditable(false); // Tidak bisa diubah oleh pengguna
        connectionPanel.add(ipField);

        connectionPanel.add(new JLabel("Port:"));
        portField = new JTextField("3306");
        connectionPanel.add(portField);

        // Panel untuk Log Server
        logArea = new JTextArea(10, 30);
        logArea.setEditable(false);
        JScrollPane logScroll = new JScrollPane(logArea);

        // Panel untuk Tombol Server
        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Start Server");
        stopButton = new JButton("Stop Server");
        stopButton.setEnabled(false);
        openAppButton = new JButton("Open Client App");

        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(openAppButton);

        // Action Listeners
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
        });

        openAppButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openClientApp();
            }
        });

        // Layout untuk Frame
        setLayout(new BorderLayout());
        add(connectionPanel, BorderLayout.NORTH);
        add(logScroll, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private String getLocalIPAddress() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress(); // Mendapatkan IP lokal yang terhubung
        } catch (UnknownHostException e) {
            return "127.0.0.1"; // Jika tidak bisa mendapatkan IP, gunakan localhost
        }
    }

    private void startServer() {
        String portStr = portField.getText();
        int port;

        try {
            port = Integer.parseInt(portStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid port number.");
            return;
        }

        if (isServerRunning) {
            JOptionPane.showMessageDialog(this, "Server is already running.");
            return;
        }

        // Memulai server baru di thread
        server = new Server(port, logArea);
        new Thread(server).start(); // Jalankan server di thread terpisah

        startButton.setEnabled(false); // Disable start button after server starts
        stopButton.setEnabled(true);   // Enable stop button
        isServerRunning = true;
        logArea.append("Server started on port " + port + "\n");
    }

    private void stopServer() {
        if (server != null) {
            server.stopServer();
            isServerRunning = false;
            startButton.setEnabled(true);  // Enable start button again
            stopButton.setEnabled(false); // Disable stop button
            logArea.append("Server stopped.\n");
        }
    }

    private void openClientApp() {
        try {
            // Membuka aplikasi client yang berisi LoginFrame, RegisterFrame, dll
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // Pastikan MainClient diakses melalui paket yang sesuai
                    client.ConnectionServer.main(new String[0]);  // Memanggil method main dari Client.MainClient
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to open client app. Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ServerFrame().setVisible(true);
            }
        });
    }
}
