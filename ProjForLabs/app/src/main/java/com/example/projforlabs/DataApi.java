package com.example.projforlabs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataApi {
    @GET("cars")
    Call<List<Driver>> getDriver();

    @POST("cars")
    Call<Driver> createDriver(@Body Driver driver);
}