package com.moviesdb.moviesdbmvvm.activity.adapter;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import com.moviesdb.moviesdbmvvm.BR;
import com.moviesdb.moviesdbmvvm.R;
import com.moviesdb.moviesdbmvvm.viewmodel.main.movies_line.MoviesLineViewModel;
import com.moviesdb.moviesdbmvvm.viewmodel.movie_details.MainInfoViewModel;
import com.squareup.picasso.Picasso;

public class MovieDetailsRecyclerViewAdapter extends ViewModelAdapter {

    Picasso picasso;
    AppCompatActivity appCompatActivity;

    public MovieDetailsRecyclerViewAdapter(Picasso picasso, AppCompatActivity activity){

        this.picasso = picasso;
        this.appCompatActivity = activity;

        registerCell(MainInfoViewModel.class, R.layout.movie_details_main_info, BR.viewModel);
        addGlobalItem(BR.picassoWrapper,new PicassoWrapper(picasso));

    }



    @Override
    public void reload(@Nullable SwipeRefreshLayout refreshLayout) {

    }
}
