package com.moviesdb.moviesdbmvvm.ui.main.viewmodel.movies_line;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieBase;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class MovieListItemViewModel extends BaseObservable {

    public ObservableField<MovieBase> movie;
    public ObservableField<String> backdrop = new ObservableField<>("");
    public ObservableField<String> poster = new ObservableField<>("");
    public ObservableBoolean trailerEnabled = new ObservableBoolean(false);

    private PublishSubject<OnMovieItemSelected> itemClickedObjectSubject = PublishSubject.create();

    public Observable<OnMovieItemSelected> onItemClicked(){
        return itemClickedObjectSubject;
    }

    public  void onPosterClicked(){

        itemClickedObjectSubject.onNext(new OnMovieItemSelected(this,ItemClickedEventType.POSTER));
    }

    public void onWishListClicked(){
        itemClickedObjectSubject.onNext(new OnMovieItemSelected(this,ItemClickedEventType.WISH_LIST_ICON));
    }


    public final String imagePrefix = "https://image.tmdb.org/t/p/w500/";

    public final String imagePrefixOriginal = "https://image.tmdb.org/t/p/original";

    public MovieListItemViewModel(MovieBase movie){

        this.movie = new ObservableField<>(movie);
        String backdrop = imagePrefix + movie.getBackdropPath();
        String poster = imagePrefix + movie.getPosterPath();
        this.poster.set(poster);
        this.backdrop.set(backdrop);
        this.trailerEnabled.set(!movie.getVideo());

    }

}
