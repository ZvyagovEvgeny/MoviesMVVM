package com.moviesdb.moviesdbmvvm.dagger.module;


import com.moviesdb.moviesdbmvvm.dagger.scope.MovieDBApplicationScope;
import com.moviesdb.moviesdbmvvm.data.remote.ApiHelper;
import com.moviesdb.moviesdbmvvm.data.remote.AppApiHelper;
import com.moviesdb.moviesdbmvvm.network.MovieSocialNetworkApi;

import dagger.Module;
import dagger.Provides;

@Module(includes = {MovieSocialNetworkApiModule.class})
public class ApiHelperModule {

    @Provides
    @MovieDBApplicationScope
    public AppApiHelper apiHelper(MovieSocialNetworkApi movieSocialNetworkApi){

        return new AppApiHelper(movieSocialNetworkApi);
    }

}
