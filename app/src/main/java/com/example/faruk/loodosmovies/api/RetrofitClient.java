package com.example.faruk.loodosmovies.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by FARUK on 6.04.2018.
 */

public class RetrofitClient {
    public static final String APIKEY="f7548b54";
    public static final String BASEURL="http://www.omdbapi.com";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
