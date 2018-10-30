package com.moviesdb.moviesdbmvvm.data.remote;

import com.moviesdb.moviesdbmvvm.data.model.themoviedb.AuthenticationResult;
import com.moviesdb.moviesdbmvvm.data.model.themoviedb.CreditsQueryResult;
import com.moviesdb.moviesdbmvvm.data.model.themoviedb.MovieDetail;
import com.moviesdb.moviesdbmvvm.data.model.themoviedb.MovieQueryResult;
import com.moviesdb.moviesdbmvvm.data.model.themoviedb.MoviesListType;
import com.moviesdb.moviesdbmvvm.data.model.themoviedb.Session;
import com.moviesdb.moviesdbmvvm.data.model.themoviedb.UserLoginAndPassword;
import com.moviesdb.moviesdbmvvm.data.model.themoviedb.UserLoginAndPasswordBuilder;
import com.moviesdb.moviesdbmvvm.network.MovieSocialNetworkApi;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

@Singleton
public class AppApiHelper implements ApiHelper{

    public AppApiHelper(MovieSocialNetworkApi movieSocialNetworkApi){
        this.movieSocialNetworkApi = movieSocialNetworkApi;
    }

    MovieSocialNetworkApi movieSocialNetworkApi;


    MovieSocialNetworkApi.Lang lang = MovieSocialNetworkApi.Lang.ENG;
    MovieSocialNetworkApi.Region region = MovieSocialNetworkApi.Region.USA;

    private Session session;

    public void registerSession(Session session){
        this.session = session;
        sessionSubject.onNext(session);
    }


    @Override
    public Observable<MovieQueryResult> getMoviesList(MovieSocialNetworkApi.ListType moviesListType, int page) {
        return movieSocialNetworkApi.getMovies(moviesListType,page, MovieSocialNetworkApi.Lang.ENG, MovieSocialNetworkApi.Region.USA);
    }

    @Override
    public Observable<MovieDetail> getMovieDetails(int id) {
        return movieSocialNetworkApi.getMovieDetails(id,lang);
    }

    @Override
    public Observable<CreditsQueryResult> getMovieCredits(int id) {
        return movieSocialNetworkApi.getMovieCredits(id);
    }


    private Observable<AuthenticationResult> getToken(){
        return movieSocialNetworkApi.createRequestToken().subscribeOn(Schedulers.io());
    }

    private Observable<AuthenticationResult> authorsizeWithLogin(UserLoginAndPassword userLoginAndPassword){
        return movieSocialNetworkApi.validateWithLogin(userLoginAndPassword).subscribeOn(Schedulers.io());
    }

    private Observable<Session> getSession(AuthenticationResult authenticationResult){
        return movieSocialNetworkApi.createSession(authenticationResult).subscribeOn(Schedulers.io());
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public PublishSubject<Session> sessionSubject = PublishSubject.create();


    public Observable<Session> authentication(String login,String password) {
        compositeDisposable.dispose();;
        compositeDisposable = new CompositeDisposable();

        PublishSubject<Session> sessionPublishSubject = PublishSubject.create();


        Disposable d = getToken()
                .map((result)->new UserLoginAndPasswordBuilder()
                        .setUserName(login)
                        .setPassword(password)
                        .setToken(result.getRequestToken())
                        .createUserLoginAndPassword())
                .subscribe((loginPassToken)->{
                     authorsizeWithLogin(loginPassToken).subscribe((token)->{
                            getSession(token).subscribe(sessionPublishSubject::onNext,
                                    sessionPublishSubject::onError);
                        },sessionPublishSubject::onError);

          },sessionPublishSubject::onError);

       compositeDisposable.add(d);

       Disposable disposable = sessionPublishSubject.observeOn(AndroidSchedulers.mainThread())
               .subscribe((session)->{
                   registerSession(session);
               },(e)->Timber.d(e));

       compositeDisposable.add(disposable);


       return sessionPublishSubject;

    }

    @Override
    public Observable<Session> getSession() {
        return sessionSubject;
    }
}

