package com.moviesdb.moviesdbmvvm.ui.base.viewmodel;

import com.moviesdb.moviesdbmvvm.ui.base.viewmodel.StoredViewModel;

public interface ViewModelFactory<T extends StoredViewModel> {

    T create();

}
