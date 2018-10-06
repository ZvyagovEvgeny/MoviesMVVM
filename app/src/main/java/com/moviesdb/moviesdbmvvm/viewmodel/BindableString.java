package com.moviesdb.moviesdbmvvm.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.BindingConversion;

public class BindableString extends BaseObservable {

    private String value;

    public String getValue() {
        if(value==null)return "";
        return value;
    }

    public void set(String value){
        this.value = value;
        notifyChange();;
    }

    public boolean isEmpty(){
        return value==null || value.isEmpty();
    }

    @BindingConversion
    public static String convertBindableToString(BindableString bindableString){
        return bindableString.getValue();
    }

}
