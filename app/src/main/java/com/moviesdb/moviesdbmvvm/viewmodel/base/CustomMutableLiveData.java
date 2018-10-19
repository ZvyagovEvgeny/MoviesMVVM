package com.moviesdb.moviesdbmvvm.viewmodel.base;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.BaseObservable;
import android.databinding.Observable;

public class CustomMutableLiveData  <T extends BaseObservable> extends MutableLiveData<T> {

    public  CustomMutableLiveData(T value){
        setValue(value);
    }

    @Override
    public void setValue(T value){
        super.setValue(value);

        value.addOnPropertyChangedCallback(callback);
    }

    Observable.OnPropertyChangedCallback callback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            setValue(getValue());
        }
    };
}
