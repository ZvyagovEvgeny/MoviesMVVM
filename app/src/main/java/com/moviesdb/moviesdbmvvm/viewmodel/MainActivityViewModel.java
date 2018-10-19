package com.moviesdb.moviesdbmvvm.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;


import com.moviesdb.moviesdbmvvm.application.MoviesDBApplication;
import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieQueryResult;
import com.moviesdb.moviesdbmvvm.network.MovieSocialNetworkApi;
import com.moviesdb.moviesdbmvvm.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class MainActivityViewModel extends BaseObservable implements StoredViewModel {
    private Context context;
    public CustomMutableLiveData<MainActivityVisability> status = new CustomMutableLiveData<>();
    public List<Object> viewModelsToShow = new ArrayList<>();
    public ObservableField<String> errorMessage = new ObservableField<>("Some error");
    private MoviesDBApplication appController;


    private MovieSocialNetworkApi api;

    private void init() {
        appController = MoviesDBApplication.create(context);
        api = appController.getMoviesDBComponent().getMovieSocialNetworkApi();
    }

    private boolean initialized = false;


    public MainActivityViewModel() {
        status.setValue(new MainActivityVisability());
        setStatus(ViewModelStatus.INITIAL_DOWNLOADS_IN_PROGRESS);
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private void download() {
        MovieSocialNetworkApi api = appController.getMoviesDBComponent().getMovieSocialNetworkApi();

        MovieSocialNetworkApi.ListType[] moviesListTypes =
                new MovieSocialNetworkApi.ListType[]{
                MovieSocialNetworkApi.ListType.POP,
                MovieSocialNetworkApi.ListType.TOP_RATED,
                MovieSocialNetworkApi.ListType.UPCOMING
        };

        for(MovieSocialNetworkApi.ListType type:moviesListTypes){
            Constants constants = appController.getMoviesDBComponent().getConstants();
            String listTitle = constants.getNamesOfMoviesTypesLists().get(type.toString());

            Observable<MovieQueryResult> resultObservable =
                    api.getMovies(type,
                            0,
                            MovieSocialNetworkApi.Lang.ENG,
                            MovieSocialNetworkApi.Region.USA);
            viewModelsToShow.add(new MoviesLineViewModel(listTitle,
                    resultObservable,appController.subscribeScheduler()));
        }
        setStatus(ViewModelStatus.FIELDS_SHOWING);
    }


    private void onFailure(Throwable o){
        Timber.d(o);
        errorMessage.set(o.getMessage());
        setStatus(ViewModelStatus.ERROR);
    }

    public void onSeeMoreItemSelecred(Object object){

    }

    @Override
    public void onActivityDetached() {

    }

    @Override
    public void onActivityDestroyed() {

    }

    private void setStatus(ViewModelStatus status){
        if(this.status!=null && this.status.getValue()!=null){
            this.status.getValue().setState(status);
        }
    }

    @Override
    public void onAttached(Context context) {
        this.context = context.getApplicationContext();
        if(!initialized){
            init();
            download();
            initialized = true;
        }

    }
}
