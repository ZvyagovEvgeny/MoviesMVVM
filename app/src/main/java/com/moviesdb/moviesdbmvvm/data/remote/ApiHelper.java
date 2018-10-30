package com.moviesdb.moviesdbmvvm.data.remote;

import com.moviesdb.moviesdbmvvm.data.model.themoviedb.AuthenticationResult;
import com.moviesdb.moviesdbmvvm.data.model.themoviedb.CreditsQueryResult;
import com.moviesdb.moviesdbmvvm.data.model.themoviedb.MovieDetail;
import com.moviesdb.moviesdbmvvm.data.model.themoviedb.MovieQueryResult;
import com.moviesdb.moviesdbmvvm.data.model.themoviedb.MoviesListType;
import com.moviesdb.moviesdbmvvm.data.model.themoviedb.Session;
import com.moviesdb.moviesdbmvvm.data.model.themoviedb.UserLoginAndPassword;
import com.moviesdb.moviesdbmvvm.network.MovieSocialNetworkApi;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiHelper{

    Observable<MovieQueryResult> getMoviesList(MovieSocialNetworkApi.ListType moviesListType,
                                                      int page);

    Observable<MovieDetail> getMovieDetails(int id);

    Observable<CreditsQueryResult> getMovieCredits(int id);

    Observable<Session> authentication(String login,String password);
    Observable<Session> getSession();
}
