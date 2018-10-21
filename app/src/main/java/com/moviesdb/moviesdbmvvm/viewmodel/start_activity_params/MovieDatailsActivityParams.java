package com.moviesdb.moviesdbmvvm.viewmodel.start_activity_params;

import android.content.Context;
import android.content.Intent;

import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieBase;

public class MovieDatailsActivityParams extends AnotherActivity {

    private MovieBase movieBase;

    public MovieDatailsActivityParams(MovieBase movieBase) {
        this.movieBase = movieBase;
    }

    @Override
    public void startActivity(Context context) {

    }

    public MovieBase getMovieBase() {
        return movieBase;
    }
}
