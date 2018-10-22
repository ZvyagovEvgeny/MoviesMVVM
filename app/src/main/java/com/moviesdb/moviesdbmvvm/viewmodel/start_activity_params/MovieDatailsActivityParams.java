package com.moviesdb.moviesdbmvvm.viewmodel.start_activity_params;

import android.content.Context;
import android.content.Intent;

import com.moviesdb.moviesdbmvvm.activity.MovieDetailsActivity;
import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieBase;

public class MovieDatailsActivityParams extends AnotherActivity {

    private MovieBase movieBase;

    public MovieDatailsActivityParams(MovieBase movieBase) {
        this.movieBase = movieBase;
    }

    @Override
    public void startActivity(Context context) {

        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra("movieId", movieBase.getId());
        context.startActivity(intent);

    }

    public MovieBase getMovieBase() {
        return movieBase;
    }
}
