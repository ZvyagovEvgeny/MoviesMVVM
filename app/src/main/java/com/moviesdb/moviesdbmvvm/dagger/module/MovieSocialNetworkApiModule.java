package com.moviesdb.moviesdbmvvm.dagger.module;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import com.moviesdb.moviesdbmvvm.dagger.module.qualifier.SocialNetworkApiQualifier;
import com.moviesdb.moviesdbmvvm.network.QueryParamsInterceptor;
import com.moviesdb.moviesdbmvvm.utils.Constants;
import com.moviesdb.moviesdbmvvm.dagger.scope.MovieDBApplicationScope;
import com.moviesdb.moviesdbmvvm.network.MovieSocialNetworkApi;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {OkHttpClientModule.class,ContextModule.class,ApiModule.class})
public class MovieSocialNetworkApiModule {

    @Provides
    public MovieSocialNetworkApi getMovieSocialNetworkAPi(
            @SocialNetworkApiQualifier
                    Retrofit retrofit){
        return retrofit.create(MovieSocialNetworkApi.class);
    }

    @MovieDBApplicationScope
    @Provides
    @SocialNetworkApiQualifier
    public Retrofit getRetrofit(OkHttpClient okHttpClient,
                                GsonConverterFactory gsonConverterFactory){

        okHttpClient.interceptors().add(new QueryParamsInterceptor(Constants.defaultParamsForSocialNetworkApi));
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.moviesDBSocialNetwork)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
