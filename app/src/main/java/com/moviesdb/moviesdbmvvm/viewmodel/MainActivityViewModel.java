package com.moviesdb.moviesdbmvvm.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;


import com.moviesdb.moviesdbmvvm.application.MoviesDBApplication;
import com.moviesdb.moviesdbmvvm.dagger.module.qualifier.SocialNetworkApiQualifier;
import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieQueryResult;
import com.moviesdb.moviesdbmvvm.network.EnumUtils;
import com.moviesdb.moviesdbmvvm.network.MovieSocialNetworkApi;
import com.moviesdb.moviesdbmvvm.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class MainActivityViewModel extends BaseObservable implements StoredViewModel {
    private Context context;
    public CustomMutableLiveData<MainActivityVisability> status = new CustomMutableLiveData<>(new MainActivityVisability());
    public List<Object> viewModelsToShow = new ArrayList<>();
    public ObservableField<String> errorMessage = new ObservableField<>("Some error");
    private MoviesDBApplication appController;
    private PublishSubject<AnotherActivity> anotherActivityPublishSubject = PublishSubject.create();

    public Observable<AnotherActivity> getAnotherActivityObserver() {
        return anotherActivityPublishSubject;
    }

    private MovieSocialNetworkApi.ListType[] moviesListTypes =
            new MovieSocialNetworkApi.ListType[]{
                    MovieSocialNetworkApi.ListType.NOW_PLAYING,
                    MovieSocialNetworkApi.ListType.POP,
                    MovieSocialNetworkApi.ListType.TOP_RATED,
                    MovieSocialNetworkApi.ListType.UPCOMING
            };

    private MovieSocialNetworkApi api;

    private void init() {
        appController = MoviesDBApplication.create(context);
        api = appController.getMoviesDBComponent().getMovieSocialNetworkApi();
    }

    private boolean initialized = false;


    public MainActivityViewModel() {
        setStatus(ViewModelStatus.INITIAL_DOWNLOADS_IN_PROGRESS);
    }

    private void download() {

        for(MovieSocialNetworkApi.ListType type:moviesListTypes){
            Constants constants = appController.getMoviesDBComponent().getConstants();
            String listTitle = constants.getNamesOfMoviesTypesLists().get(EnumUtils.GetSerializedNameValue(type));

            Observable<MovieQueryResult> resultObservable = getMoviesList(type);

            Disposable viewModel;
            if(type == moviesListTypes[0]){
                viewModel = new MovieListCowerFlowViewModel(resultObservable,
                        appController.subscribeScheduler());
            }
            else{
                MoviesLineViewModel moviesLineViewModel = new MoviesLineViewModel(listTitle,resultObservable,
                        appController.subscribeScheduler());
                Disposable disposable = moviesLineViewModel.getState().subscribe((data)->{
                    Timber.d(data.toString());
                });
                disposable = moviesLineViewModel.getOnItemClicked().subscribe((item)->{
                    switch (item.type){
                        case POSTER:

                    }
                });
                disposable = moviesLineViewModel.getOnSeeMoreItems().subscribe((o)->{
                    anotherActivityPublishSubject.onNext(new SeeMoreActivity(type));
                });
                viewModel = moviesLineViewModel;
            }

            viewModelsToShow.add(viewModel);
        }

        setStatus(ViewModelStatus.FIELDS_SHOWING);
    }

    private Observable<MovieQueryResult> getMoviesList(MovieSocialNetworkApi.ListType type){
      return api.getMovies(type,
                        1,
                        MovieSocialNetworkApi.Lang.ENG,
                        MovieSocialNetworkApi.Region.USA);
    }

    private void onFailure(Throwable o){
        Timber.d(o);
        errorMessage.set(o.getMessage());
        setStatus(ViewModelStatus.ERROR);
    }

    @Override
    public void onActivityDetached() {

    }

    @Override
    public void onActivityDestroyed() {
        for(Object o : viewModelsToShow){
            if(o instanceof Disposable )
                ((Disposable) o).dispose();
        }
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
