package com.moviesdb.moviesdbmvvm.data.model.themoviedb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.WillClose;

public class UserLoginAndPassword {

    @SerializedName("username")
    @Expose
    private String userName;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("request_token")
    @Expose
    private String token;

    public UserLoginAndPassword(String userName, String password, String token) {
        this.userName = userName;
        this.password = password;
        this.token = token;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
