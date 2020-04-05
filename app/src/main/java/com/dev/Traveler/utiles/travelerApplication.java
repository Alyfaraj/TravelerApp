package com.dev.Traveler.utiles;

import android.app.Application;

import com.dev.Traveler.data.DataManager;
import com.dev.Traveler.data.SharedPrefsHelper;

public class travelerApplication extends Application {
    SharedPrefsHelper mSharedPrefsHelper;
    DataManager mDataManger;

    @Override
    public void onCreate() {
        super.onCreate();
        mSharedPrefsHelper=new SharedPrefsHelper(getApplicationContext());
        mDataManger=new DataManager(mSharedPrefsHelper);
    }
    public DataManager getmDataManger(){return mDataManger;}
}

