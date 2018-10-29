package com.moviesdb.moviesdbmvvm.ui.main.cowerflow;

import android.databinding.BaseObservable;

import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieBase;
import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieQueryResult;
import com.moviesdb.moviesdbmvvm.ui.main.viewmodel.movies_line.MovieListItemViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class MovieListCowerFlowViewModel extends BaseObservable implements Disposable{

    public List<MovieListItemViewModel> moviesViewModels = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private boolean disposed = false;

    public MovieListCowerFlowViewModel(
                               io.reactivex.Observable<MovieQueryResult> movies,
                               Scheduler threadPool){

        Disposable disposable = movies.subscribeOn(threadPool)
                .map(this::convertResultToViewModels)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setMoviesList,this::onError);

        compositeDisposable.add(disposable);
    }

    private List<MovieListItemViewModel> convertResultToViewModels(MovieQueryResult result){
        List<MovieListItemViewModel> viewModels = new ArrayList<>();
        for (MovieBase movieBase :result.getResults())
            viewModels.add(new MovieListItemViewModel(movieBase));
        return viewModels;
    }

    private void onError(Throwable e){
        Timber.d(e);
    }

    private void setMoviesList(List<MovieListItemViewModel> viewModels){
        moviesViewModels = viewModels;
        notifyChange();
    }

    @Override
    public void dispose() {
        compositeDisposable.dispose();
        compositeDisposable = new CompositeDisposable();
        disposed = true;
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }
}
