package com.dev.Traveler.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.dev.Traveler.R;
import com.dev.Traveler.ui.AddPhoto;
import com.dev.Traveler.ui.Home.HomeFragment;
import com.dev.Traveler.ui.NotificationFragment;
import com.dev.Traveler.ui.Populer.PopulerFragment;
import com.dev.Traveler.ui.Profile.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainScreen extends AppCompatActivity {

    BottomNavigationView bottom_navigation;
    Fragment selectedfragment = null;
    String MyTobic;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        MyTobic= FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseMessaging.getInstance().subscribeToTopic(MyTobic);


        bottom_navigation = findViewById(R.id.navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
         fragmentTransaction=getSupportFragmentManager().beginTransaction().addToBackStack(null);

        Bundle intent = getIntent().getExtras();
        if (intent != null){
            String publisher = intent.getString("publisherid");
            if(publisher.equals("notifi")){
                bottom_navigation.setSelectedItemId(R.id.navigation_notifications);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,
                        new NotificationFragment()).commit();
            } else if(publisher.equals("me")){
                bottom_navigation.setSelectedItemId(R.id.navigation_profile);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,
                        new ProfileFragment()).commit();
            }
            else{

            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
            editor.putString("profileid", publisher);
            editor.apply();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame,
                    new ProfileFragment()).commit();
            }
        } else {
            bottom_navigation.setSelectedItemId(R.id.navigation_home);

            getSupportFragmentManager().beginTransaction().replace(R.id.frame,
                    new HomeFragment()).commit();

        }

}

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){
                        case R.id.navigation_home:
                            selectedfragment = new HomeFragment();
                                                        break;
                        case R.id.navigation_poupler:
                            selectedfragment = new PopulerFragment();
                            break;
                        case R.id.navigation_add:
                            selectedfragment = null;
                            startActivity(new Intent(MainScreen.this, AddPhoto.class));
                           break;
                        case R.id.navigation_notifications:
                            selectedfragment = new NotificationFragment();

                            break;
                        case R.id.navigation_profile:
                            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                            editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();
                            selectedfragment = new ProfileFragment();
                            break;
                    }
                    if (selectedfragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,
                                selectedfragment).commit();
                    }

                    return true;
                }
            };


}