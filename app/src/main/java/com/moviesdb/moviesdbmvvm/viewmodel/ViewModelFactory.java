package com.moviesdb.moviesdbmvvm.viewmodel;

public interface ViewModelFactory<T extends StoredViewModel> {

    T create();

}
