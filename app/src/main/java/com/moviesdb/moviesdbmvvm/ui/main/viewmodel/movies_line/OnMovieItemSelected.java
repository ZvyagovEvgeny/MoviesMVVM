package com.moviesdb.moviesdbmvvm.ui.main.viewmodel.movies_line;

public class OnMovieItemSelected{

    public MovieListItemViewModel viewModel;
    public ItemClickedEventType itemClickedEventType;

    public OnMovieItemSelected(MovieListItemViewModel viewModel, ItemClickedEventType itemClickedEventType) {
        this.viewModel = viewModel;
        this.itemClickedEventType = itemClickedEventType;
    }
}
