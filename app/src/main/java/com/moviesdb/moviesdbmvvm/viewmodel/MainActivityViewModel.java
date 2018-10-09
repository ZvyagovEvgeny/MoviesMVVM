package com.moviesdb.moviesdbmvvm.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;


import com.moviesdb.moviesdbmvvm.application.MoviesDBApplication;
import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieBase;
import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieQueryResult;
import com.moviesdb.moviesdbmvvm.model.themoviedb.MoviesListQueryTypes;
import com.moviesdb.moviesdbmvvm.network.MovieSocialNetworkApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

import static com.moviesdb.moviesdbmvvm.model.themoviedb.MoviesListQueryTypes.now_playing;

public class MainActivityViewModel extends BaseObservable implements StoredViewModel {
    private Context context;

    //public List<ViewModel> data = new ArrayList<>();;

    public List<Object> moviesLineViewModels = new ArrayList<>();

    public ObservableBoolean initialLoading = new ObservableBoolean(false);

    static  private List<MoviesListQueryTypes> movesTypesOrder = new ArrayList<>();
    private Map<MoviesListQueryTypes, Object> receivedData = new HashMap<>();
    private boolean initialized = false;

    static {
        movesTypesOrder.add(now_playing);
        movesTypesOrder.add(MoviesListQueryTypes.popular);
        movesTypesOrder.add(MoviesListQueryTypes.top_rated);
        movesTypesOrder.add(MoviesListQueryTypes.upcoming);
    }

    public  MainActivityViewModel (){
       initialLoading.set(true);

    }

    private void updateMainActivityContent(){
        moviesLineViewModels.clear();
        for(MoviesListQueryTypes type: movesTypesOrder){
             Object list = receivedData.get(type);
             if(list==null)continue;
            moviesLineViewModels.add(list);
        }
        notifyChange();
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private void download(){
        MoviesDBApplication appController = MoviesDBApplication.create(context);
        MovieSocialNetworkApi api = appController.getMoviesDBComponent().getMovieSocialNetworkApi();

        for(MoviesListQueryTypes type: movesTypesOrder){
            io.reactivex.Observable<MovieQueryResult> resultObservable = null;

            switch (type){
                case popular:
                    resultObservable = api.getPopularMovies(
                            api.LANG_ENG,
                            1,
                            api.REGION_USA);

                    break;
                case upcoming:
                    resultObservable = api.getUpcomingMovies(
                            api.LANG_ENG,
                            1,
                            api.REGION_USA);
                    break;
                case top_rated:
                    resultObservable = api.getTopRatedMovies(
                            api.LANG_ENG,
                            1,
                            api.REGION_USA);
                    break;
                case now_playing:
                    resultObservable = api.getNowPlayingMovies(
                            api.LANG_ENG,
                            1,
                            api.REGION_USA);
                    break;
                    default:
                        resultObservable = null;
                        break;
            }

            if(resultObservable==null) continue;

            DownloaderListener downloaderListener = new DownloaderListener(type,this::onComplete);

            Disposable disposable =  resultObservable
                    .subscribeOn(appController.subscribeScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(downloaderListener::onSuccess,downloaderListener::inFailure);

            compositeDisposable.add(disposable);
        }
    }

    private void onComplete(DownloaderListener downloaderListener){
        MoviesListQueryTypes types = downloaderListener.getMoviesListQueryTypes();
        if(downloaderListener.getThrowable()!=null){
            Timber.d(downloaderListener.getThrowable());
            return;
        }
        List<MovieBase> movieBases =  downloaderListener.getResult().getResults();
        if(downloaderListener.getMoviesListQueryTypes() == now_playing){
            MovieListCowerFlowViewModel movieListCowerFlowViewModel =
                    new MovieListCowerFlowViewModel(movieBases);
            receivedData.put(types,movieListCowerFlowViewModel);
        }
        else{
            receivedData.put(types,new MoviesLineViewModel(downloaderListener.getMoviesListQueryTypes(),movieBases));
        }
        updateMainActivityContent();
    }

    public static class DownloaderListener{
        private MoviesListQueryTypes moviesListQueryTypes;
        private Throwable throwable;
        private MovieQueryResult result;

        public MoviesListQueryTypes getMoviesListQueryTypes() {
            return moviesListQueryTypes;
        }

        public DownloaderListener(MoviesListQueryTypes moviesListQueryTypes,
                                  CallbackListener callbackListener) {
            this.moviesListQueryTypes = moviesListQueryTypes;
            this.callbackListener = callbackListener;
        }


        public interface CallbackListener{
            void execute(DownloaderListener downloaderListener);
        }
        private  CallbackListener callbackListener;

        void onSuccess(MovieQueryResult movieQueryResult){
            this.result = movieQueryResult;
            callbackListener.execute(this);
        }
        void inFailure(Throwable throwable){
            this.throwable = throwable;
            callbackListener.execute(this);
        }

        public Throwable getThrowable() {
            return throwable;
        }

        public MovieQueryResult getResult() {
            return result;
        }
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
            download();
            initialized = true;
        }

    }
}
