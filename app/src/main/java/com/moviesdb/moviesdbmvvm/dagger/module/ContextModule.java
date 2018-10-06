package com.moviesdb.moviesdbmvvm.dagger.module;

import android.content.Context;

import com.moviesdb.moviesdbmvvm.dagger.context.ApplicationContext;
import com.moviesdb.moviesdbmvvm.dagger.scope.MovieDBApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    private Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @ApplicationContext
    @MovieDBApplicationScope
    @Provides
    public Context getContext() {
        return context.getApplicationContext();
    }
}
