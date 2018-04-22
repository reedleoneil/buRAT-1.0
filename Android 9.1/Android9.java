import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Android9 {

    public static void main(String[] args) {
      
        Android9 android9 = new Android9();
        android9.initiate();
    }

    void initiate() {
        while (true) {
            try (Socket androidsocket = new Socket("192.168.2.20",1994)) {
                DataInputStream androidin = new DataInputStream(androidsocket.getInputStream());
                DataOutputStream androidout = new DataOutputStream(androidsocket.getOutputStream());
                androidout.writeUTF("Android 9");
                while (true) {
                    String data = androidin.readUTF();
                    System.out.println(data);
                    if (data.equals("<identify>")) {
                        androidout.writeUTF("<identification>Android 9;Online;192.168.2.9;Windows XP;PH;RoboLab;RoboLab-PC;7.7.7");
                        UserPort up = new UserPort();
                        byte value = -1;
                        up.outportb(0x378,value);
                    } else if (data.startsWith("<lpt>")) {
			Scanner s = new Scanner(data);
                	s.useDelimiter(">");
                	s.next();
                	int fooint = Integer.parseInt(s.next(), 2);
                	byte foobyte = (byte)fooint;
			UserPort up = new UserPort();
                        byte value = foobyte;
			value = (byte)(~value);
                        up.outportb(0x378,value);
		    } else if (data.startsWith("<and>")) {
			Scanner s = new Scanner(data);
                	s.useDelimiter(">");
                	s.next();
                	int fooint = Integer.parseInt(s.next(), 2);
                	byte foobyte = (byte)fooint;
			UserPort up = new UserPort();
                        byte value = foobyte;
			value = (byte)(~(int)up.inportb(0x378) & value);
			value = (byte)(~value);
                        up.outportb(0x378,value);
		    } else if (data.startsWith("<or>")) {
			Scanner s = new Scanner(data);
                	s.useDelimiter(">");
                	s.next();
                	int fooint = Integer.parseInt(s.next(), 2);
                	byte foobyte = (byte)fooint;
			UserPort up = new UserPort();
                        byte value = foobyte;
			value = (byte)(~(int)up.inportb(0x378) | value);
			value = (byte)(~value);
                        up.outportb(0x378,value);
		    }
                }
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }
    }
    
}