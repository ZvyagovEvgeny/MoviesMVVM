package com.moviesdb.moviesdbmvvm.viewmodel.main.movies_line;

public class OnSeeMoreItemsEvent extends Event {

    private MoviesLineViewModel moviesLineViewModel;

    public OnSeeMoreItemsEvent(MoviesLineViewModel sender) {
        super(sender);
        moviesLineViewModel = sender;
    }

    public MoviesLineViewModel getMoviesLineViewModel() {
        return moviesLineViewModel;
    }
}

