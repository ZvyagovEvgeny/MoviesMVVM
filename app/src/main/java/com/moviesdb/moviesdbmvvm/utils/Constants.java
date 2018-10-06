package com.moviesdb.moviesdbmvvm.utils;

import java.util.HashMap;

public class Constants {


    public static String moviesDBSocialNetwork = "";
    public static HashMap<String,String> defaultParamsForSocialNetworkApi;
    public static HashMap<String,String>defaultParamsForOpenMovieDatabaseApi;
    static {
        defaultParamsForSocialNetworkApi.put("api_key", "123");
        defaultParamsForOpenMovieDatabaseApi.put("api_key","123");
    }


}
