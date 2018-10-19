package com.moviesdb.moviesdbmvvm.viewmodel.start_activity_params;

import com.moviesdb.moviesdbmvvm.network.MovieSocialNetworkApi;

public class SeeMoreActivity extends AnotherActivity {
    public MovieSocialNetworkApi.ListType getListType() {
        return listType;
    }

    private MovieSocialNetworkApi.ListType listType;

    public SeeMoreActivity(MovieSocialNetworkApi.ListType listType) {
        this.listType = listType;
    }
}
