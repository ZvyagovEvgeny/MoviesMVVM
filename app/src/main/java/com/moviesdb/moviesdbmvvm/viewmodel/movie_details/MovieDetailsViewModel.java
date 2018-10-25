package com.moviesdb.moviesdbmvvm.viewmodel.movie_details;

import android.content.Context;
import android.databinding.BaseObservable;

import android.databinding.ObservableField;

import com.moviesdb.moviesdbmvvm.application.MoviesDBApplication;
import com.moviesdb.moviesdbmvvm.model.themoviedb.Cast;
import com.moviesdb.moviesdbmvvm.model.themoviedb.CreditsQueryResult;
import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieDetail;
import com.moviesdb.moviesdbmvvm.network.MovieSocialNetworkApi;
import com.moviesdb.moviesdbmvvm.viewmodel.base.CustomMutableLiveData;
import com.moviesdb.moviesdbmvvm.viewmodel.base.StoredViewModel;
import com.moviesdb.moviesdbmvvm.viewmodel.main.MainActivityVisability;
import com.moviesdb.moviesdbmvvm.viewmodel.main.ViewModelStatus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import timber.log.Timber;

public class MovieDetailsViewModel extends BaseObservable implements StoredViewModel {

    private int movieId;

    public CustomMutableLiveData<MainActivityVisability> status = new CustomMutableLiveData<>(new MainActivityVisability());
    public List<Object> viewModelsToShow = new ArrayList<>();
    public ObservableField<String> errorMessage = new ObservableField<>("Some error");

    private Context context;
    private MovieDetail movieDetail;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BehaviorSubject<MainInfoViewModel> mainInfoViewModel = BehaviorSubject.create();
    public BehaviorSubject<CastListVIewModel> castListViewModel = BehaviorSubject.create();

    public MovieDetailsViewModel(int movieId , Context context){
        this.movieId = movieId;
        this.context = context;
        status.getValue().setState(ViewModelStatus.INITIAL_DOWNLOADS_IN_PROGRESS);


        MoviesDBApplication application = MoviesDBApplication.create(context);
        MovieSocialNetworkApi api = application.getMoviesDBComponent().getMovieSocialNetworkApi();
        Observable<MovieDetail> movieDetailObservable = api.getMovieDetails(movieId, MovieSocialNetworkApi.Lang.ENG);

        Disposable d = movieDetailObservable.subscribeOn(application.subscribeScheduler())
                .map((movieDetail)->new MainInfoViewModel(movieDetail))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((data)->mainInfoViewModel.onNext(data),this::onFailure);

        compositeDisposable.add(d);

        Observable<CreditsQueryResult> credits = api.getMovieCredits(movieId);
        d =  credits.subscribeOn(application.subscribeScheduler())
                .map((data)->onRecieveCredits(data))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((data)->{
                            Timber.d("Recieved cast: "+data.objects.size());
                            castListViewModel.onNext(data);
                       }
                ,this::onFailure);
        compositeDisposable.add(d);
    }

    private CastListVIewModel onRecieveCredits(CreditsQueryResult result){
        List<CastItemViewModel> casts  = new ArrayList<>();
        for(Cast cast:result.getCast()){
            CastItemViewModel castItemViewModel = new CastItemViewModel(cast);
            casts.add(castItemViewModel);
        }

        return new CastListVIewModel(casts,"Cast", "SEE MORE");
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
