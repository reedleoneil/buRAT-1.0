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
public class ConsoleHandler extends Thread {
    
    String console;
    Socket consolesocket;
    DataInputStream consolein;
    DataOutputStream consoleout;
    
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

    ConsoleHandler(String client, Socket clientsocket, DataInputStream clientin, DataOutputStream clientout, List<String> consoles, List<Socket> consolessocket, List<DataInputStream> consolesin, List<DataOutputStream> consolesout, List<String> consolesIO, List<Socket> consolesIOsocket, List<DataInputStream> consolesIOin, List<DataOutputStream> consolesIOout, List<String> androids, List<Socket> androidssocket, List<DataInputStream> androidsin, List<DataOutputStream> androidsout, List<String> androidsIO, List<Socket> androidsIOsocket, List<DataInputStream> androidsIOin, List<DataOutputStream> androidsIOout) {
        this.console = client;
        this.consolesocket = clientsocket;
        this.consolein = clientin;
        this.consoleout = clientout;
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
    public void run () {
        consoles.add(console);
        consolessocket.add(consolesocket);
        consolesin.add(consolein);
        consolesout.add(consoleout);
        System.out.println(console + " online.");
        for (String android : androids) {
            try {
                consoleout.writeUTF("<status>" + android + ";" + "Online");
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        
        try {
            while (true) {
                String datain = consolein.readUTF();
                if (datain.contains("►")) {
                    String[] args = datain.split("►");
                    String[] recipients = args[1].split(";");
                    String[] datas = args[0].split(";");
                    for (String android : recipients) {
                        for (String data : datas) {
                            androidsout.get(this.androids.indexOf(android)).writeUTF(data);
                        }
                    }
                } else if (datain.contains("◄")) {
                    String[] args = datain.split("◄");
                    String[] recipients = args[1].split(";");
                    String[] datas = args[0].split(";");
                    for (String data : datas) {
                        for (String android : recipients) {
                            androidsout.get(this.androids.indexOf(android)).writeUTF(data);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(console + " offline.");
        } finally {
            consoles.remove(console);
            consolessocket.remove(consolesocket);
            consolesin.remove(consolein);
            consolesout.remove(consoleout);
            try {
                consolesocket.close();
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }
    }
    
}
