/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dr.gero
 */
public class ClientHandler extends Thread {
    
    String client;
    Socket clientsocket;
    
    List<String> consoles = new ArrayList<>();
    List<Socket> consolessocket = new ArrayList<>();
    List<DataInputStream> consolesin = new ArrayList<>();
    List<DataOutputStream> consolesout = new ArrayList<>();
    
    List<String> consolesIO = new ArrayList<>();
    List<Socket> consolesIOsocket = new ArrayList<>();
    List<DataInputStream> consolesIOin = new ArrayList<>();
    List<DataOutputStream> consolesIOout = new ArrayList<>();
    
    List<String> androids = new ArrayList<>();
    List<Socket> androidssocket = new ArrayList<>();
    List<DataInputStream> androidsin = new ArrayList<>();
    List<DataOutputStream> androidsout = new ArrayList<>();
    
    List<String> androidsIO = new ArrayList<>();
    List<Socket> androidsIOsocket = new ArrayList<>();
    List<DataInputStream> androidsIOin = new ArrayList<>();
    List<DataOutputStream> androidsIOout = new ArrayList<>();

    ClientHandler(Socket clientsocket, List<String> consoles, List<Socket> consolessocket, List<DataInputStream> consolesin, List<DataOutputStream> consolesout, List<String> consolesIO, List<Socket> consolesIOsocket, List<DataInputStream> consolesIOin, List<DataOutputStream> consolesIOout, List<String> androids, List<Socket> androidssocket, List<DataInputStream> androidsin, List<DataOutputStream> androidsout, List<String> androidsIO, List<Socket> androidsIOsocket, List<DataInputStream> androidsIOin, List<DataOutputStream> androidsIOout) {
        this.clientsocket = clientsocket;
        this.consoles = consoles;
        this.consolessocket = consolessocket;
        this.consolesin = consolesin;
        this.consolesout = consolesout;
        this.consolesIO = consolesIO;
        this.consolesIOsocket = consolesIOsocket;
        this.consolesIOin = consolesIOin;
        this.consolesIOout = consolesIOout;
        this.androids = androids;
        this.androidssocket = androidssocket;
        this.androidsin = androidsin;
        this.androidsout = androidsout;
        this.androidsIO = androidsIO;
        this.androidsIOsocket = androidsIOsocket;
        this.androidsIOin = androidsIOin;
        this.androidsIOout = androidsIOout;
    }
    
    @Override
    public void run() {
        try {
            DataInputStream clientin = new DataInputStream(clientsocket.getInputStream());
            DataOutputStream clientout = new DataOutputStream(clientsocket.getOutputStream());
            client = clientin.readUTF();
            if (client.startsWith("Console")) {
                if (client.endsWith("livestreaming")||client.endsWith("filetransfer")) {
                    
                } else {
                    new ConsoleHandler(client, clientsocket, clientin, clientout,
                        consoles, consolessocket, consolesin, consolesout,
                        consolesIO, consolesIOsocket, consolesIOin, consolesIOout,
                        androids, androidssocket, androidsin, androidsout,
                        androidsIO, androidsIOsocket, androidsIOin, androidsIOout).start();
                }
            } else if (client.startsWith("Android")) {
                if (client.endsWith("livestreaming")||client.endsWith("filetransfer")) {
                    
                } else {
                    new AndroidHandler(client, clientsocket, clientin, clientout,
                        consoles, consolessocket, consolesin, consolesout,
                        consolesIO, consolesIOsocket, consolesIOin, consolesIOout,
                        androids, androidssocket, androidsin, androidsout,
                        androidsIO, androidsIOsocket, androidsIOin, androidsIOout).start();
                }
            } else {
                clientsocket.close();
                System.out.println("unidentfied client " + clientsocket.getInetAddress() + " terminated");
            }
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            
        }
    }
    
}
