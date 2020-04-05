package com.dev.Traveler.utiles;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private static Preferences Prefs;
    private SharedPreferences prefs;

    String myPrefs="prefs";


    public static Preferences getInstance(Context context) {
        if (Prefs == null) {
            Prefs = new Preferences(context);
        }
        return Prefs;
    }

    private Preferences(Context context) {
        prefs = context.getSharedPreferences(myPrefs,Context.MODE_PRIVATE);
    }


    public void saveKey(String key,Object value) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        if(value instanceof String)
            prefsEditor .putString(key,(String) value);
        else if (value instanceof Integer)
            prefsEditor.putInt(key,(Integer) value);
        else if (value instanceof Float)
            prefsEditor.putFloat(key,(Float) value);
        else if(value instanceof Boolean)
            prefsEditor.putBoolean(key,(Boolean)value);

        prefsEditor.commit();
    }



    /*public void saveKey(String key,String value) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor .putString(key, value);
        prefsEditor.commit();
    }*/

    public String getKey(String key) {
        if (prefs!= null) {
            return prefs.getString(key, "");
        }
        return "";
    }



}