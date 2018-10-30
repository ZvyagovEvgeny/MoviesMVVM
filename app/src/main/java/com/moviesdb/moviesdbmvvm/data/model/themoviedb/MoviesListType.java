package com.moviesdb.moviesdbmvvm.data.model.themoviedb;

public enum MoviesListType {

    upcoming("Upcoming Movies"), top_rated("Top Rated movies"),popular("Popular Movies"),now_playing("Not Playing");
    private String title;


    public String getTitle() {
        return title;
    }

    MoviesListType(String name){
        this.title = name;
    }
}
