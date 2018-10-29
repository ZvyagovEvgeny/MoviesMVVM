package com.moviesdb.moviesdbmvvm.ui.login;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.moviesdb.moviesdbmvvm.application.App;
import com.moviesdb.moviesdbmvvm.model.themoviedb.AuthenticationResult;
import com.moviesdb.moviesdbmvvm.model.themoviedb.Session;
import com.moviesdb.moviesdbmvvm.model.themoviedb.UserLoginAndPassword;
import com.moviesdb.moviesdbmvvm.model.themoviedb.UserLoginAndPasswordBuilder;
import com.moviesdb.moviesdbmvvm.network.MovieSocialNetworkApi;
import com.moviesdb.moviesdbmvvm.ui.base.viewmodel.StoredViewModel;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import timber.log.Timber;

public class LoginViewModel extends ViewModel implements StoredViewModel {

    public ObservableField<String> userName = new ObservableField<>("");
    public ObservableField<String> password = new ObservableField<>("");
    public ObservableField<String> message = new ObservableField<>("");
    public ObservableBoolean progressBar = new ObservableBoolean(false);

    MovieSocialNetworkApi api;
    private Scheduler threadPool;


    public LoginViewModel( MovieSocialNetworkApi api,Scheduler threadPool ){
        this.api = api;
        this.threadPool = threadPool;
    }

    private AuthenticationResult authentication;
    private Session session;


    public void sendLoginAndPass( ){
        if(userName.get().isEmpty() || password.get().isEmpty()){
            return;
        }
        progressBar.set(true);

        Observable<AuthenticationResult> result = api.createRequestToken();
        Disposable d = result.subscribeOn(threadPool).observeOn(threadPool).subscribe((token)->{
            String t =  token.getRequestToken();
            UserLoginAndPassword userLoginAndPassword = new UserLoginAndPasswordBuilder()
                    .setUserName(userName.get())
                    .setPassword(password.get())
                    .setToken(t).createUserLoginAndPassword();

            Observable<AuthenticationResult> authorisationResult = api.validateWithLogin(userLoginAndPassword);
            authorisationResult.subscribeOn(threadPool).observeOn(threadPool)
                    .subscribe((authenticationResult) -> {
                        authentication = authenticationResult;
                        if(authenticationResult.getSuccess()){
                            Observable<Session> sessionObservable = api.createSession(authenticationResult);
                            sessionObservable.observeOn(threadPool).observeOn(AndroidSchedulers.mainThread())
                                    .subscribe((session)->{
                                        if(session.success){
                                            this.session = session;
                                            Timber.d("Session recieved"+session.getSessionId());
                                        }
                                        else{
                                            Timber.d("Session error"+session.getSessionId());
                                        }
                                        progressBar.set(false);
                                        message.set(session.getSessionId());
                                    },this::onError);

                        }
                        else{
                            Timber.d("Some error in Authorisation");
                        }

                    },this::onError);


        },this::onError);

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

    }

    @Override
    public void onAttached(Context context) {

    }
}
