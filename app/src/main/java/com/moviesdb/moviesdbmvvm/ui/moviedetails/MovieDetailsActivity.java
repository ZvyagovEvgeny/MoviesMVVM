package com.moviesdb.moviesdbmvvm.ui.moviedetails;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.moviesdb.moviesdbmvvm.R;

import com.moviesdb.moviesdbmvvm.dagger.component.DaggerMovieDetailsComponent;
import com.moviesdb.moviesdbmvvm.ui.moviedetails.fragment.DetailsHeaderFragment;
import com.moviesdb.moviesdbmvvm.ui.moviedetails.fragment.HorizontalCastFragment;
import com.moviesdb.moviesdbmvvm.application.App;

import com.moviesdb.moviesdbmvvm.dagger.component.MovieDetailsComponent;
import com.moviesdb.moviesdbmvvm.dagger.component.MoviesDBComponent;
import com.moviesdb.moviesdbmvvm.dagger.module.MovieDetailsModule;
import com.moviesdb.moviesdbmvvm.databinding.ActivityMovieDetailsBinding;
import com.moviesdb.moviesdbmvvm.ui.base.activity.BaseActivity;
import com.moviesdb.moviesdbmvvm.ui.base.viewmodel.ViewModelFactory;
import com.moviesdb.moviesdbmvvm.ui.moviedetails.viewmodel.MovieDetailsViewModel;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;


public class MovieDetailsActivity extends BaseActivity<MovieDetailsViewModel> {
    private MovieDetailsViewModel movieDetailsViewModel;
    private ActivityMovieDetailsBinding activityMovieDetailsBinding;

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
                .moviesDBComponent(App.get(this).getMoviesDBComponent())
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

        FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.castListWrapper,
                HorizontalCastFragment.newInstance(movieDetailsViewModel.castListViewModel));

        fragmentTransaction.replace(R.id.movieHeader,DetailsHeaderFragment
                .newInstance(movieDetailsViewModel.mainInfoViewModel));

        fragmentTransaction.commit();

    }

}
