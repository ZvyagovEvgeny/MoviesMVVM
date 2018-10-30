package com.moviesdb.moviesdbmvvm.ui.login;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;

import com.moviesdb.moviesdbmvvm.R;
import com.moviesdb.moviesdbmvvm.application.App;
import com.moviesdb.moviesdbmvvm.data.remote.ApiHelper;
import com.moviesdb.moviesdbmvvm.network.MovieSocialNetworkApi;
import com.moviesdb.moviesdbmvvm.ui.base.activity.BaseActivity;
import com.moviesdb.moviesdbmvvm.ui.base.viewmodel.ViewModelFactory;
import com.moviesdb.moviesdbmvvm.databinding.ActivityLoginBinding;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class LoginActivity extends BaseActivity<LoginViewModel> {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding activityLoginBinding;
    @Override
    protected void initDagger() {
        super.initDagger();
    }

    @NonNull
    @Override
    protected ViewModelFactory<LoginViewModel> getViewModelFactory() {
        ApiHelper apiHelper = App.create(this).getMoviesDBComponent().apiHelper();

        return ()->new LoginViewModel(apiHelper,App.create(this).subscribeScheduler());
    }

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onViewModelCreatedOrRestored(@NonNull LoginViewModel viewModel) {
        this.loginViewModel = viewModel;
        Disposable d = loginViewModel.getLoginActivtyState().subscribe((state)->{
            switch (state){
                case FINISH:
                    finish();
            }
        });
        compositeDisposable.add(d);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        activityLoginBinding.setViewModel(viewModel);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();;
        super.onDestroy();

    }
}
