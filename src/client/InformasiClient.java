/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

/**
 *
 * @author HUSAIN
 */
import java.awt.*;
import javax.swing.*;

public class InformasiClient extends JFrame {
    public InformasiClient() {
        setTitle("Client Information");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextArea infoArea = new JTextArea();
        infoArea.setText("Informasi terkait client akan ditampilkan di sini...");
        infoArea.setEditable(false);

        panel.add(infoArea, BorderLayout.CENTER);
        add(panel);
    }
}
