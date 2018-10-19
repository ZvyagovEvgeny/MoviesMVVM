package com.moviesdb.moviesdbmvvm.viewmodel.base;

import com.moviesdb.moviesdbmvvm.viewmodel.base.StoredViewModel;

public interface ViewModelFactory<T extends StoredViewModel> {

    T create();

}
