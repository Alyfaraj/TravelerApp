package com.dev.Traveler.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.dev.Traveler.R;
import com.dev.Traveler.models.parseJson;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Change_profile_pic extends AppCompatActivity {

    ImageView closeImage, imgAdded;
    TextView textPost;
    EditText Descreption, city;
    Spinner countrySpinner;
    private Uri mImageUri;
    Bitmap bitmap;
    ArrayList list;
    private String selectedCountry;
    InputStream is;
    String imageBytes;
    ProgressDialog dialog;
    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile_pic);


        initview();

        cropImage();



        textPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    upload("kdi","wd","efe","dwd");



            }
        });

    }

    public void initview() {
        closeImage = findViewById(R.id.close);
        imgAdded = findViewById(R.id.image_proo);
        textPost = findViewById(R.id.savepro);


        dialog = new ProgressDialog(Change_profile_pic.this);
        dialog.setMessage("sending...");



    }


    void upload(final String u,final String d,final String c,final String ci){
        dialog.show();
        final String img=convertToString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycby-cRgZYmDbB_WVPlFEz1lhPJHJ9B8GVuTqdFg5XrHthmgbZWY/exec"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                sendRequest(d,c,ci);
                Toast.makeText(Change_profile_pic.this, "Done", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                openMainActivity();
                finish();

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
                                .getReference("Users").child(FirebaseAuth.getInstance()
                                        .getCurrentUser().getUid());
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


    private String showJSON(String json){
        parseJson pj = new parseJson(json);
        String ur= pj.parseJSON();
        //Log.e("uImage","ser image"+JsonParser.uImages[1]);
        return ur;
    }









    public void openMainActivity() {
       Intent intent=new Intent(Change_profile_pic.this, EditProfileActivity.class);
        intent.putExtra("publisherid", "me");
        startActivity(intent);
        finish();

    }

    public void cropImage() {
        CropImage.activity()
                .setAspectRatio(1, 1)
                .start(Change_profile_pic.this);


    }

    public String convertToString() {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,30,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

                if (resultCode == RESULT_OK) {

                    try {
                        CropImage.ActivityResult result = CropImage.getActivityResult(data);
                        mImageUri =result.getUri();
                        if (mImageUri==null){
                            openMainActivity();
                        }
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),mImageUri);

                        imgAdded.setImageBitmap(bitmap);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                Toast.makeText(Change_profile_pic.this, "يجب وضع صوره او فيديو", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(Change_profile_pic.this, "حدث شء خطا", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        openMainActivity();
    }
}
