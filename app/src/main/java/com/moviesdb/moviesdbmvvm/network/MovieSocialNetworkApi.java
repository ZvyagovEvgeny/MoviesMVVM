package com.moviesdb.moviesdbmvvm.network;

import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieQueryResult;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieSocialNetworkApi {

    public final String LANG_ENG = "en-US";
    public final String REGION_USA = "US";

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
