package com.moviesdb.moviesdbmvvm.dagger.module;

import android.content.Context;

import com.moviesdb.moviesdbmvvm.activity.MovieDetailsActivity;
import com.moviesdb.moviesdbmvvm.activity.adapter.MainActivityRecyclerViewAdapter;
import com.moviesdb.moviesdbmvvm.activity.adapter.MovieDetailsRecyclerViewAdapter;
import com.moviesdb.moviesdbmvvm.activity.adapter.MoviesLineAdapter;
import com.moviesdb.moviesdbmvvm.dagger.context.ApplicationContext;
import com.moviesdb.moviesdbmvvm.dagger.scope.MovieDetailsScope;
import com.moviesdb.moviesdbmvvm.viewmodel.base.ViewModelFactory;
import com.moviesdb.moviesdbmvvm.viewmodel.movie_details.MovieDetailsViewModel;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
@Module()
public class MovieDetailsModule {

    private final MovieDetailsActivity movieDetailsActivity;
    private int movieId;

    public MovieDetailsModule (MovieDetailsActivity movieDetailsActivity,int movieId){
        this.movieDetailsActivity = movieDetailsActivity;
        this.movieId = movieId;
    }

    @Provides
    @MovieDetailsScope
    public MoviesLineAdapter moviesLineAdapter(Picasso picasso){
        return new MoviesLineAdapter(picasso);
    }

    @Provides
    @MovieDetailsScope
    public MovieDetailsRecyclerViewAdapter movieDetailsRecyclerViewAdapter(Picasso picasso){
        return new MovieDetailsRecyclerViewAdapter(picasso,movieDetailsActivity);
    }


    @Provides
    @MovieDetailsScope
    public ViewModelFactory<MovieDetailsViewModel> viewModelViewModelFactory(){
        return ()->new MovieDetailsViewModel(movieId, movieDetailsActivity.getApplicationContext());
    }



}
