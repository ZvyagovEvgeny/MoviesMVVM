package com.moviesdb.moviesdbmvvm.model.themoviedb;

public enum MoviesListQueryTypes {

    upcoming("upcoming"), top_rated("top_rated"),popular("popular"),now_playing("now_playing");
    private String title;


    public String getTitle() {
        return title;
    }

    MoviesListQueryTypes(String name){
        this.title = name;
    }
}
