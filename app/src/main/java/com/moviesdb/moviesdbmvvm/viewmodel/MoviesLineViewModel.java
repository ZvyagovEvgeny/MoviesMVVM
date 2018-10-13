package com.moviesdb.moviesdbmvvm.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieBase;
import com.moviesdb.moviesdbmvvm.model.themoviedb.MoviesListType;

import java.util.LinkedList;
import java.util.List;

public class MoviesLineViewModel extends BaseObservable {


    public ObservableField<String> rowName = new ObservableField<>("");

    public interface ButtonCallback{
        void callback(Object object);
    }

    private ButtonCallback buttonCallback;


    public void onSeeMoreIrems(){

    }

    private MoviesListType moviesListType;


    public List<MovieListItemViewModel> movies = new LinkedList<>();

    public MoviesLineViewModel( MoviesListType moviesListType){
        this.moviesListType = moviesListType;
        this.rowName.set(moviesListType.getTitle());
    }

    public MoviesLineViewModel( MoviesListType moviesListType,
                                List<MovieBase> movieBases){
        this.moviesListType = moviesListType;
        this.rowName.set(moviesListType.getTitle());
        setList(movieBases);

    }

    public void setList(List<MovieBase> movieBases){
        movies.clear();
        for(MovieBase movieBase : movieBases){
            movies.add(new MovieListItemViewModel(movieBase));
        }
    }
}
