package com.moviesdb.moviesdbmvvm.dagger.module;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import com.moviesdb.moviesdbmvvm.dagger.module.qualifier.OpenMovieDBQualifier;
import com.moviesdb.moviesdbmvvm.network.MovieDatabaseApi;
import com.moviesdb.moviesdbmvvm.network.QueryParamsInterceptor;
import com.moviesdb.moviesdbmvvm.dagger.scope.MovieDBApplicationScope;
import com.moviesdb.moviesdbmvvm.utils.Constants;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {OkHttpClientModule.class, ApiModule.class})
public class OpenMovieDBApiModule {

    @Provides
    public MovieDatabaseApi getMovieSocialNetworkAPi(@OpenMovieDBQualifier Retrofit retrofit){
        return retrofit.create(MovieDatabaseApi.class);
    }

    @MovieDBApplicationScope
    @OpenMovieDBQualifier
    @Provides
    public Retrofit getRetrofit(OkHttpClient okHttpClient,
                                GsonConverterFactory gsonConverterFactory){

//        okHttpClient.interceptors().add(new
//                QueryParamsInterceptor(Constants.defaultParamsForOpenMovieDatabaseApi));
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.moviesDBSocialNetwork)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }




}
