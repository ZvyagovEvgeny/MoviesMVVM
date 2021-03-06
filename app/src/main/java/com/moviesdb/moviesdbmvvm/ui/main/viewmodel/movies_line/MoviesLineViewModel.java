package com.moviesdb.moviesdbmvvm.ui.main.viewmodel.movies_line;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.moviesdb.moviesdbmvvm.data.model.themoviedb.MovieBase;
import com.moviesdb.moviesdbmvvm.data.model.themoviedb.MovieQueryResult;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class MoviesLineViewModel extends BaseObservable implements Disposable{


    //Observable Fields
    public ObservableField<String> rowName = new ObservableField<>("");
    public List<MovieListItemViewModel> movies = new LinkedList<>();
    private PublishSubject<OnMovieItemSelected> onMovieItemSelected = PublishSubject.create();
    private PublishSubject<MoviesLineButtonClickEvent> buttonClickEvent = PublishSubject.create();

    private Throwable lastError;
    private boolean disposed;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public Observable<MoviesLineButtonClickEvent> getButtonClickEvent() {
        return buttonClickEvent;
    }

    public Observable<OnMovieItemSelected> getOnMovieItemSelected() {
        return onMovieItemSelected;
    }

    public Throwable getLastError() {
        return lastError;
    }

    private void setState(MoviesLineState state){
        //this.state = state;

    }

    public MoviesLineViewModel(String listTitle,
                                    io.reactivex.Observable<MovieQueryResult> movies,
                                    Scheduler threadPool){

        this.rowName.set(listTitle);
        Disposable disposable = movies
                .subscribeOn(threadPool)
                .map(this::convertResultToViewModels)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setMoviesList,this::onError);

        compositeDisposable.add(disposable);
        setState(MoviesLineState.DOWNLOADING);
    }

    private List<MovieListItemViewModel> convertResultToViewModels(MovieQueryResult result){
        List<MovieListItemViewModel> viewModels = new ArrayList<>();
        for (MovieBase movieBase :result.getResults())
            viewModels.add(new MovieListItemViewModel(movieBase));
        return viewModels;
    }

    private void onError(Throwable e){
        Timber.d(e);
        lastError = e;
        setState(MoviesLineState.ERROR);
    }

    private void setMoviesList(List<MovieListItemViewModel> viewModels){

        movies = viewModels;
        for(MovieListItemViewModel itemViewModel:viewModels){
            itemViewModel.onItemClicked().subscribe(onMovieItemSelected);
        }
        notifyChange();
    }

    public void onSeeMoreItems(){
        buttonClickEvent.onNext(MoviesLineButtonClickEvent.SEE_MORE_ITEMS);
    }


    @Override
    public void dispose() {
        compositeDisposable.dispose();
        disposed = true;
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }
}
