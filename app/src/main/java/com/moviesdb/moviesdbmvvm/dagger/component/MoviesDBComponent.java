package com.moviesdb.moviesdbmvvm.dagger.component;

import com.moviesdb.moviesdbmvvm.dagger.module.ConstantsModule;
import com.moviesdb.moviesdbmvvm.dagger.module.OpenMovieDBApiModule;
import com.moviesdb.moviesdbmvvm.network.MovieDatabaseApi;
import com.moviesdb.moviesdbmvvm.dagger.scope.MovieDBApplicationScope;
import com.moviesdb.moviesdbmvvm.network.MovieSocialNetworkApi;
import com.moviesdb.moviesdbmvvm.dagger.module.MovieSocialNetworkApiModule;
import com.moviesdb.moviesdbmvvm.dagger.module.PicassoModule;
import com.moviesdb.moviesdbmvvm.utils.Constants;
import com.squareup.picasso.Picasso;

import dagger.Component;

@MovieDBApplicationScope//singleton
@Component(modules = {PicassoModule.class,MovieSocialNetworkApiModule.class, OpenMovieDBApiModule.class
, ConstantsModule.class})
public interface MoviesDBComponent {

    Constants getConstants();
    MovieDatabaseApi getMovieDatabaseApi();
    MovieSocialNetworkApi getMovieSocialNetworkApi();
    Picasso getPicasso();
}
