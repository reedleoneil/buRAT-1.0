/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package android8;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 *
 * @author dr.gero
 */
public class Android8 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // ►◄ Android 8.0
        Android8 android8 = new Android8();
        android8.initiate();
    }

    void initiate() {
        while (true) {
            try (Socket androidsocket = new Socket("192.168.2.20",1994)) {
                DataInputStream androidin = new DataInputStream(androidsocket.getInputStream());
                DataOutputStream androidout = new DataOutputStream(androidsocket.getOutputStream());
                androidout.writeUTF("Android 8");
                while (true) {
                    String data = androidin.readUTF();
                    System.out.println(data);
                    if (data.equals("<identify>")) {
                        androidout.writeUTF("<identification>Android 8;Online;192.168.2.8;Windows XP;PH;RoboLab;RoboLab-PC;7.7.7");
                    } else if (data.equals("<shutdown>")||(data.equals("<restart>"))||data.equals("<logoff>")) {
                        Runtime r = Runtime.getRuntime();
                        if (data.equals("<shutdown>")) {
                            r.exec("shutdown -s -t 0");
                        } else if (data.equals("<restart>")) {
                            r.exec("shutdown -r -t 0");
                        }
                    }
                }
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }
    }
    
}
