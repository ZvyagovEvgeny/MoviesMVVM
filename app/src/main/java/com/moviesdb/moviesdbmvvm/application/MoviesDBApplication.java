package com.moviesdb.moviesdbmvvm.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.moviesdb.moviesdbmvvm.dagger.component.DaggerMoviesDBComponent;
import com.moviesdb.moviesdbmvvm.dagger.component.MoviesDBComponent;
import com.moviesdb.moviesdbmvvm.dagger.module.ContextModule;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MoviesDBApplication extends Application {

    private Scheduler scheduler;

    private MoviesDBComponent moviesDBComponent;
    public static MoviesDBApplication get(Activity activity){
        return (MoviesDBApplication) activity.getApplication();
    }

    private static MoviesDBApplication get(Context context) {
        return (MoviesDBApplication) context.getApplicationContext();
    }

    public static MoviesDBApplication create(Context context) {
        return MoviesDBApplication.get(context);
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

    public Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }

        return scheduler;
    }
}
