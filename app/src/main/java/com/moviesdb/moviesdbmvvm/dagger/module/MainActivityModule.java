package com.moviesdb.moviesdbmvvm.dagger.module;

import com.moviesdb.moviesdbmvvm.ui.main.MainActivity;
import com.moviesdb.moviesdbmvvm.ui.main.activity.adapter.MainActivityRecyclerViewAdapter;
import com.moviesdb.moviesdbmvvm.ui.main.activity.adapter.MoviesLineAdapter;
import com.moviesdb.moviesdbmvvm.dagger.scope.MainActivityScope;
import com.moviesdb.moviesdbmvvm.ui.main.viewmodel.MainActivityViewModel;
import com.moviesdb.moviesdbmvvm.ui.base.viewmodel.ViewModelFactory;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

@Module()
public class MainActivityModule {

    private final MainActivity mainActivity;

    public MainActivityModule (MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Provides
    @MainActivityScope
    public  MoviesLineAdapter moviesLineAdapter(Picasso picasso){
        return new MoviesLineAdapter(picasso);
    }

    @Provides
    @MainActivityScope
    public MainActivityRecyclerViewAdapter mainActivityRecyclerViewAdapter(Picasso picasso){
        return new MainActivityRecyclerViewAdapter(picasso,mainActivity);
    }


    @Provides
    @MainActivityScope
    public ViewModelFactory<MainActivityViewModel> viewModelViewModelFactory(){
        return ()->new MainActivityViewModel();
    }



}
