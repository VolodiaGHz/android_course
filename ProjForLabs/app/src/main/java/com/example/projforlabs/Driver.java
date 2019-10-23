package com.example.projforlabs;

import com.google.gson.annotations.SerializedName;

public class Driver {
    private String name;
    @SerializedName("car model")
    private String carModel;
    private String raiting;
    private String status;

    public Driver(String name, String carModel, String raiting, String status) {
        this.name = name;
        this.carModel = carModel;
        this.raiting = raiting;
        this.status = status;
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

}
