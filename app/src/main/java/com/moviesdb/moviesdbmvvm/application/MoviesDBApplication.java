package com.moviesdb.moviesdbmvvm.application;

import android.app.Activity;
import android.app.Application;

import com.moviesdb.moviesdbmvvm.dagger.component.DaggerMoviesDBComponent;
import com.moviesdb.moviesdbmvvm.dagger.component.MoviesDBComponent;
import com.moviesdb.moviesdbmvvm.dagger.module.ContextModule;

import timber.log.Timber;

public class MoviesDBApplication extends Application {

    private MoviesDBComponent moviesDBComponent;
    public static MoviesDBApplication get(Activity activity){
        return (MoviesDBApplication) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        moviesDBComponent = DaggerMoviesDBComponent
                .builder()
                .contextModule(
                        new ContextModule(this))
                .build();

    }

    public MoviesDBComponent getMoviesDBComponent() {
        return moviesDBComponent;
    }
}
