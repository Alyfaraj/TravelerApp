package com.dev.Traveler.models;

import com.google.gson.annotations.SerializedName;

public class post {

    @SerializedName("idimg")
    private int idimg;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("country")
    private String country;
    @SerializedName("city")
    private String city;
    @SerializedName("urlimg")
    private String urlimg;

    public int getIdimg() {
        return idimg;
    }

    public void setIdimg(int idimg) {
        this.idimg = idimg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUrlimg() {
        return urlimg;
    }

    public void setUrlimg(String urlimg) {
        this.urlimg = urlimg;
    }
}

