package com.moviesdb.moviesdbmvvm.viewmodel.start_activity_params;

import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieBase;

public class MovieDatailsActivity  extends AnotherActivity {

    private MovieBase movieBase;

    public MovieDatailsActivity(MovieBase movieBase) {
        this.movieBase = movieBase;
    }

    public MovieBase getMovieBase() {
        return movieBase;
    }
}
