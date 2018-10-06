package com.moviesdb.moviesdbmvvm.dagger.module;

import android.content.Context;

import com.moviesdb.moviesdbmvvm.dagger.context.ApplicationContext;
import com.moviesdb.moviesdbmvvm.dagger.scope.MovieDBApplicationScope;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module(includes = {OkHttpClientModule.class,ContextModule.class})
public class PicassoModule {

    @MovieDBApplicationScope
    @Provides
    Picasso picasso(@ApplicationContext Context context,
                    OkHttp3Downloader okHttp3Downloader){
        return new Picasso.Builder(context)
                .downloader(okHttp3Downloader)
                .build();

    }

    @Provides
    public OkHttp3Downloader okHttp3Downloader(OkHttpClient okHttpClient){
        return new OkHttp3Downloader(okHttpClient);
    }

}
