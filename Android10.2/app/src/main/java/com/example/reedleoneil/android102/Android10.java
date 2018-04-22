package com.example.reedleoneil.android102;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Android10 extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Variables keni la.
     */
    static String system = null;
    List<Socket> androidsocket = new ArrayList<Socket>();
    static List<DataOutputStream> androidout = new ArrayList<DataOutputStream>();
    List<DataInputStream> androidin = new ArrayList<DataInputStream>();
    protected static final int REQUEST_OK = 1;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new connect(androidsocket, androidin, androidout).start(); /**keni ya ing java code ku*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android10);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance(position + 1)).commit();

        /**
         * Set me kenie ing id na ning pic a gamitan.
         */
        switch (position) {
            case 0:
                system = "0001";
                break;
            case 1:
                system = "0010";
                break;
            case 2:
                system = "0011";
                break;
            case 3:
                system = "0100";
                break;
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.android10, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_example) {
            /**Voice com keni ya matriger*/
            Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
            try {
                startActivityForResult(i, REQUEST_OK);
            } catch (Exception e) {
                Toast.makeText(this, "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            /**
             * Load mula keni deng fragments para keng bawat appliance
             */
            View rootView = null;
            if (system.equals("0001")) {
                rootView = inflater.inflate(R.layout.electricfan, container, false);
            } else if (system.equals("0010")) {
                rootView = inflater.inflate(R.layout.lightning, container, false);
            } else if (system.equals("0011")) {
                rootView = inflater.inflate(R.layout.electricalsocket, container, false);
            } else if (system.equals("0100")) {
                rootView = inflater.inflate(R.layout.computer, container, false);
            }
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((Android10) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    /**
     * Keni la deng events
     */

    public void onElectricFanClicked(View view) throws IOException { /**para keng electric pan*/
        ToggleButton tb1 = (ToggleButton) findViewById(R.id.toggleButton1);
        ToggleButton tb2 = (ToggleButton) findViewById(R.id.toggleButton2);
        switch (view.getId()) {
            case R.id.switchmanualremote:
                boolean remoteon = ((Switch) view).isChecked();
                if (remoteon) {
                    androidout.get(0).writeUTF("<or>00000001"); //remote
                } else {
                    androidout.get(0).writeUTF("<and>11111110"); //manual
                }
                break;
            case R.id.toggleButton2:
                boolean on2 = ((ToggleButton) view).isChecked();
                if (on2) {
                    androidout.get(0).writeUTF("<and>11110101");
                    androidout.get(0).writeUTF("<or>00000100"); //speed 2
                    tb1.setChecked(false);
                }
                break;
            case R.id.toggleButton1:
                boolean on1 = ((ToggleButton) view).isChecked();
                if (on1) {
                    androidout.get(0).writeUTF("<and>11111001");
                    androidout.get(0).writeUTF("<or>00001000"); //speed 1
                    tb2.setChecked(false);
                }
                break;
            case R.id.buttonoff:
                androidout.get(0).writeUTF("<and>11110001"); //turnoff
                tb1.setChecked(false);
                tb2.setChecked(false);
                break;
        }
    }

    public void onLightningClicked(View view) throws IOException { /**para keng lighting*/
        switch (view.getId()) {
            case R.id.switchlight1:
                boolean lighton1 = ((Switch) view).isChecked();
                if (lighton1) {
                    androidout.get(0).writeUTF("<or>00010000"); //on 1
                } else {
                    androidout.get(0).writeUTF("<and>11101111"); //off 1
                }
                break;
            case R.id.switchlight2:
                boolean lighton2 = ((Switch) view).isChecked();
                if (lighton2) {
                    androidout.get(0).writeUTF("<or>00100000"); //on 2
                } else {
                    androidout.get(0).writeUTF("<and>11011111"); //off 2
                }
                break;
        }
    }

    public void onElectricalSocketClicked(View view) throws IOException { /**para keng electriccal sockets*/
        boolean socketon = ((ToggleButton) view).isChecked();
            if (socketon) {
                    androidout.get(0).writeUTF("<or>01000000"); //on socket
            } else {
                androidout.get(0).writeUTF("<and>10111111"); //off socket
            }
    }

    public void onComputerClicked(View view) throws IOException { /**para keng computer*/
        switch (view.getId()) {
            case R.id.buttonshutdownpc:
                androidout.get(0).writeUTF("<shutdown>►Android 8"); //shutdown
                break;
            case R.id.buttonrestartpc:
                androidout.get(0).writeUTF("<restart>►Android 8"); //restart
                break;
            case R.id.buttonturnonpc:
                androidout.get(0).writeUTF("<or>10000000");
                androidout.get(0).writeUTF("<or>10000000");
                androidout.get(0).writeUTF("<or>10000000");
                androidout.get(0).writeUTF("<or>10000000");
                androidout.get(0).writeUTF("<or>10000000");
                androidout.get(0).writeUTF("<and>01111111");
                break;
        }
    }

    /**
     * Keni ya ing voice commands
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_OK  && resultCode==RESULT_OK) {
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            Toast.makeText(this, thingsYouSaid.get(0), Toast.LENGTH_LONG).show();
            try {
                /*if(thingsYouSaid.get(0).equals("remote")){
                    androidout.get(0).writeUTF("<or>00000001"); //remote
                } else if (thingsYouSaid.get(0).equals("manual")) {
                    androidout.get(0).writeUTF("<and>11111110"); //manual
                } else if (thingsYouSaid.get(0).equals("3")) {
                    androidout.get(0).writeUTF("<and>11110011");
                    androidout.get(0).writeUTF("<or>00000010"); //speed 3
                } else if (thingsYouSaid.get(0).equals("sobrang init")) {
                    androidout.get(0).writeUTF("<and>11110101");
                    androidout.get(0).writeUTF("<or>00000100"); //speed 2
                } else if (thingsYouSaid.get(0).equals("mainit")) {
                    androidout.get(0).writeUTF("<and>11111001");
                    androidout.get(0).writeUTF("<or>00001000"); //speed 1
                } else if (thingsYouSaid.get(0).equals("light 1 on")) {
                    androidout.get(0).writeUTF("<or>00010000"); //on 1
                } else if (thingsYouSaid.get(0).equals("light one off")) {
                    androidout.get(0).writeUTF("<and>11101111"); //off 1
                } else if (thingsYouSaid.get(0).equals("light 2 on")) {
                    androidout.get(0).writeUTF("<or>00100000"); //on 2
                } else if (thingsYouSaid.get(0).equals("light 2 off")) {
                    androidout.get(0).writeUTF("<and>11011111"); //off 2
                } else if (thingsYouSaid.get(0).equals("plug socket 1")) {
                    androidout.get(0).writeUTF("<or>01000000"); //on socket 1
                } else if (thingsYouSaid.get(0).equals("unplug socket 1")) {
                    androidout.get(0).writeUTF("<and>10111111"); //off socket 1
                } else if (thingsYouSaid.get(0).equals("plug socket 2")) {
                    androidout.get(0).writeUTF("<or>10000000"); //on socket 2
                } else if (thingsYouSaid.get(0).equals("unplug socket 2")) {
                    androidout.get(0).writeUTF("0<and>01111111"); //off socket 2
                }*/
                if(thingsYouSaid.get(0).equals("remote")){
                    androidout.get(0).writeUTF("<or>00000001"); //remote
                } else if (thingsYouSaid.get(0).equals("manual")) {
                    androidout.get(0).writeUTF("<and>11111110"); //manual
                } else if (thingsYouSaid.get(0).equals("sobrang init")) {
                    androidout.get(0).writeUTF("<and>11110101");
                    androidout.get(0).writeUTF("<or>00000100"); //speed 2
                } else if (thingsYouSaid.get(0).equals("mainit")) {
                    androidout.get(0).writeUTF("<and>11111001");
                    androidout.get(0).writeUTF("<or>00001000"); //speed 1
                } else if (thingsYouSaid.get(0).equals("patayin ang electric fan")) {
                    androidout.get(0).writeUTF("<and>11110001"); //patdan ing electric fan
                } else if (thingsYouSaid.get(0).equals("sindihan ang ilaw")) {
                    androidout.get(0).writeUTF("<or>00010000"); //on 1
                    androidout.get(0).writeUTF("<or>00100000"); //on 2
                } else if (thingsYouSaid.get(0).equals("patayin ang ilaw")) {
                    androidout.get(0).writeUTF("<and>11101111"); //off 1
                    androidout.get(0).writeUTF("<and>11011111"); //off 2
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
