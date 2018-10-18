package com.example.user.qr_try_lokal.Model;

import com.google.gson.annotations.SerializedName;

public class LoginPost {

    @SerializedName("username")
    private String username_login;
    @SerializedName("password")
    private String password;

    public String getUsername_login() {
        return username_login;
    }

    public void setUsername_login(String username_login) {
        this.username_login = username_login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginPost(String username_login, String password) {

        this.username_login = username_login;
        this.password = password;
    }

    public LoginPost() {

    }
}
