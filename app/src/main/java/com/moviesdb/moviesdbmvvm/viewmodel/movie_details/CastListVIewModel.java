package com.moviesdb.moviesdbmvvm.viewmodel.movie_details;

import com.moviesdb.moviesdbmvvm.viewmodel.HorizontalListViewModel;

import java.util.List;

public class CastListVIewModel extends HorizontalListViewModel<CastItemViewModel> {

    public CastListVIewModel(List<CastItemViewModel> objects, String listTitle, String seeMoreText) {
        super(objects, listTitle, seeMoreText);
    }
}
