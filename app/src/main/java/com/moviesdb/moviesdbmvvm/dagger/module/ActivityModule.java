package com.moviesdb.moviesdbmvvm.dagger.module;

import android.content.Context;

import com.moviesdb.moviesdbmvvm.dagger.context.ActivityContext;
import com.moviesdb.moviesdbmvvm.dagger.scope.MovieDBApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final Context context;

    public ActivityModule(Context context) {
        this.context = context;
    }

    @ActivityContext
    @MovieDBApplicationScope
    @Provides
    public Context getContext() {
        return context;
    }
}
