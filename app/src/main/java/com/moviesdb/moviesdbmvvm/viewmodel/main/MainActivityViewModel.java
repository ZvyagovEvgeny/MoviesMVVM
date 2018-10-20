package com.moviesdb.moviesdbmvvm.viewmodel.main;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;


import com.moviesdb.moviesdbmvvm.application.MoviesDBApplication;
import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieQueryResult;
import com.moviesdb.moviesdbmvvm.network.EnumUtils;
import com.moviesdb.moviesdbmvvm.network.MovieSocialNetworkApi;
import com.moviesdb.moviesdbmvvm.utils.Constants;
import com.moviesdb.moviesdbmvvm.viewmodel.main.movies_line.Event;
import com.moviesdb.moviesdbmvvm.viewmodel.main.movies_line.ItemClickedEventType;
import com.moviesdb.moviesdbmvvm.viewmodel.main.movies_line.MovieListCowerFlowViewModel;
import com.moviesdb.moviesdbmvvm.viewmodel.main.movies_line.MoviesLineViewModel;
import com.moviesdb.moviesdbmvvm.viewmodel.base.StoredViewModel;
import com.moviesdb.moviesdbmvvm.viewmodel.main.movies_line.OnMovieItemSelected;
import com.moviesdb.moviesdbmvvm.viewmodel.main.movies_line.OnSeeMoreItemsEvent;
import com.moviesdb.moviesdbmvvm.viewmodel.start_activity_params.AnotherActivity;
import com.moviesdb.moviesdbmvvm.viewmodel.base.CustomMutableLiveData;
import com.moviesdb.moviesdbmvvm.viewmodel.start_activity_params.MovieDatailsActivity;
import com.moviesdb.moviesdbmvvm.viewmodel.start_activity_params.SeeMoreActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class MainActivityViewModel extends BaseObservable implements StoredViewModel {
    private Context context;
    public CustomMutableLiveData<MainActivityVisability> status = new CustomMutableLiveData<>(new MainActivityVisability());
    public List<Object> viewModelsToShow = new ArrayList<>();
    public ObservableField<String> errorMessage = new ObservableField<>("Some error");

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

    private boolean initialized = false;

    public MainActivityViewModel() {
        setStatus(ViewModelStatus.INITIAL_DOWNLOADS_IN_PROGRESS);
    }

    private void download() {


        MoviesDBApplication appController = MoviesDBApplication.create(context);
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
                        .getEventsSubject()
                        .map((event)->handleEvent(event,type))
                        .subscribe((activity)->{if(activity!=null) anotherActivityPublishSubject.onNext(activity);});

                viewModel = moviesLineViewModel;
            }

            viewModelsToShow.add(viewModel);
        }

        setStatus(ViewModelStatus.FIELDS_SHOWING);
    }



    private AnotherActivity handleEvent(Event event, MovieSocialNetworkApi.ListType type){

        if(event instanceof OnMovieItemSelected){
            return handleOnMovieItemSelected((OnMovieItemSelected)event);
        }

        if(event instanceof OnSeeMoreItemsEvent){
            return handleSeeMoreItems(type);
        }
        return null;
    }

    static private AnotherActivity handleOnMovieItemSelected(OnMovieItemSelected onMovieItemSelected){
        switch (onMovieItemSelected.getEventType()){
            case POSTER:
                return new MovieDatailsActivity(onMovieItemSelected.getMovie().movie.get());
                ///anotherActivityPublishSubject.onNext());

            case WISH_LIST_ICON:
    //            addToWishList(onMovieItemSelected.getMovie().movie.get().getId());
                break;
            default:
                break;

        }
        return null;
    }

    private static AnotherActivity handleSeeMoreItems(MovieSocialNetworkApi.ListType type){
        return new SeeMoreActivity(type);
    }

    //private void addToWishList(int id){}

    private Observable<MovieQueryResult> getMoviesList(MovieSocialNetworkApi.ListType type){
        MoviesDBApplication appController = MoviesDBApplication.create(context);
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
            download();
            initialized = true;
        }

    }
}
