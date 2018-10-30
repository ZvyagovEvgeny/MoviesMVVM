package com.moviesdb.moviesdbmvvm.network;

import com.moviesdb.moviesdbmvvm.data.model.themoviedb.MovieQueryResult;

import io.reactivex.Observable;

public  interface MovieListRepository {
    Observable<MovieQueryResult> getMivies(String lang, int page, String region );

}

