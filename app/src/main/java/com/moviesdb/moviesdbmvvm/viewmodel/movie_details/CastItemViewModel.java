package com.moviesdb.moviesdbmvvm.viewmodel.movie_details;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.moviesdb.moviesdbmvvm.model.themoviedb.Cast;
import com.moviesdb.moviesdbmvvm.network.ImagesUtils;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class CastItemViewModel extends BaseObservable{

    public ObservableField<String> fullName  = new ObservableField<>();
    public ObservableField<String> photoPath = new ObservableField<>();
    public ObservableField<String> chatacter = new ObservableField<>();

    private PublishSubject<CastItemViewModel> onClicked = PublishSubject.create();

    public Observable<CastItemViewModel> getOnClicked() {
        return onClicked;
    }
    public void onClick(){

    }

    public CastItemViewModel(Cast cast){
        fullName.set(cast.getName());
        photoPath.set(ImagesUtils.getImagePath(cast.getProfilePath()));
        chatacter.set(cast.getCharacter());
    }

}
