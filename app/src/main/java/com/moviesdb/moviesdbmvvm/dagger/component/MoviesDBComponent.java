package com.moviesdb.moviesdbmvvm.dagger.component;

import android.support.constraint.solver.widgets.Helper;

import com.moviesdb.moviesdbmvvm.dagger.module.ApiHelperModule;
import com.moviesdb.moviesdbmvvm.dagger.module.ConstantsModule;
import com.moviesdb.moviesdbmvvm.dagger.module.OpenMovieDBApiModule;
import com.moviesdb.moviesdbmvvm.data.remote.ApiHelper;
import com.moviesdb.moviesdbmvvm.data.remote.AppApiHelper;
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
, ConstantsModule.class, ApiHelperModule.class})
public interface MoviesDBComponent {

    Constants getConstants();
    MovieDatabaseApi getMovieDatabaseApi();
    MovieSocialNetworkApi getMovieSocialNetworkApi();
    Picasso getPicasso();
    AppApiHelper apiHelper();

}
