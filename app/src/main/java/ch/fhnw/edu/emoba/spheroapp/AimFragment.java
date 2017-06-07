package ch.fhnw.edu.emoba.spheroapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.fhnw.edu.emoba.spherolib.SpheroRobotFactory;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotProxy;
import ch.fhnw.edu.emoba.spherolib.impl.SpheroMock;

import static ch.fhnw.edu.emoba.spheroapp.PairingActivity.MOCK_MODE;


public class AimFragment extends Fragment {

    SpheroRobotProxy spheroRobotProxy = SpheroRobotFactory.getActualRobotProxy();

    public AimFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aim, container, false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (spheroRobotProxy == null) return;

        if (isVisibleToUser) {
            spheroRobotProxy.setBackLedBrightness(1);
            spheroRobotProxy.drive(0, 0);
        } else {
            spheroRobotProxy.setBackLedBrightness(0);
            spheroRobotProxy.drive(0, 0);
        }
    }
}
