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
public class AndroidHandler extends Thread {
    
    String android;
    Socket androidsocket;
    DataInputStream androidin;
    DataOutputStream androidout;
    
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

    AndroidHandler(String client, Socket clientsocket, DataInputStream clientin, DataOutputStream clientout, List<String> consoles, List<Socket> consolessocket, List<DataInputStream> consolesin, List<DataOutputStream> consolesout, List<String> consolesIO, List<Socket> consolesIOsocket, List<DataInputStream> consolesIOin, List<DataOutputStream> consolesIOout, List<String> androids, List<Socket> androidssocket, List<DataInputStream> androidsin, List<DataOutputStream> androidsout, List<String> androidsIO, List<Socket> androidsIOsocket, List<DataInputStream> androidsIOin, List<DataOutputStream> androidsIOout) {
        this.android = client;
        this.androidsocket = clientsocket;
        this.androidin = clientin;
        this.androidout = clientout;
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
        androids.add(android);
        androidssocket.add(androidsocket);
        androidsin.add(androidin);
        androidsout.add(androidout);
        System.out.println(android + " online.");
        try {
            consolesout.get(0).writeUTF("<status>" + android + ";" + "Online");
        } catch (Exception e) {
            //System.err.println("Console is offline: cannot send status.");
        }
        try {
            while (true) {
                String data = androidin.readUTF();
                consolesout.get(0).writeUTF(data);
            }
        } catch (Exception ex) {
            System.out.println(android + " offline.");
            try {
                consolesout.get(0).writeUTF("<status>" + android + ";" + "Offline");
            } catch (Exception e) {
               //System.err.println("Console is offline: cannot send status.");
            }
        } finally {
            androids.remove(android);
            androidssocket.remove(androidsocket);
            androidsin.remove(androidin);
            androidsout.remove(androidout);
            try {
                androidsocket.close();
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }
    }
    
}
