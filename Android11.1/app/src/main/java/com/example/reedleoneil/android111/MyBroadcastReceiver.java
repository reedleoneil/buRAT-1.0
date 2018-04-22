package com.example.reedleoneil.android111;

/**
 * Created by Ed Bynce on 10/15/2014.
 */

        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.telephony.SmsManager;
        import android.telephony.SmsMessage;
        import android.util.Log;
        import android.widget.Toast;

        import java.io.DataInputStream;
        import java.io.DataOutputStream;
        import java.net.Socket;
        import java.util.ArrayList;
        import java.util.List;

//Here is your broadcast receiver class
public class MyBroadcastReceiver extends BroadcastReceiver{
    List<Socket> androidsocket = new ArrayList<Socket>();
    List<DataOutputStream> androidout = new ArrayList<DataOutputStream>();
    List<DataInputStream> androidin = new ArrayList<DataInputStream>();

        // Get the object of SmsManager
        final SmsManager sms = SmsManager.getDefault();

        public void onReceive(Context context, Intent intent) {

            // Retrieves a map of extended data from the intent.
            final Bundle bundle = intent.getExtras();

            try {

                if (bundle != null) {

                    final Object[] pdusObj = (Object[]) bundle.get("pdus");

                    for (int i = 0; i < pdusObj.length; i++) {

                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                        String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                        String senderNum = phoneNumber;
                        String message = currentMessage.getDisplayMessageBody();

                        Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);


                        // Show Alert
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context,
                                "senderNum: "+ senderNum + ", message: " + message, duration);
                        toast.show();


                        //send commands to server
                        new connect(androidsocket, androidin, androidout).start();
                        Thread.sleep(5000);
                        androidout.get(0).writeUTF("<sms>" + message);
                        androidsocket.get(0).close();


                    } // end for loop
                } // bundle is null

            } catch (Exception e) {
                Log.e("SmsReceiver", "Exception smsReceiver" +e);

            }
        }
    }
