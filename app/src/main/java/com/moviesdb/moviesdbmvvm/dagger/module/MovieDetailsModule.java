package com.moviesdb.moviesdbmvvm.dagger.module;

import com.moviesdb.moviesdbmvvm.ui.moviedetails.MovieDetailsActivity;

import com.moviesdb.moviesdbmvvm.ui.main.activity.adapter.MoviesLineAdapter;
import com.moviesdb.moviesdbmvvm.dagger.scope.MovieDetailsScope;
import com.moviesdb.moviesdbmvvm.ui.base.viewmodel.ViewModelFactory;
import com.moviesdb.moviesdbmvvm.ui.moviedetails.viewmodel.MovieDetailsViewModel;
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
    public ViewModelFactory<MovieDetailsViewModel> viewModelViewModelFactory(){
        return ()->new MovieDetailsViewModel(movieId, movieDetailsActivity.getApplicationContext());
    }



}
