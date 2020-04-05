package com.dev.Traveler.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.Traveler.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {
   EditText emailfeild;
   Button resetPass;
   TextView tvChack;
   FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        emailfeild=findViewById(R.id.et_email_for_restart);
        resetPass=findViewById(R.id.bt_reset);
        tvChack=findViewById(R.id.et_check);
        firebaseAuth=FirebaseAuth.getInstance();
        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String email=emailfeild.getText().toString().trim();
              if(email.isEmpty()){
                  Toast.makeText(ForgetPasswordActivity.this
                  ,"Please Check filed",Toast.LENGTH_SHORT).show();
              }
              else {
                  firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(ForgetPasswordActivity.this,new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {
                          if(task.isSuccessful()){
                          tvChack.setVisibility(View.VISIBLE);
                          Toast.makeText(ForgetPasswordActivity.this
                                  ,"Done",Toast.LENGTH_SHORT).show();
                          }
                          else{
                              Toast.makeText(ForgetPasswordActivity.this
                                      ,""+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                          }
                      }
                  }).addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                          Toast.makeText(ForgetPasswordActivity.this
                                  ,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                      }



              });
              }
            }


        });





    }
}
