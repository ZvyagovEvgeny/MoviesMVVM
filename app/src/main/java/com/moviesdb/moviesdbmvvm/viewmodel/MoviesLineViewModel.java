package com.moviesdb.moviesdbmvvm.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieBase;
import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieQueryResult;

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

    public ObservableField<String> rowName = new ObservableField<>("");
    public List<MovieListItemViewModel> movies = new LinkedList<>();

    private PublishSubject<MoviesLineState> stateSubject = PublishSubject.create();
    private PublishSubject<OnItemClicked> onItemClicked = PublishSubject.create();
    private PublishSubject<MoviesLineViewModel> onSeeMoreItems = PublishSubject.create();
    private Throwable lastError;
    private MoviesLineState state;

    public Observable<MoviesLineViewModel> getOnSeeMoreItems() {
        return onSeeMoreItems;
    }

    public Observable<MoviesLineState> getState() {
        return stateSubject;
    }

    public Observable<OnItemClicked> getOnItemClicked() {
        return onItemClicked;
    }

    public Throwable getLastError() {
        return lastError;
    }

    private void setState(MoviesLineState state){
        this.state = state;
        stateSubject.onNext(state);
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MoviesLineViewModel(String listTitle,
                                    io.reactivex.Observable<MovieQueryResult> movies,
                                    Scheduler threadPool){

        this.rowName.set(listTitle);
        Disposable disposable = movies.subscribeOn(threadPool)
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
        for(MovieListItemViewModel viewModel:viewModels){
            viewModel.onItemClicked.observeOn(AndroidSchedulers.mainThread()).subscribe(
                    (type)->onItemClicked.onNext(new OnItemClicked(type,viewModel)));
        }

        notifyChange();
    }

    public void onSeeMoreItems(){
        onSeeMoreItems.onNext(this);
    }

    private boolean disposed;
    @Override
    public void dispose() {
        compositeDisposable.dispose();;
        disposed = true;
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    static class OnItemClicked{
        public MovieClickedType type;
        public MovieListItemViewModel item;

        public OnItemClicked(MovieClickedType type, MovieListItemViewModel item) {
            this.type = type;
            this.item = item;
        }
    }
}
