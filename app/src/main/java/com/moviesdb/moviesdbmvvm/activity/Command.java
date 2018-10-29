package com.moviesdb.moviesdbmvvm.activity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public abstract class Command extends BaseObservable{
    private boolean isEnabled = true;
    @Bindable
    public boolean isEnabled(){return isEnabled;}

    public void isEnabled(boolean isEnabled){
        this.isEnabled = isEnabled;
        notifyChange();
    }
    public abstract void execute();
}
