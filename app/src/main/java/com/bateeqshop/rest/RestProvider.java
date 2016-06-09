package com.bateeqshop.rest;

import com.bateeqshop.config.ApiConfig;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestProvider
{
    private static DevRest mDevRest;
    private static AuthRest mAuthRest;
    private static ResourceRest mResourceRest;
    private static ResourceRest mExposedResourceRest;

    public static DevRest getDevRest() {
        if (mDevRest == null)
        {
            mDevRest = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl("http://api.openweathermap.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(DevRest.class);
        }
        return mDevRest;
    }

    public static AuthRest getAuthRest()
    {
        if (mAuthRest == null)
        {
            mAuthRest = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(ApiConfig.AUTH_ENDPOINT_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(
                            new GsonBuilder()
                                    .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                                    .create()
                    ))
                    .build()
                    .create(AuthRest.class);
        }
        return mAuthRest;
    }

    public static ResourceRest getResourceRest()
    {
        if (mResourceRest == null)
        {
            mResourceRest = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(ApiConfig.RESOURCE_ENDPOINT_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(
                            new GsonBuilder()
                                    .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                                    .create()
                    ))
                    .build()
                    .create(ResourceRest.class);
        }
        return mResourceRest;
    }

    public static ResourceRest getExposedResourceRest()
    {
        //new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
        if (mExposedResourceRest == null)
        {
            mExposedResourceRest = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(ApiConfig.RESOURCE_ENDPOINT_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
                    .build()
                    .create(ResourceRest.class);
        }
        return mExposedResourceRest;
    }
}
