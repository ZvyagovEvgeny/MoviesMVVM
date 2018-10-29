package com.moviesdb.moviesdbmvvm.dagger.component;

import com.moviesdb.moviesdbmvvm.ui.moviedetails.MovieDetailsActivity;
import com.moviesdb.moviesdbmvvm.dagger.module.MovieDetailsModule;
import com.moviesdb.moviesdbmvvm.dagger.scope.MovieDetailsScope;

import dagger.Component;


@MovieDetailsScope
@Component(dependencies = {MoviesDBComponent.class} ,modules = {MovieDetailsModule.class})
public interface MovieDetailsComponent {


    void injectActivity(MovieDetailsActivity mainActivity);

}
