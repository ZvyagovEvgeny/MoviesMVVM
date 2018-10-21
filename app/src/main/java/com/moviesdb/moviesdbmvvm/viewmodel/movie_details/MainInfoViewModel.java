package com.moviesdb.moviesdbmvvm.viewmodel.movie_details;

import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableField;

import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieBase;

public class MainInfoViewModel extends BaseObservable {

    public ObservableField<MovieBase> movieBaseObservableField = new ObservableField<>();

    public MainInfoViewModel(MovieBase movieBaseObservableField) {
        this.movieBaseObservableField.set(movieBaseObservableField);
    }


}
