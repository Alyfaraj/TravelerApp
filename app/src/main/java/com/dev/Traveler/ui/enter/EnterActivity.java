package com.dev.Traveler.ui.enter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dev.Traveler.R;
import com.dev.Traveler.data.DataManager;
import com.dev.Traveler.utiles.travelerApplication;
import com.dev.Traveler.ui.loin.LoginActivity;
import com.dev.Traveler.ui.signUp.signupActivity;

public class EnterActivity extends AppCompatActivity implements enterMvpView{
    DataManager mdataManager;
    enterPresenter menterPresenter;
    Button signup,login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        mdataManager=((travelerApplication) getApplication()).getmDataManger();
        menterPresenter = new enterPresenter(mdataManager);
        menterPresenter.onAttach(this);
        initView();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignupActivity();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });


    }

    @Override
    public void initView() {
        signup=findViewById(R.id.enterSignUP);
        login=findViewById(R.id.enterLogin);

    }

    @Override
    public void openSignupActivity() {
        startActivity(new Intent(EnterActivity.this, signupActivity.class));
    }

    @Override
    public void openLoginActivity() {
        startActivity(new Intent(EnterActivity.this, LoginActivity.class));

    }

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);

    }
}
