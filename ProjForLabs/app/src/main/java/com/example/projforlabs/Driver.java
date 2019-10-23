package com.example.projforlabs;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class Driver {
    private String name;
    @SerializedName("car model") //анотація лише тут, тому що назва в класі відрізняється від назви в json
    private String carModel;
    private String raiting;
    private String status;
    private String photo;

    public Driver(String name, String carModel, String raiting, String status, String photo) {
        this.name = name;
        this.carModel = carModel;
        this.raiting = raiting;
        this.status = status;
        this.photo = photo;
    }


    public String getName() {
        return name;
    }


    public String getCarModel() {
        return carModel;
    }


    public String getReiting() {
        return raiting;
    }


    public String getStatus() {
        return status;
    }

    public Uri getPhoto() {
        return Uri.parse(photo);
    }
}
