package com.moviesdb.moviesdbmvvm.viewmodel.main.movies_line;

public abstract class Event {

    private Object sender;

    public Event(Object sender) {
        this.sender = sender;
    }

}

