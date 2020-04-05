package com.dev.Traveler.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class parseJson {

    private JSONArray users = null;
    String url;
    String json;
    public parseJson(String json){
        this.json=json;
    }


    public String parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray("records");


            for(int i=users.length()-1;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);

                url = jo.getString("uImage");
            }

            // Log.e("uImage","ser image"+uImages[0]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }

}
