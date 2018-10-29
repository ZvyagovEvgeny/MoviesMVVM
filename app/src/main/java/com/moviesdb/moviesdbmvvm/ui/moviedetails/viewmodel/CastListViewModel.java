package com.moviesdb.moviesdbmvvm.ui.moviedetails.viewmodel;

import com.moviesdb.moviesdbmvvm.ui.base.viewmodel.HorizontalListViewModel;

import java.util.List;

public class CastListViewModel extends HorizontalListViewModel<CastItemViewModel> {

    public CastListViewModel(List<CastItemViewModel> objects, String listTitle, String seeMoreText) {
        super(objects, listTitle, seeMoreText);
    }
}
