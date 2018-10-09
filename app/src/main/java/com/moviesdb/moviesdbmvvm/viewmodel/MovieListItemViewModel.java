package com.moviesdb.moviesdbmvvm.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableField;

import com.moviesdb.moviesdbmvvm.Movie;
import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieBase;

public class MovieListItemViewModel extends BaseObservable {

    public ObservableField<MovieBase> movie;
    public ObservableField<String> backdrop = new ObservableField<>("");
    public ObservableField<String> poster = new ObservableField<>("");

    public final String imagePrefix = "https://image.tmdb.org/t/p/w500/";

    public final String imagePrefixOriginal = "https://image.tmdb.org/t/p/original";

    public MovieListItemViewModel(MovieBase movie){
        this.movie = new ObservableField<>(movie);
        String backdrop = imagePrefix + movie.getBackdropPath();
        String poster = imagePrefix + movie.getPosterPath();
        this.poster.set(poster);
        this.backdrop.set(backdrop);

    }

}
