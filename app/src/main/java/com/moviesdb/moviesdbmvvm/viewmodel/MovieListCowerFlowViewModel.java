package com.moviesdb.moviesdbmvvm.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Observable;

import com.moviesdb.moviesdbmvvm.Movie;
import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieBase;

import java.util.ArrayList;
import java.util.List;

public class MovieListCowerFlowViewModel extends BaseObservable implements ViewModelList<MovieBase> {

    public List<MovieListItemViewModel> moviesViewModels = new ArrayList<>();

    public MovieListCowerFlowViewModel(List<MovieBase> movies) {
        for(MovieBase movie : movies){
            moviesViewModels.add(new MovieListItemViewModel(movie));
        }
    }

    public MovieListCowerFlowViewModel() {
    }

    @Override
    public void insertList(List<MovieListItemViewModel> list) {
        moviesViewModels.clear();
        for(MovieListItemViewModel viewModel : list){
            moviesViewModels.add(viewModel);
        }
        notifyChange();
    }
}
