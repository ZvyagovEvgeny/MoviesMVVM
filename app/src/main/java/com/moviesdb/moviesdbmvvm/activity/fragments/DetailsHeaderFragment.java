package com.moviesdb.moviesdbmvvm.activity.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moviesdb.moviesdbmvvm.BR;
import com.moviesdb.moviesdbmvvm.R;
import com.moviesdb.moviesdbmvvm.activity.adapter.PicassoWrapper;
import com.moviesdb.moviesdbmvvm.activity.adapter.ViewModelAdapter;
import com.moviesdb.moviesdbmvvm.application.MoviesDBApplication;
import com.moviesdb.moviesdbmvvm.databinding.MovieDetailsMainInfoBinding;
import com.moviesdb.moviesdbmvvm.viewmodel.movie_details.MainInfoViewModel;
import com.squareup.picasso.Picasso;

public class DetailsHeaderFragment extends MyFragment<MainInfoViewModel,MovieDetailsMainInfoBinding> {


    private Picasso picasso;

    public void init(Picasso picasso){
        this.picasso = picasso;
    }

    public DetailsHeaderFragment() {
        super(R.layout.movie_details_main_info, BR.viewModel);
    }



    @Override
    protected void onBind(MovieDetailsMainInfoBinding bind) {
        bind.setPicassoWrapper(new PicassoWrapper(picasso));
    }
}
