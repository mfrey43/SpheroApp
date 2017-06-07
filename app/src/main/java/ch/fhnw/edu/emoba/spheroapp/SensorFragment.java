package ch.fhnw.edu.emoba.spheroapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.fhnw.edu.emoba.spherolib.SpheroRobotFactory;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotProxy;
import ch.fhnw.edu.emoba.spherolib.impl.SpheroMock;

import static ch.fhnw.edu.emoba.spheroapp.PairingActivity.MOCK_MODE;


public class SensorFragment extends Fragment {

    private SensorEventListener sensorEventListener;
    private SensorManager sensorManager;
    private Sensor rotationSensor;

    SpheroRobotProxy spheroRobotProxy = SpheroRobotFactory.getActualRobotProxy();

    public SensorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                double deltaX = event.values[0];
                double deltaY = event.values[1];

                double rad = Math.atan2(deltaX, deltaY); // start 0° at the top
                double heading = rad * (180 / Math.PI) + 180;
                double speed = (Math.abs(deltaX) + Math.abs(deltaY)) * 2;
                //Log.d("heading", Double.toString(heading));
                //Log.d("speed", Double.toString(speed));

                spheroRobotProxy.drive((float)heading, (float)speed);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser){
            enableSensor();
        }
        else{
            disableSensor();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d("hidden",Boolean.toString(hidden));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sensor, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disableSensor();
    }

    private void enableSensor(){
        if(sensorManager != null){
            sensorManager.registerListener(sensorEventListener, rotationSensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    private void disableSensor(){
        if(sensorManager != null && sensorEventListener != null){
            sensorManager.unregisterListener(sensorEventListener);
        }
        spheroRobotProxy.drive(0, 0);
    }
}
