package com.moviesdb.moviesdbmvvm.dagger.module;

import android.content.Context;

import com.moviesdb.moviesdbmvvm.dagger.context.ApplicationContext;
import com.moviesdb.moviesdbmvvm.dagger.scope.MovieDBApplicationScope;
import com.moviesdb.moviesdbmvvm.utils.Constants;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ContextModule.class})
public class ConstantsModule {

    @Provides
    @MovieDBApplicationScope
    Constants constants(@ApplicationContext Context context){
        return new Constants(context);
    }

}
