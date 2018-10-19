package com.moviesdb.moviesdbmvvm.viewmodel;

import com.moviesdb.moviesdbmvvm.network.MovieSocialNetworkApi;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class SeeMoreActivity extends AnotherActivity{
    public MovieSocialNetworkApi.ListType getListType() {
        return listType;
    }

    private MovieSocialNetworkApi.ListType listType;

    public SeeMoreActivity(MovieSocialNetworkApi.ListType listType) {
        this.listType = listType;
    }
}
