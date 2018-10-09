package com.moviesdb.moviesdbmvvm.activity.adapter;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.moviesdb.moviesdbmvvm.BR;
import com.moviesdb.moviesdbmvvm.R;
import com.moviesdb.moviesdbmvvm.viewmodel.MovieListItemViewModel;
import com.squareup.picasso.Picasso;

public class MoviesLineAdapter extends ViewModelAdapter {

    private Picasso picasso;
    public MoviesLineAdapter(Picasso picasso){
        this.picasso = picasso;
        registerCell(MovieListItemViewModel.class, R.layout.movies_line_movie_item,BR.viewModel);
        PicassoWrapper picassoWrapper = new PicassoWrapper(picasso);
        addGlobalItem(BR.picassoWrapper,picassoWrapper);
    }

    @Override
    public void reload(@Nullable SwipeRefreshLayout refreshLayout) {

    }
}
