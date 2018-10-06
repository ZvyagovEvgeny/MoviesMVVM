package com.moviesdb.moviesdbmvvm.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.util.Log;

import com.moviesdb.moviesdbmvvm.Movie;
import com.moviesdb.moviesdbmvvm.activity.adapter.MoviesLineAdapter;
import com.moviesdb.moviesdbmvvm.activity.adapter.ViewModelAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class MoviesLineViewModel extends BaseObservable {


    public ObservableField<String> rowName = new ObservableField<>("");
    public ObservableField<ViewModelAdapter> adapter;

    public interface ButtonCallback{
        void callback(Object object);
    }

    private ButtonCallback buttonCallback;

    public enum  MoviesListType{

        POPULAR("Popular"),TOP_RATED("Top related"), COMING_SOON("Coming soon"),BEST_PICTURE_WINNERS("Best picture winner");

        String title;

         MoviesListType(String name){
            this.title = name;
        }
    }

    public void onSeeMoreIrems(){
        Log.d("log",moviesListType.title);
    }

    private MoviesListType moviesListType;


    public List<MovieListItemViewModel> movies = new LinkedList<>();

    public MoviesLineViewModel( MoviesListType moviesListType){
        this.moviesListType = moviesListType;
        this.rowName.set(moviesListType.title);
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("back to the future" + moviesListType.title));
        movies.add(new Movie("Hatico"+ moviesListType.title));
        movies.add(new Movie("How to train"+ moviesListType.title));
        movies.add(new Movie("1"+ moviesListType.title));
        for(Movie movie:movies){
            this.movies.add(new MovieListItemViewModel(movie));
        }
    }
}
