package com.moviesdb.moviesdbmvvm.utils;

import android.content.Context;

import com.moviesdb.moviesdbmvvm.R;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    private Context context;

    public Constants(Context context) {
        this.context = context;
    }

    public static String moviesDBSocialNetwork = " https://api.themoviedb.org/3/";

    public Map<String, String> getNamesOfMoviesTypesLists(){
        Map<String,String> map = new HashMap<>();
        String arr[] = context.getResources().getStringArray(R.array.moviesListTypes);
        for(String keyVal:arr){
            String[] parsed = keyVal.split("|");
            map.put(parsed[0],parsed[1]);
        }
        return map;
    }

}
