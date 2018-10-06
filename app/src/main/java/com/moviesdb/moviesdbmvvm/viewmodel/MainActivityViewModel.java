package com.moviesdb.moviesdbmvvm.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;


import com.moviesdb.moviesdbmvvm.Movie;
import com.moviesdb.moviesdbmvvm.activity.adapter.ViewModelAdapter;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class MainActivityViewModel extends BaseObservable implements StoredViewModel {
    private Context context;

    //public List<ViewModel> data = new ArrayList<>();;

    public List<MoviesLineViewModel> moviesLineViewModels = new ArrayList<>();



    public  MainActivityViewModel (){
        moviesLineViewModels.add(new MoviesLineViewModel(MoviesLineViewModel.MoviesListType.COMING_SOON));
        moviesLineViewModels.add(new MoviesLineViewModel(MoviesLineViewModel.MoviesListType.TOP_RATED));

        notifyChange();
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
        this.context = context;
    }
}
