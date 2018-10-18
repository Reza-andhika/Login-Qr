package com.example.user.qr_try_lokal.Rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static final String BASE_URL="http://192.168.1.14/test_login/index.php/";

    private static Retrofit retrofit= new Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();

    public static <S> S cretaeService(Class<S> serviceClass){
        return retrofit.create(serviceClass);
    }
}
