package com.dev.Traveler.models;

import com.google.gson.annotations.SerializedName;

public class MainResponse {
    @SerializedName("message")
    public String msg;
    @SerializedName("status")
    public String status;
}
