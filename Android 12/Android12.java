import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Android12 {

    public static void main(String[] args) {
      
        Android12 android12 = new Android12();
        android12.initiate();
    }

    void initiate() {
        while (true) {
            try (Socket androidsocket = new Socket("192.168.2.20",1994)) {
                DataInputStream androidin = new DataInputStream(androidsocket.getInputStream());
                DataOutputStream androidout = new DataOutputStream(androidsocket.getOutputStream());
                androidout.writeUTF("Android 12");
                while (true) {
                    String data = androidin.readUTF();
                    System.out.println(data);
                    if (data.equals("<identify>")) {
                        androidout.writeUTF("<identification>Android 12;Online;192.168.2.12;Windows XP;PH;RoboLab;RoboLab-PC;7.7.7");
                        UserPort up = new UserPort();
                        byte value = up.inportb(0x379);
			int currentvalue = up.inportb(0x379);
			while (true) {
				while (currentvalue == value) {
                        		value = up.inportb(0x379);
				}
				currentvalue = value;
				System.out.println("Status Port: " + value);
				androidout.writeUTF("<statusport>" + value);
			}
                    } 
                }
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }
    }
    
}