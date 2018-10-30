package com.moviesdb.moviesdbmvvm.dagger.module;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import com.moviesdb.moviesdbmvvm.R;
import com.moviesdb.moviesdbmvvm.dagger.context.ApplicationContext;
import com.moviesdb.moviesdbmvvm.dagger.module.qualifier.SocialNetworkApiQualifier;
import com.moviesdb.moviesdbmvvm.utils.EnumConverter;
import com.moviesdb.moviesdbmvvm.network.QueryParamsInterceptor;
import com.moviesdb.moviesdbmvvm.utils.Constants;
import com.moviesdb.moviesdbmvvm.dagger.scope.MovieDBApplicationScope;
import com.moviesdb.moviesdbmvvm.network.MovieSocialNetworkApi;

import java.util.HashMap;
import java.util.Map;

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
    public Retrofit getRetrofit(@SocialNetworkApiQualifier OkHttpClient okHttpClient,
                                GsonConverterFactory gsonConverterFactory){

        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.moviesDBSocialNetwork)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(new EnumConverter())
                .build();
    }

    @MovieDBApplicationScope
    @Provides
    @SocialNetworkApiQualifier
    public OkHttpClient okHttpClient(OkHttpClient.Builder builder, @ApplicationContext  Context context){

        String apiKey = context.getString(R.string.api_key_social_network);

        Map<String,String> params = new HashMap<>();
        params.put("api_key",apiKey);
        builder.addInterceptor(new QueryParamsInterceptor(params));
        return builder.build();
    }

}
