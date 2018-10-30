package com.moviesdb.moviesdbmvvm.ui.login;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.moviesdb.moviesdbmvvm.application.App;
import com.moviesdb.moviesdbmvvm.data.model.themoviedb.Session;
import com.moviesdb.moviesdbmvvm.data.remote.ApiHelper;
import com.moviesdb.moviesdbmvvm.network.MovieSocialNetworkApi;
import com.moviesdb.moviesdbmvvm.ui.base.viewmodel.StoredViewModel;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class LoginViewModel extends ViewModel implements StoredViewModel {

    public ObservableField<String> userName = new ObservableField<>("");
    public ObservableField<String> password = new ObservableField<>("");
    public ObservableField<String> message = new ObservableField<>("");
    public ObservableBoolean progressBar = new ObservableBoolean(false);

    private PublishSubject<LoginActivtyState> loginActivtyState = PublishSubject.create();

    public Observable<LoginActivtyState> getLoginActivtyState() {
        return loginActivtyState;
    }

    private ApiHelper apiHelper;
    private Session session;
    private Scheduler threadPool;


    public LoginViewModel(ApiHelper apiHelper,Scheduler threadPool ){
        this.apiHelper = apiHelper;
        this.threadPool = threadPool;
    }

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public void sendLoginAndPass( ){
        if(userName.get().isEmpty() || password.get().isEmpty()){
            return;
        }
        progressBar.set(true);

        Disposable d = apiHelper.authentication(userName.get(),password.get()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(session ->{this.session = session;
                    message.set(session.getSessionId());
                    progressBar.set(false);
                    loginActivtyState.onNext(LoginActivtyState.FINISH);
                }, this::onError);
        compositeDisposable.add(d);

    }

    private void onError(Throwable e){
        if(e instanceof com.jakewharton.retrofit2.adapter.rxjava2.HttpException){
            com.jakewharton.retrofit2.adapter.rxjava2.HttpException httpException = ( com.jakewharton.retrofit2.adapter.rxjava2.HttpException) e;
            int code = httpException.code();
            message.set(httpException.message());
        }
        else {
            Timber.d(e);
        }
        progressBar.set(false);
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

    public enum LoginActivtyState{
        FINISH
    }
}
