package com.moviesdb.moviesdbmvvm.network;

import com.google.gson.annotations.SerializedName;
import com.moviesdb.moviesdbmvvm.data.model.themoviedb.*;
import com.moviesdb.moviesdbmvvm.data.model.themoviedb.MovieQueryResult;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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


    @GET("movie/{movie_id}")
    Observable<MovieDetail> getMovieDetails(@Path("movie_id") int id,
                                            @Query("language") Lang lang);

    @GET("movie/{movie_id}/credits")
    Observable<CreditsQueryResult> getMovieCredits(@Path("movie_id") int id);


    @GET("authentication/token/new")
    Observable<AuthenticationResult> createRequestToken();

    @POST("authentication/token/validate_with_login")
    Observable<AuthenticationResult> validateWithLogin(@Body UserLoginAndPassword login);

    @POST("authentication/session/new")
    Observable<Session> createSession(@Body AuthenticationResult authenticationResult);

}
