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

public class App extends Application {

    private Scheduler scheduler;

    private MoviesDBComponent moviesDBComponent;
    public static App get(Activity activity){
        return (App) activity.getApplication();
    }

    private static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public static App create(Context context) {
        return App.get(context);
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
