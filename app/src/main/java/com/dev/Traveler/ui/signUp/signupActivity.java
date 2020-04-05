package com.dev.Traveler.ui.signUp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dev.Traveler.R;
import com.dev.Traveler.data.DataManager;
import com.dev.Traveler.data.SharedPrefsHelper;
import com.dev.Traveler.models.User;
import com.dev.Traveler.ui.main.MainScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class signupActivity extends AppCompatActivity {
    DataManager dataManager;
    SharedPrefsHelper sharedPrefsHelper;
    EditText username, email, pass, rePass, favCan, yourCan,location,phone;
    private String selectedType;
    Spinner typeSpinner;

    User userObject;
    Button signUp;
    public ProgressDialog pd;
    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        FirebaseApp.initializeApp(this);
        typeSpinner = findViewById(R.id.spinner2);

        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,R.array.type_array
                ,android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(dataAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedType = parent.getSelectedItem().toString();
                if(selectedType.equals("Company")){
                    location.setVisibility(View.VISIBLE);
                    phone.setVisibility(View.VISIBLE);
                }else{
                    location.setVisibility(View.GONE);
                    phone.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        sharedPrefsHelper = new SharedPrefsHelper(this);
        dataManager = new DataManager(sharedPrefsHelper);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        username = findViewById(R.id.usernameUP);
        email = findViewById(R.id.et_email);
        pass = findViewById(R.id.et_passwordUP);
        rePass = findViewById(R.id.et_repassword);
        favCan = findViewById(R.id.favCoun);
        yourCan = findViewById(R.id.yourCount);
        location=findViewById(R.id.company_location);
        phone=findViewById(R.id.company_mobile);

        signUp = findViewById(R.id.signup_bt);



        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(signupActivity.this);
                pd.setMessage(signupActivity.this.getResources().getString(R.string.Pleasewait));
                pd.show();

                String str_username = username.getText().toString();
                String str_email = email.getText().toString();
                String str_password = pass.getText().toString();

                if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_email)
                        || TextUtils.isEmpty(str_password) || TextUtils.isEmpty(favCan.getText().toString())
                        || TextUtils.isEmpty(yourCan.getText().toString())) {
                    Toast.makeText(signupActivity.this, signupActivity.this.getResources()
                            .getString(R.string.Allfields), Toast.LENGTH_SHORT).show();
                } else if (str_password.length() < 6) {
                    Toast.makeText(signupActivity.this, signupActivity.this.getResources()
                            .getString(R.string.passwordmasst), Toast.LENGTH_SHORT).show();
                } else {
                    register(str_username, str_email, str_password, yourCan.getText().toString(), favCan.getText().toString()
                    ,location.getText().toString(),phone.getText().toString()
                    );
                }
            }
        });
    }

    public void register(final String username, final String email, String password, final String your, final String fav,final String loc,final String phon) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(signupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userID = firebaseUser.getUid();
                            dataManager.saveToken(userID);
                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
                            HashMap<String, Object> map = new HashMap<>();

                            map.put("id", userID);
                            map.put("username", username);
                            map.put("fcan", fav);
                            map.put("ycan", your);
                            map.put("urlimg", "https://firebasestorage.googleapis.com/v0/b/instagramtest-fcbef.appspot.com/o/placeholder.png?alt=media&token=b09b809d-a5f8-499b-9563-5252262e9a49");
                            map.put("bio", "");
                            map.put("email",email);
                            map.put("location",loc);
                            map.put("phone",phon);
                            map.put("type",selectedType);

                            reference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                          pd.dismiss();
                                         dataManager.setLoggedIn();
                                        Intent intent = new Intent(signupActivity.this, MainScreen.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            //  pd.dismiss();
                            Toast.makeText(signupActivity.this, signupActivity.this.getResources().getString(R.string.Authenticationfailed), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}