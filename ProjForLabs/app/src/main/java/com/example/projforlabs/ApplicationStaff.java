package com.example.projforlabs;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationStaff extends Application {
    private DataApi api;
    private FirebaseAuth auth;

    public void onCreate() {
        super.onCreate();
        api = createApi();
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public DataApi getApi() {
        return api;
    }

    public DataApi createApi() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-android-course-528bc.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(DataApi.class);
    }
}