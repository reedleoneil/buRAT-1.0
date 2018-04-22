/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package console;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

/**
 *
 * @author dr.gero
 */
public class disconnect extends Thread {
    
    List<Socket> consolesocket = new ArrayList<>();
    List<DataOutputStream> consoleout = new ArrayList<>();
    List<DataInputStream> consolein = new ArrayList<>();
    TextArea terminalwindow;
    ObservableList identifications = FXCollections.observableArrayList();

    disconnect(TextArea terminalwindow, List<Socket> consolesocket, List<DataInputStream> consolein, List<DataOutputStream> consoleout, ObservableList<identification> identifications) {
        this.terminalwindow = terminalwindow;
        this.consolesocket = consolesocket;
        this.consolein = consolein;
        this.consoleout = consoleout;
        this.identifications = identifications;
    }
    
    @Override
    public void run() {
        try {
            terminalwindow.appendText("\n├─ Disconnecting to " + consolesocket.get(0).getInetAddress() + " on port " + consolesocket.get(0).getPort() + ".");
            consolesocket.get(0).close();
            terminalwindow.appendText("\n├── " + consolesocket.get(0) + " closed.");
            consolein.get(0).close();
            terminalwindow.appendText("\n├── " + consolein.get(0) + " closed.");
            consoleout.get(0).close();
            terminalwindow.appendText("\n├── " + consoleout.get(0) + " closed.");
            terminalwindow.appendText("\n├── Disconnected to " + consolesocket.get(0).getInetAddress() + " on port " + consolesocket.get(0).getPort() + ".");
            consolesocket.remove(0);
            consolein.remove(0);
            consoleout.remove(0);
            terminalwindow.appendText("\n├─ Deactivating Data Protocol."); //dataprotocol automatically deactivates when socket is closed
            identifications.clear();
            terminalwindow.appendText("\n└── Data Protocol deactivated.");
        } catch (Exception ex) {
            terminalwindow.appendText("\n└─ Disconnected: No existing connections.");
        }
    }
    
}
