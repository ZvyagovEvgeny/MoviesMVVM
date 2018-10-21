package com.moviesdb.moviesdbmvvm.viewmodel.start_activity_params;

import android.content.Context;

import com.moviesdb.moviesdbmvvm.network.MovieSocialNetworkApi;

public class SeeMoreActivityParams extends AnotherActivity {
    public MovieSocialNetworkApi.ListType getListType() {
        return listType;
    }

    private MovieSocialNetworkApi.ListType listType;

    public SeeMoreActivityParams(MovieSocialNetworkApi.ListType listType) {
        this.listType = listType;
    }

    @Override
    public void startActivity(Context context) {

    }
}
