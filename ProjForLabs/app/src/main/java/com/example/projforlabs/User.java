package com.example.projforlabs;

public class User {

    public String email, username, phone;

    public User(){
    }

    public User(String email, String username, String phone){
        this.email = email;
        this.username = username;
        this.phone = phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
