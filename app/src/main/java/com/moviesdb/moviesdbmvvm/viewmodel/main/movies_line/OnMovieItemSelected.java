package com.moviesdb.moviesdbmvvm.viewmodel.main.movies_line;

public class OnMovieItemSelected extends Event{

    private MovieListItemViewModel movie;

    public OnMovieItemSelected(MovieListItemViewModel movie, ItemClickedEventType eventType) {
        super(movie);
        this.movie = movie;
        this.eventType = eventType;
    }

    public MovieListItemViewModel getMovie() {
        return movie;
    }

    public ItemClickedEventType getEventType() {
        return eventType;
    }

    private ItemClickedEventType eventType;
}
