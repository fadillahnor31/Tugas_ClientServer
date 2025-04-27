package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ConnectionServer extends JFrame {
    private JTextField ipField, portField;
    private JButton connectButton;
    private JLabel statusLabel;

    public ConnectionServer() {
        setTitle("Koneksi Server");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Panel untuk IP dan Port
        JPanel connectionPanel = new JPanel(new GridLayout(3, 2));
        connectionPanel.add(new JLabel("Server IP:"));
        ipField = new JTextField(getLocalIPAddress()); // Menampilkan IP lokal yang terhubung
        ipField.setEditable(true); // Dapat diubah oleh pengguna
        connectionPanel.add(ipField);

        connectionPanel.add(new JLabel("Port:"));
        portField = new JTextField("3306"); // Default port MySQL (ubah sesuai kebutuhan)
        connectionPanel.add(portField);

        // Status Label
        statusLabel = new JLabel("Status: Menunggu Koneksi...");
        connectionPanel.add(statusLabel);

        // Tombol Koneksi
        connectButton = new JButton("Connect");
        connectionPanel.add(connectButton);

        // Action Listener untuk tombol connect
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer(); // Menangani proses koneksi ke server
            }
        });

        // Layout untuk Frame
        setLayout(new BorderLayout());
        add(connectionPanel, BorderLayout.CENTER);
    }

    // Mendapatkan alamat IP lokal yang terhubung
    private String getLocalIPAddress() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress(); // Mendapatkan IP lokal
        } catch (UnknownHostException e) {
            return "127.0.0.1"; // Jika tidak bisa mendapatkan IP, gunakan localhost
        }
    }

    // Fungsi untuk mencoba terhubung ke server
    private void connectToServer() {
        String ip = ipField.getText(); // IP dari field
        String portStr = portField.getText(); // Port dari field
        int port;

        // Validasi port
        try {
            port = Integer.parseInt(portStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Port tidak valid. Harap masukkan angka yang valid.");
            return;
        }

        // Validasi IP dengan regex untuk format IP yang benar
        if (!isValidIP(ip)) {
            JOptionPane.showMessageDialog(this, "Alamat IP tidak valid. Harap masukkan alamat IP yang benar.");
            return;
        }

        // Mencoba untuk terhubung ke server
        try (Socket socket = new Socket(ip, port)) {
            statusLabel.setText("Status: Terkoneksi ke server");
            connectButton.setEnabled(false); // Disable tombol connect setelah terkoneksi

            // Setelah terhubung, buka LoginFrame dengan mengirimkan IP
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new LoginFrame(ip, port).setVisible(true); // Menampilkan LoginFrame dengan IP dan port yang valid
                    dispose(); // Menutup frame ConnectionServer
                }
            });

        } catch (IOException e) {
            statusLabel.setText("Status: Gagal terhubung ke server");
            JOptionPane.showMessageDialog(this, "Gagal terhubung ke server. Pastikan alamat IP dan port benar.");
        }
    }

    // Fungsi untuk memvalidasi alamat IP
    private boolean isValidIP(String ip) {
        String regex = "^([0-9]{1,3}\\.){3}[0-9]{1,3}$"; // Regex untuk validasi IP
        return ip.matches(regex);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ConnectionServer().setVisible(true); // Menjalankan ConnectionServer
            }
        });
    }
}
