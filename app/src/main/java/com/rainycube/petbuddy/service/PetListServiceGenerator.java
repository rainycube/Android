package com.rainycube.petbuddy.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.rainycube.petbuddy.util.JsonStringConverterFactory;

/**
 * Created by SBKim on 2016-06-14.
 */
public class PetListServiceGenerator {
    public static final String API_BASE_URL = "http://leeskhome.iptime.org:8080/petAPI/";

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(new JsonStringConverterFactory(GsonConverterFactory.create()));

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.build();
        return retrofit.create(serviceClass);
    }
}



