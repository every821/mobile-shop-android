package com.bateeqshop.rest;

import com.bateeqshop.model.DevModelWeather;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DevRest
{
    @GET("data/2.5/forecast/daily?lat=35&lon=139&cnt=10&mode=json&appid=803d8c3f4ad4e25b47ea5f90d3309226")
    Call<DevModelWeather> getWeather();
}
