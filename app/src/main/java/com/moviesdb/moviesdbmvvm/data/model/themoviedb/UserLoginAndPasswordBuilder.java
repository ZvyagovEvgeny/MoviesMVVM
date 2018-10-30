package com.moviesdb.moviesdbmvvm.data.model.themoviedb;

public class UserLoginAndPasswordBuilder {
    private String userName;
    private String password;
    private String token;

    public UserLoginAndPasswordBuilder setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserLoginAndPasswordBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserLoginAndPasswordBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    public UserLoginAndPassword createUserLoginAndPassword() {
        return new UserLoginAndPassword(userName, password, token);
    }
}