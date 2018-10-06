package com.moviesdb.moviesdbmvvm.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.moviesdb.moviesdbmvvm.Movie;

public class MovieListItemViewModel extends BaseObservable {

    public ObservableField<Movie> movie;
    public ObservableField<String> name;

    public MovieListItemViewModel(Movie movie){
        this.movie = new ObservableField<>(movie);
        this.name = new ObservableField<>(movie.title);
    }

}
