package com.moviesdb.moviesdbmvvm.viewmodel.movie_details;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.moviesdb.moviesdbmvvm.viewmodel.base.CustomMutableLiveData;
import com.moviesdb.moviesdbmvvm.viewmodel.base.StoredViewModel;
import com.moviesdb.moviesdbmvvm.viewmodel.main.MainActivityVisability;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsViewModel extends BaseObservable implements StoredViewModel {

    private int movieId;

    public CustomMutableLiveData<MainActivityVisability> status = new CustomMutableLiveData<>(new MainActivityVisability());
    public List<Object> viewModelsToShow = new ArrayList<>();
    public ObservableField<String> errorMessage = new ObservableField<>("Some error");


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
