package com.dev.Traveler.data;

public class DataManager {
    SharedPrefsHelper msharedPrefsHelper;
    public DataManager(SharedPrefsHelper sharedPrefsHelper){
        msharedPrefsHelper=sharedPrefsHelper;
    }

    public void clear(){
        msharedPrefsHelper.clear();
    }

    public void saveEmail(String email){
        msharedPrefsHelper.putEmail(email);
    }
    public String getEmail(){
        return msharedPrefsHelper.getEmail();
    }
    public void saveUsername(String username){
        msharedPrefsHelper.putusername(username);
    }
    public String getusername(){
        return msharedPrefsHelper.getusername();
    }

    public void setLoggedIn(){
        msharedPrefsHelper.setLoggedInMode(true);
    }
        public Boolean getLoggedInMode(){
        return msharedPrefsHelper.getLoggedInMode();
    }

    public void saveToken(String token){
        msharedPrefsHelper.putToken(token);
    }
    public String getToken(){
        return msharedPrefsHelper.getToken();
    }

    public void savePassword(String password){
        msharedPrefsHelper.putPassword(password);
    }
    public String getPassword(){
        return msharedPrefsHelper.getPassword();
    }

    public void LogoutSession(){
        msharedPrefsHelper.setLoggedInMode(false);
    }


}
