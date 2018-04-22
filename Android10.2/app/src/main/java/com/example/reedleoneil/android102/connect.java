package com.example.reedleoneil.android102;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ReedLeoneil on 9/15/2014.
 */
public class connect extends Thread {

    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    List<Socket> androidsocket = new ArrayList<Socket>();
    List<DataOutputStream> androidout = new ArrayList<DataOutputStream>();
    List<DataInputStream> androidin = new ArrayList<DataInputStream>();

    connect(List<Socket> androidsocket, List<DataInputStream> androidin, List<DataOutputStream> androidout) {
        this.androidin = androidin;
        this.androidout = androidout;
        this.androidsocket = androidsocket;
    }

    @Override
    public void run() {
        try {
            String host = "192.168.2.20";
            int port = 1994;
            int request = 1;
            for (int r=0; r<request; r++) {
                try {
                    socket = new Socket(host, port);
                    androidsocket.add(socket);
                    in = new DataInputStream(socket.getInputStream());
                    androidin.add(in);
                    out = new DataOutputStream(socket.getOutputStream());
                    androidout.add(out);
                    out.writeUTF("Android 10");
                } catch (Exception e) {
                }
            }
            while (true) {
                String data = null;
                try {
                    data = in.readUTF();
                    System.out.println(data);

                    if (data.equals("<identify>")) {
                        out.writeUTF("<identification>Android 10;Online;192.168.2.10;Android 4.0.4;PH;Sony Live;WT19i;2.6.32.9");
                    } else {
                        out.writeUTF("<we always find a way>training is success;complaining is failure");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {

        }
    }

}