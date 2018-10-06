package com.moviesdb.moviesdbmvvm.viewmodel;

import android.content.Context;

public interface StoredViewModel {

    void onActivityDetached();
    void onActivityDestroyed();
    void onAttached(Context context);

}
