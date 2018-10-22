package com.moviesdb.moviesdbmvvm.activity;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.moviesdb.moviesdbmvvm.R;
import com.moviesdb.moviesdbmvvm.activity.adapter.MainActivityRecyclerViewAdapter;
import com.moviesdb.moviesdbmvvm.activity.adapter.MovieDetailsRecyclerViewAdapter;
import com.moviesdb.moviesdbmvvm.application.MoviesDBApplication;
import com.moviesdb.moviesdbmvvm.dagger.component.DaggerMovieDetailsComponent;
import com.moviesdb.moviesdbmvvm.dagger.component.MovieDetailsComponent;
import com.moviesdb.moviesdbmvvm.dagger.module.MovieDetailsModule;
import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieDetail;
import com.moviesdb.moviesdbmvvm.databinding.ActivityMovieDetailsBinding;
import com.moviesdb.moviesdbmvvm.viewmodel.base.ViewModelFactory;
import com.moviesdb.moviesdbmvvm.viewmodel.main.MainActivityVisability;
import com.moviesdb.moviesdbmvvm.viewmodel.movie_details.MovieDetailsViewModel;

import javax.inject.Inject;

public class MovieDetailsActivity extends BaseActivity<MovieDetailsViewModel> {
    //MovieDatabaseApi movieDatabaseApi;
   // private ActivityMainBinding activityMainBinding;
    //private MovieDetailsViewModel viewModelFactory;

    private MovieDetailsViewModel movieDetailsViewModel;
    private ActivityMovieDetailsBinding activityMovieDetailsBinding;

    @Inject
    public MovieDetailsRecyclerViewAdapter movieDetailsRecyclerViewAdapter;

    @Inject
    public ViewModelFactory<MovieDetailsViewModel> viewModelFactory;

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
        initMainRecyclerList();
    }


    private void initDataBinding() {
        activityMovieDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);

        activityMovieDetailsBinding.setViewModel(movieDetailsViewModel);
        activityMovieDetailsBinding.setAdapter(movieDetailsRecyclerViewAdapter);
        activityMovieDetailsBinding.mainRecyclerView.setLayoutManager(new LinearLayoutManager(this) );




    }

    private void onChangeState(MainActivityVisability visability){
        Log.d("Hello", "State: "+visability);

    }

    private void initMainRecyclerList(){
        // activityMainBinding.mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //  activityMainBinding.mainRecyclerView.setAdapter(mainActivityAdapter);
    }
}
