package com.moviesdb.moviesdbmvvm.viewmodel.main.movies_line;

import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieBase;

public class OnMovieItemSelected{

    public MovieListItemViewModel viewModel;
    public ItemClickedEventType itemClickedEventType;

    public OnMovieItemSelected(MovieListItemViewModel viewModel, ItemClickedEventType itemClickedEventType) {
        this.viewModel = viewModel;
        this.itemClickedEventType = itemClickedEventType;
    }
}
