package com.moviesdb.moviesdbmvvm.ui.login;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;

import com.moviesdb.moviesdbmvvm.R;
import com.moviesdb.moviesdbmvvm.application.App;
import com.moviesdb.moviesdbmvvm.network.MovieSocialNetworkApi;
import com.moviesdb.moviesdbmvvm.ui.base.activity.BaseActivity;
import com.moviesdb.moviesdbmvvm.ui.base.viewmodel.ViewModelFactory;
import com.moviesdb.moviesdbmvvm.databinding.ActivityLoginBinding;

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
        MovieSocialNetworkApi api = App.create(this).getMoviesDBComponent().getMovieSocialNetworkApi();

        return ()->new LoginViewModel(api,App.create(this).subscribeScheduler());
    }

    @Override
    protected void onViewModelCreatedOrRestored(@NonNull LoginViewModel viewModel) {
        this.loginViewModel = viewModel;
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        activityLoginBinding.setViewModel(viewModel);
    }


}
