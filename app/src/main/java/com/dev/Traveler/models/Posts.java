package com.dev.Traveler.models;

public class Posts {
    private String postid;
    private String urlimg;
    private String description;
    private String publisher;
    private String country;
    private String city;
    private String date;


    public Posts(){}

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getUrlimg() {
        return urlimg;
    }

    public void setUrlimg(String urlimg) {
        this.urlimg = urlimg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Posts(String postid, String urlimg, String description, String publisher, String country, String city, String date) {
        this.postid = postid;
        this.urlimg = urlimg;
        this.description = description;
        this.publisher = publisher;
        this.country = country;
        this.city = city;
        this.date = date;
    }
}