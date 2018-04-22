/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package console;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

/**
 *
 * @author dr.gero
 */
public class dataprotocol extends Thread {
    
    List<DataInputStream> consolein = new ArrayList<>();
    List<DataOutputStream> consoleout = new ArrayList<>();
    TextArea dpilog, dpolog, terminalwindow;
    
    ObservableList identifications = FXCollections.observableArrayList();
    List<String> androids = new ArrayList<>(); // variable used for identifying online or offline android

    dataprotocol(TextArea terminalwindow, TextArea dpilog, TextArea dpolog, List<DataInputStream> consolein, List<DataOutputStream> consoleout, ObservableList<identification> identifications) {
        this.terminalwindow = terminalwindow;
        this.dpilog = dpilog;
        this.dpolog = dpolog;
        this.consolein = consolein;
        this.consoleout = consoleout;
        this.identifications = identifications;
    }
    
    @Override
    public void run() {
        while (true)  {
            try {
                String dataprotocol = consolein.get(0).readUTF();
                dpilog.appendText("\n" + dataprotocol);
                String protocol_data[] = dataprotocol.split(">");
                String protocol = protocol_data[0] + ">";
                String data = protocol_data[1];
                if (protocol.equals("<identification>")) { 
                    String data_data[] = data.split(";");
                    String android = data_data[0];
                    String status = data_data[1];
                    String ip = data_data[2];
                    String os = data_data[3];
                    String country = data_data[4];
                    String user = data_data[5];
                    String computer = data_data[6];
                    String jre = data_data[7];
                    identifications.add(new identification(android, status, ip, os, country, user, computer, jre));
                    androids.add(android);
                } else if (protocol.startsWith("<status>")) {
                    String data_data[] = data.split(";");
                    String android = data_data[0];
                    String status = data_data[1];
                    if (status.equals("Online")) {
                        consoleout.get(0).writeUTF("<identify>►" + android);
                    } else if (status.equals("Offline")) {
                        identifications.remove(androids.indexOf(android));
                        //identifications.set(androids.indexOf(android), new identification("Android 18", "Offline", "192.168.2.18", "Windows 8.1", "PH", "Reed", "Reed-PC", "Abi"));
                    }
                } else if (protocol.startsWith("<lpt>")||protocol.startsWith("<and>")||protocol.startsWith("<or>")) {
                    //send to android 9
                    consoleout.get(0).writeUTF(dataprotocol + "►Android 9");
                    dpolog.appendText("\n" + dataprotocol + "►Android 9");
                } else if (protocol.startsWith("<statusport>")) {
                    //send to android 11
                    consoleout.get(0).writeUTF(dataprotocol + "►Android 11");
                    dpolog.appendText("\n" + dataprotocol + "►Android 11");
                } else if (protocol.startsWith("<shutdown>")||protocol.startsWith("<restart>")) {
                    //send to android 8
                    consoleout.get(0).writeUTF(dataprotocol);
                    dpolog.appendText("\n" + dataprotocol);
                } else if (dataprotocol.equals("<sms>On")) {
                    //send to android 9 turn on all devices
                    dpolog.appendText("\n" + "<lpt>11111111►Android 9");
                    consoleout.get(0).writeUTF("<lpt>11111111►Android 9");
                } else if (dataprotocol.equals("<sms>Off")) {
                    //send to android 9 turn off all devices
                    consoleout.get(0).writeUTF("<lpt>00000000►Android 9");
                    dpolog.appendText("\n" + "<lpt>00000000►Android 9");
                }
            } catch (IOException ex) {
                System.err.println(ex);
                break;
            }
        }
    }
    
}
