/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package console;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

/**
 *
 * @author dr.gero
 */
public class connect extends Thread {
    
    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    List<Socket> consolesocket = new ArrayList<>();
    List<DataOutputStream> consoleout = new ArrayList<>();
    List<DataInputStream> consolein = new ArrayList<>();
    String command;
    TextArea terminalwindow, dpilog, dpolog;
    ObservableList identifications = FXCollections.observableArrayList();

    connect(String command, TextArea terminalwindow, List<Socket> consolesocket, List<DataInputStream> consolein, List<DataOutputStream> consoleout, TextArea dpilog, TextArea dpolog, ObservableList<identification> identifications) {
        this.command = command;
        this.terminalwindow = terminalwindow;
        this.consolesocket = consolesocket;
        this.consolein = consolein;
        this.consoleout = consoleout;
        this.dpilog = dpilog;
        this.dpolog = dpolog;
        this.identifications = identifications;
    }
    
    @Override
    public void run() {
        Scanner cmdscanner = new Scanner(command);
        cmdscanner.next();
        try { 
            String host = cmdscanner.next();
            int port = cmdscanner.nextInt();
            int request = cmdscanner.nextInt();
            for (int r=0; r<request; r++) {
                terminalwindow.appendText("\n├─ Connecting to " + host + " on port " + port + ".");
                try {
                    socket = new Socket(host, port);
                    terminalwindow.appendText("\n├── " + socket + " opened.");
                    consolesocket.add(socket);
                    in = new DataInputStream(socket.getInputStream());
                    terminalwindow.appendText("\n├── " + in + " opened.");
                    consolein.add(in);
                    out = new DataOutputStream(socket.getOutputStream());
                    terminalwindow.appendText("\n├── " + out + " opened.");
                    consoleout.add(out);
                    out.writeUTF("Console");
                    terminalwindow.appendText("\n├── Connected to " + host + " on port " + port + ".");
                    terminalwindow.appendText("\n├─ Activating Data Protocol.");
                    new dataprotocol(terminalwindow, dpilog, dpolog, consolein, consoleout, identifications).start();
                    terminalwindow.appendText("\n└── Data Protocol activatied.");
                    break;
                } catch (UnknownHostException e) {
                    terminalwindow.appendText("\n├── " + e);
                    terminalwindow.appendText("\n└── Host " + host + " unknown.");
                    break;
                } catch (IOException e) {
                    terminalwindow.appendText("\n├── " + e);
                    if (r+1!=request) {
                        terminalwindow.appendText("\n├── " + host + " on port " + port + " is offline.");
                    } else {
                        terminalwindow.appendText("\n└── " + host + " on port " + port + " is offline.");
                    }
                } catch (IllegalArgumentException e) {
                    terminalwindow.appendText("\n├── " + e);
                    terminalwindow.appendText("\n└── Port " + port + " is out of range.");
                    break;
                }
            }
        } catch (Exception ex) {
            System.err.println(ex);
            terminalwindow.appendText("\n└─ Syntax Error: connect <host> <port> <# of request>");
        }
    }
    
}
