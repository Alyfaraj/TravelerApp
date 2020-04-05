package com.dev.Traveler.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsHelper {
    //name the sharedPreference
    public static final String MY_PREFS="MY_APP";
    public static final String MY_EMAIL="EMAIL";
    public static final String MY_TOKEN="TOKEN";
    public static final String MY_PASSWORD="PASSWORD";
    public static final String USER_NAME="USERNAME";


    SharedPreferences msharedPreferences;

    public SharedPrefsHelper(Context context){
        msharedPreferences=context.getSharedPreferences(MY_PREFS,Context.MODE_PRIVATE);

    }
    public void clear(){
        msharedPreferences.edit().clear().apply();
    }
    public void putEmail(String email){
        msharedPreferences.edit().putString(MY_EMAIL,email).apply();
    }
    public String getEmail(){
        return msharedPreferences.getString(MY_EMAIL,null);
    }

    public void putusername(String username){
        msharedPreferences.edit().putString(USER_NAME,username).apply();
    }
    public String getusername(){
        return msharedPreferences.getString(USER_NAME,null);
    }


    public void setLoggedInMode(Boolean loggedInMode){
        msharedPreferences.edit().putBoolean("IS_LOGGED_IN",loggedInMode).apply();
    }
    public Boolean getLoggedInMode(){
        return msharedPreferences.getBoolean("IS_LOGGED_IN",false);
    }
    public void putToken(String token){
        msharedPreferences.edit().putString(MY_TOKEN,token).apply();
    }
    public String getToken(){
        return msharedPreferences.getString(MY_TOKEN,null);
    }

    public void putPassword(String password){
        msharedPreferences.edit().putString(MY_PASSWORD,password).apply();
    }
    public String getPassword(){
        return msharedPreferences.getString(MY_PASSWORD,null);
    }


}

