package com.moviesdb.moviesdbmvvm.dagger.component;

import com.moviesdb.moviesdbmvvm.activity.MainActivity;
import com.moviesdb.moviesdbmvvm.activity.MovieDetailsActivity;
import com.moviesdb.moviesdbmvvm.dagger.module.MainActivityModule;
import com.moviesdb.moviesdbmvvm.dagger.module.MovieDetailsModule;
import com.moviesdb.moviesdbmvvm.dagger.scope.MainActivityScope;
import com.moviesdb.moviesdbmvvm.dagger.scope.MovieDetailsScope;
import com.moviesdb.moviesdbmvvm.network.MovieSocialNetworkApi;

import dagger.Component;


@MovieDetailsScope
@Component(dependencies = {MoviesDBComponent.class} ,modules = {MovieDetailsModule.class})
public interface MovieDetailsComponent {


    void injectActivity(MovieDetailsActivity mainActivity);

}
