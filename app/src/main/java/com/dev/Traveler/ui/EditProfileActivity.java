package com.dev.Traveler.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dev.Traveler.R;
import com.dev.Traveler.models.User;
import com.dev.Traveler.models.parseJson;
import com.dev.Traveler.ui.main.MainScreen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    ImageView close, image_profile,imgAdded;

    TextView save, tv_change;
   EditText fullname, username, bio,ycan,favcan,emailet,phoneet,locationet;

    FirebaseUser firebaseUser;

    private Uri mImageUri;
    private StorageTask uploadTask;
    StorageReference storageRef;
    Bitmap bitmap;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        close = findViewById(R.id.close);
        image_profile = findViewById(R.id.image_profile);
        save = findViewById(R.id.save);
        tv_change = findViewById(R.id.tv_change);
        fullname = findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        bio = findViewById(R.id.bio);
        ycan=findViewById(R.id.ycan);
        favcan=findViewById(R.id.fcan);
        emailet=findViewById(R.id.edit_email);
        phoneet=findViewById(R.id.edit_phone);
        locationet=findViewById(R.id.edit_location);


        dialog = new ProgressDialog(EditProfileActivity.this);
        dialog.setMessage("uploading...");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storageRef = FirebaseStorage.getInstance().getReference("uploads");

        DatabaseReference reference = FirebaseDatabase.getInstance().
                getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                bio.setText(user.getBio());
                ycan.setText(user.getYcan());
                favcan.setText(user.getFcan());
                emailet.setText(user.getEmail());
                phoneet.setText(user.getPhone());
                locationet.setText(user.getLocation());
                Glide.with(getApplicationContext()).load(user.getUrlimg()).into(image_profile);
                if(user.getType().equals("company")){
                    emailet.setVisibility(View.VISIBLE);
                    phoneet.setVisibility(View.VISIBLE);
                    locationet.setVisibility(View.VISIBLE);
                }else {
                    emailet.setVisibility(View.GONE);
                    phoneet.setVisibility(View.GONE);
                    locationet.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile(fullname.getText().toString(),
                        username.getText().toString(),
                        bio.getText().toString()
                ,ycan.getText().toString(),favcan.getText().toString(),emailet.getText().toString()
                        ,phoneet.getText().toString().trim(),locationet.getText().toString()
                );
                Intent intent = new Intent(EditProfileActivity.this, MainScreen.class);
                intent.putExtra("publisherid", "me");
                startActivity(intent);


            }
        });

        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfileActivity.this, Change_profile_pic.class));

            }
        });

        image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(EditProfileActivity.this, Change_profile_pic.class));
            }
        });
    }

    private void updateProfile(String fullname, String username, String bio,String yocan,String favcan
            ,String mail,String mob,String loc){

        DatabaseReference reference = FirebaseDatabase.getInstance().
                getReference().child("Users").child(firebaseUser.getUid());

        HashMap<String, Object> map = new HashMap<>();
        map.put("fullname", fullname);
        map.put("username", username);
        map.put("bio", bio);
        map.put("ycan",yocan);
        map.put("fcan",favcan);
        map.put("phone",mob);
        map.put("location",loc);
        map.put("email",mail);

        reference.updateChildren(map);

        Toast.makeText(EditProfileActivity.this, "Successfully updated!", Toast.LENGTH_SHORT).show();
    }


    private String showJSON(String json){
        parseJson pj = new parseJson(json);
        String ur= pj.parseJSON();
        //Log.e("uImage","ser image"+JsonParser.uImages[1]);
        return ur;
    }





    void upload(final String u,final String d,final String c,final String ci){
        dialog.show();
        final String img=convertToString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycby-cRgZYmDbB_WVPlFEz1lhPJHJ9B8GVuTqdFg5XrHthmgbZWY/exec"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                sendRequest(d,c,ci);
                Toast.makeText(EditProfileActivity.this, "Done", Toast.LENGTH_LONG).show();
                dialog.dismiss();


            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.cancel();                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("action","insert");
                params.put("uId","id");
                params.put("uName","user");
                params.put("uImage",img);

                return params;            }
        };


        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);



    }





    private void sendRequest(final String des, final String con, final String city){

        StringRequest stringRequest = new StringRequest("https://script.google.com/macros/s/AKfycby-cRgZYmDbB_WVPlFEz1lhPJHJ9B8GVuTqdFg5XrHthmgbZWY/exec"+"?action=readAll",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Log.e("null","ser image"+response);
                        String url= showJSON(response);

                        DatabaseReference reference = FirebaseDatabase.getInstance()
                                .getReference("Users").child(firebaseUser.getUid());
                        HashMap<String, Object> map1 = new HashMap<>();
                        map1.put("urlimg", ""+url);
                        reference.updateChildren(map1);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public String convertToString() {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,35,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

                if (resultCode == RESULT_OK) {

                    try {
                        CropImage.ActivityResult result = CropImage.getActivityResult(data);
                        mImageUri =result.getUri();

                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),mImageUri);

                        image_profile.setImageBitmap(bitmap);
                        upload("d","df","df","ssd");



                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                Toast.makeText(EditProfileActivity.this, "يجب وضع صوره او فيديو", Toast.LENGTH_LONG).show();
            }

    }




}





