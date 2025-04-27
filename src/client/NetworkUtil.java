/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

/**
 *
 * @author HUSAIN
 */
import java.net.*;
import java.io.*;

public class NetworkUtil {

    // Fungsi untuk mendapatkan IP lokal yang terhubung ke jaringan
    public static String getLocalIPAddress() {
        try {
            // Mendapatkan alamat IP lokal menggunakan InetAddress.getLocalHost()
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "127.0.0.1";  // Mengembalikan localhost jika ada error
        }
    }
}
