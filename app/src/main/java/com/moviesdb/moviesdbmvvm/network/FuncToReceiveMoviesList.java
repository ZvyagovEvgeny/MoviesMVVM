package com.moviesdb.moviesdbmvvm.network;

import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieQueryResult;

import io.reactivex.Observable;

public interface FuncToReceiveMoviesList {

    Observable<MovieQueryResult> getMovies(String lang,int page,String region);

}
