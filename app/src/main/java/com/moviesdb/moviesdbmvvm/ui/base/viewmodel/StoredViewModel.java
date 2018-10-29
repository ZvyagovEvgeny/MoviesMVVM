package com.moviesdb.moviesdbmvvm.ui.base.viewmodel;

import android.content.Context;

public interface StoredViewModel {

    void onActivityDetached();
    void onActivityDestroyed();
    void onAttached(Context context);

}
