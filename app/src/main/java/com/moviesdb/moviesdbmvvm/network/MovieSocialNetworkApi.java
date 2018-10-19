package com.moviesdb.moviesdbmvvm.network;

import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieQueryResult;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieSocialNetworkApi {

    public final String LANG_ENG = "en-US";
    public final String REGION_USA = "US";

    public enum ListType {

        POP("popular"),UPCOMING("upcoming"),TOP_RATED("top_rated"),NOW_PLAYING("now_playing");

        String path;
        private ListType(String path){
            this.path = path;
        }

        @Override
        public String toString() {
            return path;
        }
    }

    public enum Region{

        USA("US");

        String path;
        private Region(String path){
            this.path = path;
        }

        @Override
        public String toString() {
            return path;
        }
    }

    public enum Lang{
        ENG("en-US");

        String path;
        private Lang(String path){
            this.path = path;
        }

        @Override
        public String toString() {
            return path;
        }
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
