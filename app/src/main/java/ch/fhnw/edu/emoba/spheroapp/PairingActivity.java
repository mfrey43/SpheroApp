package ch.fhnw.edu.emoba.spheroapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PairingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                launchMainActivity();
            }
        }, 500);
    }

    private void launchMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
    }
}
