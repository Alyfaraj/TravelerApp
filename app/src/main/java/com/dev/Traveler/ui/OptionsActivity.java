package com.dev.Traveler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.dev.Traveler.R;
import com.dev.Traveler.data.DataManager;
import com.dev.Traveler.data.SharedPrefsHelper;
import com.dev.Traveler.ui.enter.EnterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;


public class OptionsActivity extends AppCompatActivity {
    DataManager dataManager;
    SharedPrefsHelper sharedPrefsHelper;
    TextView logout,da;
    String topoic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        sharedPrefsHelper=new SharedPrefsHelper(this);
        dataManager=new DataManager(sharedPrefsHelper);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Options");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
da=findViewById(R.id.date);
        topoic= FirebaseAuth.getInstance().getCurrentUser().getUid();

        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseMessaging.getInstance().unsubscribeFromTopic(topoic);
                FirebaseAuth.getInstance().signOut();
                dataManager.LogoutSession();
                startActivity(new Intent(OptionsActivity.this, EnterActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });
    }
}
