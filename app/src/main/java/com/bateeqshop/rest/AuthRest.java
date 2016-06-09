package com.bateeqshop.rest;

import android.hardware.camera2.params.Face;

import com.bateeqshop.model.FacebookLoginModel;
import com.bateeqshop.model.SessionModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthRest
{
    @FormUrlEncoded
    @POST("token")
    Call<SessionModel> login(@Field("username") String userName,
                             @Field("password") String password,
                             @Field("grant_type") String grantType,
                             @Field("client_id") String clientId,
                             @Field("client_secret") String clientSecret);

    @FormUrlEncoded
    @POST("token")
    Call<SessionModel> refreshToken(@Field("refresh_token") String refreshToken,
                                    @Field("grant_type") String grantType,
                                    @Field("client_id") String clientId,
                                    @Field("client_secret") String clientSecret);

}
