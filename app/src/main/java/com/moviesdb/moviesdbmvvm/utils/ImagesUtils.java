package com.moviesdb.moviesdbmvvm.utils;

public class ImagesUtils {
    static public final String imagePrefix = "https://image.tmdb.org/t/p/w500/";

    static  public final String imagePrefixOriginal = "https://image.tmdb.org/t/p/original/";

    public static String getImagePath(String path){
        return imagePrefix + path;
    }


}
