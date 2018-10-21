package com.moviesdb.moviesdbmvvm.activity;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;

import com.moviesdb.moviesdbmvvm.activity.adapter.MainActivityRecyclerViewAdapter;
import com.moviesdb.moviesdbmvvm.application.MoviesDBApplication;
import com.moviesdb.moviesdbmvvm.dagger.component.DaggerMovieDetailsComponent;
import com.moviesdb.moviesdbmvvm.dagger.component.MovieDetailsComponent;
import com.moviesdb.moviesdbmvvm.dagger.module.MovieDetailsModule;
import com.moviesdb.moviesdbmvvm.viewmodel.base.ViewModelFactory;
import com.moviesdb.moviesdbmvvm.viewmodel.main.MainActivityVisability;
import com.moviesdb.moviesdbmvvm.viewmodel.movie_details.MovieDetailsViewModel;

import javax.inject.Inject;

public class MovieDetailsActivity extends BaseActivity<MovieDetailsViewModel> {
    //MovieDatabaseApi movieDatabaseApi;
   // private ActivityMainBinding activityMainBinding;
    //private MovieDetailsViewModel movieDetailsActivity;

    @Inject
    public MainActivityRecyclerViewAdapter mainActivityRecyclerViewAdapter;


    public  MovieDetailsActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MovieDetailsComponent movieDetailsComponent =
                DaggerMovieDetailsComponent.builder()
                .movieDetailsModule(new MovieDetailsModule(this))
                .moviesDBComponent(MoviesDBApplication.get(this).getMoviesDBComponent())
                .build();

        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    protected ViewModelFactory<MovieDetailsViewModel> getViewModelFactory() {
        return ()->new MovieDetailsViewModel();
    }

    @Override
    protected void onViewModelCreatedOrRestored(@NonNull MovieDetailsViewModel viewModel) {
      /*  movieDetailsActivity = viewModel;
        initDataBinding();
        initMainRecyclerList();*/
    }


    private void initDataBinding() {
      /*  activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        activityMainBinding.setViewModel(mainActivityViewModel);
        activityMainBinding.setAdapter(mainActivityRecyclerViewAdapter);
        activityMainBinding.mainRecyclerView.setLayoutManager(new LinearLayoutManager(this) );
        mainActivityViewModel.status.observe(this,this::onChangeState );
*/


    }

    private void onChangeState(MainActivityVisability visability){
        Log.d("Hello", "State: "+visability);

    }

    private void initMainRecyclerList(){
        // activityMainBinding.mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //  activityMainBinding.mainRecyclerView.setAdapter(mainActivityAdapter);
    }
}
