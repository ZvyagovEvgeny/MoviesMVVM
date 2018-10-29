package com.moviesdb.moviesdbmvvm.activity;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.moviesdb.moviesdbmvvm.R;

import com.moviesdb.moviesdbmvvm.activity.adapter.MainActivityRecyclerViewAdapter;
import com.moviesdb.moviesdbmvvm.application.App;
import com.moviesdb.moviesdbmvvm.dagger.component.DaggerMainActivityComponent;
import com.moviesdb.moviesdbmvvm.dagger.component.MainActivityComponent;
import com.moviesdb.moviesdbmvvm.dagger.module.MainActivityModule;
import com.moviesdb.moviesdbmvvm.databinding.ActivityMainBinding;
import com.moviesdb.moviesdbmvvm.viewmodel.main.MainActivityViewModel;
import com.moviesdb.moviesdbmvvm.viewmodel.base.ViewModelFactory;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class  MainActivity extends BaseActivity<MainActivityViewModel> {

    //MovieDatabaseApi movieDatabaseApi;
    private ActivityMainBinding activityMainBinding;
    private MainActivityViewModel mainActivityViewModel;

    private boolean viewModelInited = false;

    @Inject
    public MainActivityRecyclerViewAdapter mainActivityRecyclerViewAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main,menu);
        menu.add("Hello");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void initDagger(){
        MainActivityComponent mainActivityComponent =
                DaggerMainActivityComponent.builder()
                        .mainActivityModule(new MainActivityModule(this))
                        .moviesDBComponent(App.get(this).getMoviesDBComponent())
                        .build();
        mainActivityComponent.injectMainActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @NonNull
    @Override
    protected ViewModelFactory<MainActivityViewModel> getViewModelFactory() {
       return ()->new MainActivityViewModel();
    }

    @Override
    protected void onViewModelCreatedOrRestored(@NonNull MainActivityViewModel viewModel) {
        mainActivityViewModel = viewModel;
        addMenuBinding(R.id.search_button,mainActivityViewModel.saveCommand, MenuCommandBindings.EnableBinding.enabled);
        addMenuBinding(R.id.searchField,mainActivityViewModel.searchCommand,MenuCommandBindings.EnableBinding.enabled);
        initViewModel();
        initDataBinding();
        initMainRecyclerList();
    }

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private void initViewModel(){
        if(viewModelInited)return;
        compositeDisposable.dispose();;
        compositeDisposable = new CompositeDisposable();
        Disposable disposable =  mainActivityViewModel.getAnotherActivityObserver()
                .observeOn(AndroidSchedulers.mainThread()).subscribe((activity)->{
            activity.startActivity(this);
        });
        compositeDisposable.add(disposable);
        viewModelInited = true;
    }

    private void initDataBinding() {
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        activityMainBinding.setViewModel(mainActivityViewModel);
        activityMainBinding.setAdapter(mainActivityRecyclerViewAdapter);
        activityMainBinding.mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initMainRecyclerList(){
       // activityMainBinding.mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
      //  activityMainBinding.mainRecyclerView.setAdapter(mainActivityAdapter);
    }
}
