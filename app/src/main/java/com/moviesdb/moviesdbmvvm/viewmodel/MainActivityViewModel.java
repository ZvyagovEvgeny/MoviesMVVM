package com.moviesdb.moviesdbmvvm.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.util.ArrayMap;
import android.util.Log;


import com.moviesdb.moviesdbmvvm.application.MoviesDBApplication;
import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieQueryResult;
import com.moviesdb.moviesdbmvvm.model.themoviedb.MoviesListType;
import com.moviesdb.moviesdbmvvm.network.FuncToRecieveMoviesList;
import com.moviesdb.moviesdbmvvm.network.MovieSocialNetworkApi;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import javax.security.auth.Destroyable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.observable.ObservableFromCallable;
import io.reactivex.internal.schedulers.RxThreadFactory;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subscribers.DisposableSubscriber;
import timber.log.Timber;

public class MainActivityViewModel extends BaseObservable implements StoredViewModel {
    private Context context;

    public CustomMutableLiveData<MainActivityVisability> status = new CustomMutableLiveData<>();


    public List<Object> viewModelsToShow = new ArrayList<>();
    public ObservableField<String> errorMessage = new ObservableField<>("Some error");


    private Map<MoviesListType, Object> receivedData= new HashMap<>();
    private static List<MoviesListType> priorityToPrint = new ArrayList<>();
    private Map<MoviesListType, FuncToRecieveMoviesList> functionToReceiveMoviesLists;
    private MoviesDBApplication appController;

    private MovieSocialNetworkApi api;

    private void init() {
        appController = MoviesDBApplication.create(context);
        api = appController.getMoviesDBComponent().getMovieSocialNetworkApi();
        initFunctions(api);
    }

    private boolean initialized = false;

    static {
        priorityToPrint.add(MoviesListType.now_playing);
        priorityToPrint.add(MoviesListType.popular);
        priorityToPrint.add(MoviesListType.top_rated);
        priorityToPrint.add(MoviesListType.upcoming);
    }

    Handler handler;

    public MainActivityViewModel() {

        receivedData = new HashMap<>();
        functionToReceiveMoviesLists = new HashMap<>();
        status.setValue(new MainActivityVisability());
        status.getValue().setState(MainViewStatus.INITIAL_DOWNLOADS_IN_PROGRESS);

        MyPublisher myPublisher = new MyPublisher();
        Observable<MainViewStatus> observable = Observable.fromPublisher(myPublisher);
        Disposable disposable =  observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe((status)->{
                    this.status.getValue().setState(status);
                    Log.d("Hello", "On next");
                },(error)->{});
    }
    class MyPublisher implements Publisher<MainViewStatus>{

        public MyPublisher() {
            start();
        }

        /**
         * Constructs a PublishProcessor.
         *
         * @since 2.0
         */


        int i = 0;
        public void start(){
           new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    MainViewStatus mainViewStatus = MainViewStatus.values()[i];
                    Log.d("Hello","timer");
                    if(s!=null)s.onNext(mainViewStatus);
                    i++;
                    if(i>=MainViewStatus.values().length)i = 0;
                }
            },0,5000);
        }

        Subscriber s;

        @Override
        public void subscribe(Subscriber<? super MainViewStatus> s) {
            this.s = s;
            s.onSubscribe(new Subscription() {
                @Override
                public void request(long n) {

                }

                @Override
                public void cancel() {

                }
            });
        }


    }

    private void updateMainActivityContent() {
        if(status.getValue().getMainViewStatus()==MainViewStatus.ERROR){
            return;
        }

        status.getValue().setState(MainViewStatus.FIELDS_SHOWING);
        viewModelsToShow.clear();
        for (MoviesListType type : priorityToPrint) {
            viewModelsToShow.add(receivedData.get(type));
        }
        notifyChange();
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private void initFunctions(MovieSocialNetworkApi api) {
        functionToReceiveMoviesLists.clear();
        functionToReceiveMoviesLists.put(MoviesListType.now_playing, api::getNowPlayingMovies);
        functionToReceiveMoviesLists.put(MoviesListType.popular, api::getPopularMovies);
        functionToReceiveMoviesLists.put(MoviesListType.top_rated, api::getTopRatedMovies);
        functionToReceiveMoviesLists.put(MoviesListType.upcoming, api::getUpcomingMovies);
    }

    private void download() {
        for (Map.Entry<MoviesListType, FuncToRecieveMoviesList> typeAndFunc :
                functionToReceiveMoviesLists.entrySet())
            download(typeAndFunc.getKey(), typeAndFunc.getValue());
    }


    void download(MoviesListType type,FuncToRecieveMoviesList func){

        Observable<MovieQueryResult> result = func.getMovies(MovieSocialNetworkApi.LANG_ENG,
                1,MovieSocialNetworkApi.REGION_USA);

        Disposable disposable = result
            .subscribeOn(appController.subscribeScheduler())
            .map((movieQueryResult)-> {
                return getViewModelOfRecievedData(type, movieQueryResult);
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((viewModel)->{
                receivedData.put(type,viewModel);
                updateMainActivityContent();
            },this::onFailure);

        compositeDisposable.add(disposable);
    }

    private Object getViewModelOfRecievedData(MoviesListType type, MovieQueryResult movieQueryResult){
        if(type == MoviesListType.now_playing){
            return new MovieListCowerFlowViewModel(movieQueryResult.getResults());
        }
        else{
            return new MoviesLineViewModel(type,movieQueryResult.getResults());
        }
    }

    private void onFailure(Throwable o){
        Timber.d(o);
        errorMessage.set(o.getMessage());
        status.getValue().setState(MainViewStatus.ERROR);
    }

    public void onSeeMoreItemSelecred(Object object){

    }

    @Override
    public void onActivityDetached() {

    }

    @Override
    public void onActivityDestroyed() {

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
