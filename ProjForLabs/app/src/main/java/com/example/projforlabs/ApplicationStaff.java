package com.example.projforlabs;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationStaff extends Application {
    private JsonDataApi json;

    public void onCreate() {
        super.onCreate();
        json = createApi();
    }

    public JsonDataApi getApi() {
        return json;
    }

    public JsonDataApi createApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-android-course-528bc.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit.create(JsonDataApi.class);
    }
}
