/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dr.gero
 */
public class Server {
    
    static List<String> consoles = new ArrayList<>();
    static List<Socket> consolessocket = new ArrayList<>();
    static List<DataInputStream> consolesin = new ArrayList<>();
    static List<DataOutputStream> consolesout = new ArrayList<>();
    
    static List<String> consolesIO = new ArrayList<>();
    static List<Socket> consolesIOsocket = new ArrayList<>();
    static List<DataInputStream> consolesIOin = new ArrayList<>();
    static List<DataOutputStream> consolesIOout = new ArrayList<>();
    
    static List<String> androids = new ArrayList<>();
    static List<Socket> androidssocket = new ArrayList<>();
    static List<DataInputStream> androidsin = new ArrayList<>();
    static List<DataOutputStream> androidsout = new ArrayList<>();
    
    static List<String> androidsIO = new ArrayList<>();
    static List<Socket> androidsIOsocket = new ArrayList<>();
    static List<DataInputStream> androidsIOin = new ArrayList<>();
    static List<DataOutputStream> androidsIOout = new ArrayList<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // ►◄ Server 1.1
        System.out.println("►◄ Server 1.1");
        try (ServerSocket serversocket = new ServerSocket(1994);) { 
            while (true) {
                new ClientHandler(
                        serversocket.accept(),
                        consoles,consolessocket,consolesin,consolesout,
                        consolesIO,consolesIOsocket,consolesIOin,consolesIOout,
                        androids,androidssocket,androidsin,androidsout,
                        androidsIO,androidsIOsocket,androidsIOin,androidsIOout
                ).start();
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
    
}
