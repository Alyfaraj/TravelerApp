package com.dev.Traveler.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppConfig
{
        public static String Base_Url="http//:192.168.1.8/";


        public static Retrofit getretrofit(){
            return new Retrofit.Builder()
                    .baseUrl(AppConfig.Base_Url).
                            addConverterFactory(GsonConverterFactory.create())
                    .build();
        }}