package com.moviesdb.moviesdbmvvm.network;

import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieQueryResult;

import io.reactivex.Observable;

public interface FuncToRecieveMoviesList {

    Observable<MovieQueryResult> getMovies(String lang,int page,String region);

}
