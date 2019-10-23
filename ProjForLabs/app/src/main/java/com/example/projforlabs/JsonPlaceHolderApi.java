package com.example.projforlabs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("cars")
    Call<List<Driver>> getDriver();


}
