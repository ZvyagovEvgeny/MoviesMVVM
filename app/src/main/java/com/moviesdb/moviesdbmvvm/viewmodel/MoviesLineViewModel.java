package com.moviesdb.moviesdbmvvm.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieBase;
import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieQueryResult;

import com.moviesdb.moviesdbmvvm.network.MovieSocialNetworkApi;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import timber.log.Timber;

public class MoviesLineViewModel extends BaseObservable{

    public ObservableField<String> rowName = new ObservableField<>("");
    public List<MovieListItemViewModel> movies = new LinkedList<>();


    public MoviesLineViewModel(String listTitle,
                                    io.reactivex.Observable<MovieQueryResult> movies,
                                    Scheduler scheduler){

        this.rowName.set(listTitle);
        Disposable disposable = movies.observeOn(scheduler)
                .map((result)-> {
                    List<MovieListItemViewModel> viewModels = new ArrayList<>();
                    for (MovieBase movieBase :result.getResults())
                        viewModels.add(new MovieListItemViewModel(movieBase));
                    return viewModels;

                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe((result)->{
                    setMoviesList(result);
                },this::onError);

    }

    private void onError(Throwable e){
        Timber.d(e);
    }

    private void setMoviesList(List<MovieListItemViewModel> viewModels){
        movies = viewModels;
        notifyChange();
    }

    public void onSeeMoreItems(){

    }
}
