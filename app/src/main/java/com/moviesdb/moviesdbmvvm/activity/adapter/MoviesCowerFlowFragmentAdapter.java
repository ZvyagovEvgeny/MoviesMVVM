package com.moviesdb.moviesdbmvvm.activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.moviesdb.moviesdbmvvm.activity.cowerflow.PageFragment;
import com.moviesdb.moviesdbmvvm.viewmodel.main.movies_line.MovieListItemViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesCowerFlowFragmentAdapter extends FragmentStatePagerAdapter {

    public List<MovieListItemViewModel> movieListItemViewModels = new ArrayList<>();

    private Picasso picasso;

    public Picasso getPicasso() {
        return picasso;
    }

    public MoviesCowerFlowFragmentAdapter(FragmentManager fm, Picasso picasso) {
        super(fm);
        this.picasso = picasso;
    }

    public void insertList( List<MovieListItemViewModel> movieListItemViewModels ){
        this.movieListItemViewModels.clear();
        this.movieListItemViewModels.addAll(movieListItemViewModels);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        MovieListItemViewModel viewModel = movieListItemViewModels.get(position);

        PageFragment pageFragment  = PageFragment.newInstance();
        pageFragment.setViewModel(viewModel);
        pageFragment.setCowerFlowFragmentAdapter(this);
        return pageFragment;
    }

    @Override
    public int getCount() {
        return movieListItemViewModels.size();
    }

}
