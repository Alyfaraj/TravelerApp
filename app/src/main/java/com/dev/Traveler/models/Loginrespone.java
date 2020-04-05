package com.dev.Traveler.models;

import com.google.gson.annotations.SerializedName;

public class Loginrespone {

    @SerializedName("token")
    public String token;
    @SerializedName("message")
    public String msg;
    @SerializedName("status")
    public String status;
    }
