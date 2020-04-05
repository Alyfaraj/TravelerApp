package com.dev.Traveler.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.Traveler.R;
import com.dev.Traveler.data.DataManager;
import com.dev.Traveler.data.SharedPrefsHelper;
import com.dev.Traveler.ui.enter.EnterActivity;
import com.dev.Traveler.ui.main.MainScreen;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class splashActivity extends AppCompatActivity  {
    FirebaseDatabase fb;

    FirebaseUser firebaseUser;
    DataManager dataManager;
    SharedPrefsHelper sharedPrefsHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fb = FirebaseDatabase.getInstance();
        fb.goOnline();
        fb.goOffline();
        TimerTask tt = new TimerTask() {

            @Override
            public void run() {
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        fb.goOnline();
                        Log.d("MainActivity", "run: Went Online and synced");
                        try {
                            sleep(10000); //sleep for 10 seconds
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        fb.goOffline();
                        Log.d("MainActivity", "run: Went Offline and synced");
                    }
                };
                t.run();
            }
        };
        Timer timer = new Timer("Timer");
        long delay = 10000L;
        long period = 10000L;
        timer.scheduleAtFixedRate(tt, delay, period);










        sharedPrefsHelper=new SharedPrefsHelper(this);
        dataManager=new DataManager(sharedPrefsHelper);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    if(dataManager.getLoggedInMode()){
                        openMainActivity();
                    }
                    else openEnterActivity();
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

    }

    public void openEnterActivity() {
        startActivity(new Intent(splashActivity.this, EnterActivity.class));
    }

    public void openMainActivity() {
        startActivity(new Intent(splashActivity.this, MainScreen.class));


    }
}
