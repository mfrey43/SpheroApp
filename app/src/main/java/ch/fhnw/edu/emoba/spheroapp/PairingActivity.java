package ch.fhnw.edu.emoba.spheroapp;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ch.fhnw.edu.emoba.spherolib.SpheroRobotDiscoveryListener;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotFactory;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotProxy;

import static ch.fhnw.edu.emoba.spherolib.SpheroRobotDiscoveryListener.SpheroRobotBluetoothNotification.Online;

public class PairingActivity extends AppCompatActivity implements SpheroRobotDiscoveryListener {

    public static final boolean MOCK_MODE = false;

    TextView textView;
    SpheroRobotProxy proxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing);
        textView = (TextView)findViewById(R.id.textView);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            textView.setText("Bluetooth device must be enabled.");
            return;
        }

        if(MOCK_MODE){
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
