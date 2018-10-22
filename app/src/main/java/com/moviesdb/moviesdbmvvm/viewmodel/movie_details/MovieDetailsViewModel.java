package com.moviesdb.moviesdbmvvm.viewmodel.movie_details;

import android.content.Context;
import android.databinding.BaseObservable;

import android.databinding.ObservableField;

import com.moviesdb.moviesdbmvvm.Movie;
import com.moviesdb.moviesdbmvvm.application.MoviesDBApplication;
import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieDetail;
import com.moviesdb.moviesdbmvvm.network.MovieSocialNetworkApi;
import com.moviesdb.moviesdbmvvm.viewmodel.base.CustomMutableLiveData;
import com.moviesdb.moviesdbmvvm.viewmodel.base.StoredViewModel;
import com.moviesdb.moviesdbmvvm.viewmodel.main.MainActivityVisability;
import com.moviesdb.moviesdbmvvm.viewmodel.main.ViewModelStatus;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class MovieDetailsViewModel extends BaseObservable implements StoredViewModel {

    private int movieId;

    public CustomMutableLiveData<MainActivityVisability> status = new CustomMutableLiveData<>(new MainActivityVisability());
    public List<Object> viewModelsToShow = new ArrayList<>();
    public ObservableField<String> errorMessage = new ObservableField<>("Some error");


    private Context context;
    private MovieDetail movieDetail;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MovieDetailsViewModel(int movieId , Context context){
        this.movieId = movieId;
        this.context = context;
        status.getValue().setState(ViewModelStatus.INITIAL_DOWNLOADS_IN_PROGRESS);

        MoviesDBApplication application = MoviesDBApplication.create(context);
        Observable<MovieDetail> movieDetailObservable = application.getMoviesDBComponent().getMovieSocialNetworkApi().getMovieDetails(movieId, MovieSocialNetworkApi.Lang.ENG);

        Disposable d = movieDetailObservable.subscribeOn(application.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRecieveMovieDetails,this::onFailure);
        compositeDisposable.add(d);
    }

    private void onRecieveMovieDetails(MovieDetail movieDetail){
        this.movieDetail = movieDetail;
        MainInfoViewModel mainInfoViewModel = new MainInfoViewModel(movieDetail);
        viewModelsToShow.add(mainInfoViewModel);
        status.getValue().setState(ViewModelStatus.FIELDS_SHOWING);
        notifyChange();;

    }

    private void onFailure(Throwable t){
        Timber.d(t);
        errorMessage.set(t.getMessage());
        status.getValue().setState(ViewModelStatus.ERROR);
    }


    @Override
    public void onActivityDetached() {

    }

    @Override
    public void onActivityDestroyed() {
        compositeDisposable.dispose();
    }

    @Override
    public void onAttached(Context context) {

    }
}
