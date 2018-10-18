package com.example.user.qr_try_lokal.Model;

import com.google.gson.annotations.SerializedName;

public class UserPut {

    @SerializedName("username")
    private String username;
    @SerializedName("status")
    private String status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserPut() {

    }

    public UserPut(String username, String status) {

        this.username = username;
        this.status = status;
    }
}
