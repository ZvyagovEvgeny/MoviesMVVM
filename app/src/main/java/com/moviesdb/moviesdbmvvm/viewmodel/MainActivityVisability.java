package com.moviesdb.moviesdbmvvm.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;

public class MainActivityVisability extends BaseObservable{
    private MainViewStatus mainViewStatus = MainViewStatus.INITIAL_DOWNLOADS_IN_PROGRESS;

    public MainViewStatus getMainViewStatus() {
        return mainViewStatus;
    }

    public boolean recyclerViewVisible;
    public boolean errorMessageVisible;
    public boolean initialProgressBarVisible;

    public void setState(MainViewStatus state){

        errorMessageVisible = false;
        initialProgressBarVisible = false;
        recyclerViewVisible = false;
        mainViewStatus = state;
        switch (state){
            case ERROR:
                errorMessageVisible = true;
                break;
            case FIELDS_SHOWING:
                recyclerViewVisible = true;
                break;
            case INITIAL_DOWNLOADS_IN_PROGRESS:
                initialProgressBarVisible = true;
                break;
        }
        notifyChange();
    }

}
