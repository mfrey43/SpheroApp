package ch.fhnw.edu.emoba.spheroapp;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import ch.fhnw.edu.emoba.spherolib.SpheroRobotDiscoveryListener;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotFactory;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotProxy;

import static ch.fhnw.edu.emoba.spherolib.SpheroRobotDiscoveryListener.SpheroRobotBluetoothNotification.Online;

public class PairingActivity extends AppCompatActivity implements SpheroRobotDiscoveryListener {

    TextView textView;
    SpheroRobotProxy proxy;

    boolean skip = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing);
        textView = (TextView)findViewById(R.id.textView);

        if(skip){
            launchMainActivity();
        }else{
            boolean onEmulator = Build.PRODUCT.startsWith("sdk");
            proxy = SpheroRobotFactory.createRobot(onEmulator);
            proxy.setDiscoveryListener(this);
            proxy.startDiscovering(getApplicationContext());
        }

    }

    private void launchMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
        if(proxy != null){
            proxy.stopDiscovering();
        }
    }

    private void updateText(String text){
        textView.setText(text);
    }

    @Override
    public void handleRobotChangedState(final SpheroRobotBluetoothNotification spheroRobotBluetoothNotification) {
        if(spheroRobotBluetoothNotification.equals(Online)){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    launchMainActivity();
                }
            });
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateText(spheroRobotBluetoothNotification.name());
                }
            });
        }
    }
}
