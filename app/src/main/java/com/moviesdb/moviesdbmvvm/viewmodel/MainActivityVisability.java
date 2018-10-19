package com.moviesdb.moviesdbmvvm.viewmodel;

import android.databinding.BaseObservable;

public class MainActivityVisability extends BaseObservable{
    private ViewModelStatus viewModelStatus = ViewModelStatus.INITIAL_DOWNLOADS_IN_PROGRESS;

    public ViewModelStatus getViewModelStatus() {
        return viewModelStatus;
    }

    public boolean recyclerViewVisible;
    public boolean errorMessageVisible;
    public boolean initialProgressBarVisible;

    public void setState(ViewModelStatus state){

        errorMessageVisible = false;
        initialProgressBarVisible = false;
        recyclerViewVisible = false;
        viewModelStatus = state;
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
