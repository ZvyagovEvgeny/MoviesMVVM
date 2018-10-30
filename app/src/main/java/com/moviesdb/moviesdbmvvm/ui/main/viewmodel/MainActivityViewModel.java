package com.moviesdb.moviesdbmvvm.ui.main.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;


import com.moviesdb.moviesdbmvvm.application.App;

import com.moviesdb.moviesdbmvvm.data.model.themoviedb.MovieQueryResult;
import com.moviesdb.moviesdbmvvm.data.remote.ApiHelper;
import com.moviesdb.moviesdbmvvm.utils.EnumUtils;
import com.moviesdb.moviesdbmvvm.network.MovieSocialNetworkApi;
import com.moviesdb.moviesdbmvvm.ui.start_activity_params.StartLoginForm;
import com.moviesdb.moviesdbmvvm.utils.Constants;

import com.moviesdb.moviesdbmvvm.ui.base.viewmodel.ViewModelBase;
import com.moviesdb.moviesdbmvvm.ui.main.cowerflow.MovieListCowerFlowViewModel;
import com.moviesdb.moviesdbmvvm.ui.main.viewmodel.movies_line.MoviesLineButtonClickEvent;
import com.moviesdb.moviesdbmvvm.ui.main.viewmodel.movies_line.MoviesLineViewModel;
import com.moviesdb.moviesdbmvvm.ui.base.viewmodel.StoredViewModel;
import com.moviesdb.moviesdbmvvm.ui.main.viewmodel.movies_line.OnMovieItemSelected;
import com.moviesdb.moviesdbmvvm.ui.start_activity_params.AnotherActivity;
import com.moviesdb.moviesdbmvvm.ui.base.viewmodel.ObservableLiveData;
import com.moviesdb.moviesdbmvvm.ui.start_activity_params.MovieDatailsActivityParams;
import com.moviesdb.moviesdbmvvm.ui.start_activity_params.SeeMoreActivityParams;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class MainActivityViewModel extends ViewModelBase implements StoredViewModel {
    private Context context;
    public ObservableLiveData<MainActivityVisability> status = new ObservableLiveData<>(new MainActivityVisability());
    public List<Object> viewModelsToShow = new ArrayList<>();
    public ObservableField<String> errorMessage = new ObservableField<>("Some error");

    private PublishSubject<AnotherActivity> anotherActivityPublishSubject = PublishSubject.create();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

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

    private boolean initialized = false;

    private ApiHelper apiHelper;

    public MainActivityViewModel(ApiHelper apiHelper) {
        setStatus(ViewModelStatus.INITIAL_DOWNLOADS_IN_PROGRESS);
        this.apiHelper = apiHelper;
        Disposable d = apiHelper.getSession().observeOn(AndroidSchedulers.mainThread()).subscribe((session -> {
            if(session!=null)
                if(session.success){
                    logined = true;
                    refreshCommands();
                }
        }));
        refreshCommands();
        compositeDisposable.add(d);

    }

    private void download() {


        App appController = App.create(context);
        MovieSocialNetworkApi api = appController.getMoviesDBComponent().getMovieSocialNetworkApi();
        Constants constants = appController.getMoviesDBComponent().getConstants();


        for(MovieSocialNetworkApi.ListType type:moviesListTypes){

            Observable<MovieQueryResult> resultObservable = getMoviesList(type);
            String listTitle = constants.getNamesOfMoviesTypesLists()
                    .get(EnumUtils.GetSerializedNameValue(type));

            Disposable viewModel;
            if(type == moviesListTypes[0]){
                viewModel = new MovieListCowerFlowViewModel(resultObservable,
                        appController.subscribeScheduler());
            }
            else{
                MoviesLineViewModel moviesLineViewModel =
                        new MoviesLineViewModel(listTitle,resultObservable,
                                appController.subscribeScheduler());

                Disposable d = moviesLineViewModel
                        .getButtonClickEvent()
                        .subscribe((e)->handleMoviesLineButtonEvents(e,type));

                d = moviesLineViewModel.getOnMovieItemSelected()
                        .subscribe(this::handleOnMovieItemSelected);

                viewModel = moviesLineViewModel;
                compositeDisposable.add(moviesLineViewModel);
            }

            viewModelsToShow.add(viewModel);

        }

        setStatus(ViewModelStatus.FIELDS_SHOWING);
    }

    private AnotherActivity handleOnMovieItemSelected(OnMovieItemSelected onMovieItemSelected){
        switch (onMovieItemSelected.itemClickedEventType){
            case POSTER:
                anotherActivityPublishSubject.onNext(new MovieDatailsActivityParams(onMovieItemSelected.viewModel.movie.get()));
            case WISH_LIST_ICON:
                break;
            default:
                break;

        }
        return null;
    }

    private void handleMoviesLineButtonEvents(MoviesLineButtonClickEvent event, MovieSocialNetworkApi.ListType type){
        switch (event){
            case SEE_MORE_ITEMS:
                anotherActivityPublishSubject.onNext(new SeeMoreActivityParams(type));
                break;
        }
    }

    private Observable<MovieQueryResult> getMoviesList(MovieSocialNetworkApi.ListType type){
        App appController = App.create(context);
        MovieSocialNetworkApi api = appController.getMoviesDBComponent().getMovieSocialNetworkApi();
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
            download();
            initialized = true;
        }

    }

    boolean logined = false;
    public CommandVM openLoginForm = new CommandVM() {

        @Override
        public void refresh() {
            isEnabled(!logined);
        }

        @Override
        public void execute() {
            anotherActivityPublishSubject.onNext(new StartLoginForm());
            refreshCommands();
        }
    };

    public CommandVM logOut = new CommandVM() {

        @Override
        public void refresh() {
            isEnabled(logined);

        }

        @Override
        public void execute() {
            logined = false;
            refreshCommands();;
        }
    };
}
