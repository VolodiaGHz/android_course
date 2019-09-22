package com.example.projforlabs;

public class User {

    public String username, phone;

    public User(){
    }

    public User(String username, String phone){
        this.username = username;
        this.phone = phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }
}
