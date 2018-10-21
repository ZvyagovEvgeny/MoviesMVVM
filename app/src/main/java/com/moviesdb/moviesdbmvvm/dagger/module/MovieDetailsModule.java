package com.moviesdb.moviesdbmvvm.dagger.module;

import com.moviesdb.moviesdbmvvm.activity.MovieDetailsActivity;
import com.moviesdb.moviesdbmvvm.activity.adapter.MainActivityRecyclerViewAdapter;
import com.moviesdb.moviesdbmvvm.activity.adapter.MoviesLineAdapter;
import com.moviesdb.moviesdbmvvm.dagger.scope.MovieDetailsScope;
import com.moviesdb.moviesdbmvvm.viewmodel.base.ViewModelFactory;
import com.moviesdb.moviesdbmvvm.viewmodel.movie_details.MovieDetailsViewModel;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
@Module()
public class MovieDetailsModule {

    private final MovieDetailsActivity movieDetailsActivity;

    public MovieDetailsModule (MovieDetailsActivity movieDetailsActivity){
        this.movieDetailsActivity = movieDetailsActivity;
    }

    @Provides
    @MovieDetailsScope
    public MoviesLineAdapter moviesLineAdapter(Picasso picasso){
        return new MoviesLineAdapter(picasso);
    }

    @Provides
    @MovieDetailsScope
    public MainActivityRecyclerViewAdapter mainActivityRecyclerViewAdapter(Picasso picasso){
        return new MainActivityRecyclerViewAdapter(picasso,movieDetailsActivity);
    }


    @Provides
    @MovieDetailsScope
    public ViewModelFactory<MovieDetailsViewModel> viewModelViewModelFactory(){
        return ()->new MovieDetailsViewModel();
    }



}
