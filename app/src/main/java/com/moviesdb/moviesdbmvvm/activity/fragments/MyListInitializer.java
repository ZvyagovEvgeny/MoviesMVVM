package com.moviesdb.moviesdbmvvm.activity.fragments;

import android.databinding.ViewDataBinding;

import java.util.Map;

public abstract class MyListInitializer<T extends ViewDataBinding> {

    private Map<Integer, Object> objectMap;
    public abstract void onBind(T viewDataBinding);


    public Map<Integer,Object> getObjectMap(){
        return objectMap;
    }

}
