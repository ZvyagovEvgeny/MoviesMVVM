package com.moviesdb.moviesdbmvvm.network;

import com.google.gson.annotations.SerializedName;
import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieQueryResult;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieSocialNetworkApi {

    enum ListType {
        @SerializedName("popular") POP,
        @SerializedName("upcoming") UPCOMING,
        @SerializedName("top_rated") TOP_RATED,
        @SerializedName("now_playing") NOW_PLAYING;
    }
    enum Region{
        @SerializedName("US") USA
    }
    enum Lang{
       @SerializedName("en-US") ENG;
    }

    @GET("movie/{type}")
    Observable<MovieQueryResult> getMovies(@Path("type") ListType type,
                                           @Query("page") int page,
                                           @Query("language") Lang lang,
                                           @Query("region")Region region);

    @GET("movie/popular")
    Observable<MovieQueryResult> getPopularMovies(@Query("language") String lang,
                                                  @Query("page") int page,
                                                  @Query("region")String region);
    @GET("movie/upcoming")
    Observable<MovieQueryResult>  getUpcomingMovies(@Query("language") String lang,
                                      @Query("page") int page,
                                      @Query("region")String region);


    @GET("movie/top_rated")
    Observable<MovieQueryResult>  getTopRatedMovies(@Query("language") String lang,
                                       @Query("page") int page,
                                       @Query("region")String region);

    @GET("movie/now_playing")
    Observable<MovieQueryResult>  getNowPlayingMovies(@Query("language") String lang,
                                       @Query("page") int page,
                                       @Query("region")String region);


}
