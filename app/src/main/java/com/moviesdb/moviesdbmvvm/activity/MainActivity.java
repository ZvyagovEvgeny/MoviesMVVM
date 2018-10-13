package com.moviesdb.moviesdbmvvm.activity;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.moviesdb.moviesdbmvvm.R;

import com.moviesdb.moviesdbmvvm.activity.adapter.MainActivityRecyclerViewAdapter;


import com.moviesdb.moviesdbmvvm.activity.adapter.MoviesLineAdapter;
import com.moviesdb.moviesdbmvvm.application.MoviesDBApplication;
import com.moviesdb.moviesdbmvvm.dagger.component.DaggerMainActivityComponent;
import com.moviesdb.moviesdbmvvm.dagger.component.MainActivityComponent;
import com.moviesdb.moviesdbmvvm.dagger.module.MainActivityModule;
import com.moviesdb.moviesdbmvvm.databinding.ActivityMainBinding;
import com.moviesdb.moviesdbmvvm.viewmodel.MainActivityViewModel;
import com.moviesdb.moviesdbmvvm.viewmodel.MainActivityVisability;
import com.moviesdb.moviesdbmvvm.viewmodel.MainViewStatus;
import com.moviesdb.moviesdbmvvm.viewmodel.ViewModelFactory;

import java.util.Timer;

import javax.inject.Inject;

import timber.log.Timber;

public class  MainActivity extends BaseActivity<MainActivityViewModel> {

    //MovieDatabaseApi movieDatabaseApi;
    private ActivityMainBinding activityMainBinding;
    private MainActivityViewModel mainActivityViewModel;

    private static final  int PAGE_COUNT = 5;

    @Inject
    public MainActivityRecyclerViewAdapter mainActivityRecyclerViewAdapter;


    public  MainActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainActivityComponent mainActivityComponent =
                DaggerMainActivityComponent.builder()
                        .mainActivityModule(new MainActivityModule(this))
                        .moviesDBComponent(MoviesDBApplication.get(this).getMoviesDBComponent())
                        .build();
        mainActivityComponent.injectMainActivity(this);

        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);


    }

    @NonNull
    @Override
    protected ViewModelFactory<MainActivityViewModel> getViewModelFactory() {
       return ()->new MainActivityViewModel();
    }

    @Override
    protected void onViewModelCreatedOrRestored(@NonNull MainActivityViewModel viewModel) {
        mainActivityViewModel = viewModel;
        initDataBinding();
        initMainRecyclerList();
    }

    private void initDataBinding() {
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        activityMainBinding.setViewModel(mainActivityViewModel);
        activityMainBinding.setAdapter(mainActivityRecyclerViewAdapter);
        activityMainBinding.mainRecyclerView.setLayoutManager(new LinearLayoutManager(this) );
        mainActivityViewModel.status.observe(this,this::onChangeState );



    }

    private void onChangeState(MainActivityVisability visability){
        Log.d("Hello", "State: "+visability);

    }

    private void initMainRecyclerList(){
       // activityMainBinding.mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
      //  activityMainBinding.mainRecyclerView.setAdapter(mainActivityAdapter);
    }



}
