package com.moviesdb.moviesdbmvvm.viewmodel;

import android.databinding.BaseObservable;

import com.moviesdb.moviesdbmvvm.Movie;
import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieBase;

import java.util.ArrayList;
import java.util.List;

public class MovieListCowerFlowViewModel extends BaseObservable {

    public List<MovieListItemViewModel> moviesViewModels = new ArrayList<>();

    public MovieListCowerFlowViewModel(List<MovieBase> movies) {
        for(MovieBase movie : movies){
            moviesViewModels.add(new MovieListItemViewModel(movie));
        }
    }

    public MovieListCowerFlowViewModel() {
    }
}
