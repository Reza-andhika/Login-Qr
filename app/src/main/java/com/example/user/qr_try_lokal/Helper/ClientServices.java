package com.example.user.qr_try_lokal.Helper;

import com.example.user.qr_try_lokal.Model.LoginPost;
import com.example.user.qr_try_lokal.Model.UserPut;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ClientServices {

    @POST("android/login_api")
    Call<LoginPost> logPost(@Body LoginPost userLogin);
    @PUT("api/api_try")
    Call<UserPut> edituser(@Body UserPut userEdit);
}
