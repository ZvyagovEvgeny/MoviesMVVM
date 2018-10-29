package com.moviesdb.moviesdbmvvm.ui.main.activity.adapter;

import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.moviesdb.moviesdbmvvm.BR;
import com.moviesdb.moviesdbmvvm.R;
import com.moviesdb.moviesdbmvvm.ui.base.adapter.PicassoWrapper;
import com.moviesdb.moviesdbmvvm.ui.base.adapter.ViewModelAdapter;
import com.moviesdb.moviesdbmvvm.ui.main.viewmodel.movies_line.MovieListItemViewModel;
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
