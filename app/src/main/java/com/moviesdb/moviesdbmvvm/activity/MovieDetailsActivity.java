package com.moviesdb.moviesdbmvvm.activity;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.moviesdb.moviesdbmvvm.R;
import com.moviesdb.moviesdbmvvm.activity.adapter.CastListAdapter;
import com.moviesdb.moviesdbmvvm.activity.adapter.MovieDetailsRecyclerViewAdapter;

import com.moviesdb.moviesdbmvvm.activity.adapter.PicassoWrapper;
import com.moviesdb.moviesdbmvvm.activity.fragments.DetailsHeaderFragment;
import com.moviesdb.moviesdbmvvm.activity.fragments.HorisontalCastFragment;
import com.moviesdb.moviesdbmvvm.application.MoviesDBApplication;
import com.moviesdb.moviesdbmvvm.dagger.component.DaggerMovieDetailsComponent;
import com.moviesdb.moviesdbmvvm.dagger.component.MovieDetailsComponent;
import com.moviesdb.moviesdbmvvm.dagger.component.MoviesDBComponent;
import com.moviesdb.moviesdbmvvm.dagger.module.MovieDetailsModule;
import com.moviesdb.moviesdbmvvm.databinding.ActivityMovieDetailsBinding;
import com.moviesdb.moviesdbmvvm.databinding.HorisontalListFragmentBinding;
import com.moviesdb.moviesdbmvvm.viewmodel.base.ViewModelFactory;
import com.moviesdb.moviesdbmvvm.viewmodel.movie_details.MovieDetailsViewModel;
import com.squareup.picasso.Picasso;

import java.util.zip.Inflater;

import javax.inject.Inject;

public class MovieDetailsActivity extends BaseActivity<MovieDetailsViewModel> {
    private MovieDetailsViewModel movieDetailsViewModel;
    private ActivityMovieDetailsBinding activityMovieDetailsBinding;

    @Inject
    public MovieDetailsRecyclerViewAdapter movieDetailsRecyclerViewAdapter;

    @Inject
    public ViewModelFactory<MovieDetailsViewModel> viewModelFactory;

    @Inject
    public MoviesDBComponent moviesDBComponent;

    @Inject
    public Picasso picasso;

    public  MovieDetailsActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int movieId = getIntent().getIntExtra("movieId", -1);
        MovieDetailsComponent movieDetailsComponent =
                DaggerMovieDetailsComponent.builder()
                .movieDetailsModule(new MovieDetailsModule(this,movieId))
                .moviesDBComponent(MoviesDBApplication.get(this).getMoviesDBComponent())
                .build();
        movieDetailsComponent.injectActivity(this);

        super.onCreate(savedInstanceState);

    }

    @NonNull
    @Override
    protected ViewModelFactory<MovieDetailsViewModel> getViewModelFactory() {
        return viewModelFactory;
    }

    @Override
    protected void onViewModelCreatedOrRestored(@NonNull MovieDetailsViewModel viewModel) {
        movieDetailsViewModel = viewModel;
        initDataBinding();
    }

    private void initDataBinding() {
        activityMovieDetailsBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_movie_details);

        activityMovieDetailsBinding.setViewModel(movieDetailsViewModel);
        activityMovieDetailsBinding.setAdapter(movieDetailsRecyclerViewAdapter);

       FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();
        HorisontalCastFragment horisontalCastFragment = new HorisontalCastFragment();
        horisontalCastFragment.setPicasso(picasso);
        horisontalCastFragment.setViewModel(movieDetailsViewModel.castListViewModel);
        fragmentTransaction.replace(R.id.castListWrapper,horisontalCastFragment);


/*
        HorisontalListFragmentBinding binding =  DataBindingUtil.inflate(getLayoutInflater(),R.layout.horisontal_list_fragment,activityMovieDetailsBinding.castListWrapper,true);
        binding.recyclerView.setAdapter(new CastListAdapter(new PicassoWrapper(picasso),this));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        movieDetailsViewModel.castListViewModel.subscribe((view)->binding.setViewModel(view));
*/

        DetailsHeaderFragment detailsHeaderFragment = new DetailsHeaderFragment();
        detailsHeaderFragment.init(picasso);
        detailsHeaderFragment.setViewModel(movieDetailsViewModel.mainInfoViewModel);
        fragmentTransaction.replace(R.id.movieHeader,detailsHeaderFragment);

        fragmentTransaction.commit();

    }
}
