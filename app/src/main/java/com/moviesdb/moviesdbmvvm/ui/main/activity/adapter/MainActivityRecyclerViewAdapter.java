package com.moviesdb.moviesdbmvvm.ui.main.activity.adapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Pair;
import android.view.View;

import com.moviesdb.moviesdbmvvm.R;
import com.moviesdb.moviesdbmvvm.ui.main.cowerflow.ParalaxPagerTransformer;
import com.moviesdb.moviesdbmvvm.ui.base.adapter.ViewModelAdapter;
import com.moviesdb.moviesdbmvvm.ui.base.fragment.HorisontalListFragment;
import com.moviesdb.moviesdbmvvm.databinding.MoviesCoverFlowBinding;
import com.moviesdb.moviesdbmvvm.ui.main.cowerflow.MovieListCowerFlowViewModel;
import com.moviesdb.moviesdbmvvm.ui.main.viewmodel.movies_line.MoviesLineViewModel;
import com.moviesdb.moviesdbmvvm.databinding.MoviesLineBinding;
import com.squareup.picasso.Picasso;
import com.moviesdb.moviesdbmvvm.BR;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class MainActivityRecyclerViewAdapter extends ViewModelAdapter{

    Picasso picasso;

    PagerAdapter pagerAdapter;

    AppCompatActivity activity;
    private Map<Object, ViewModelAdapter> adapterMap = new Hashtable<>();
    public List<Pair<Object,ViewModelAdapter>> itemsList;

    public MainActivityRecyclerViewAdapter(Picasso picasso,AppCompatActivity activity){
        registerCell(MoviesLineViewModel.class, R.layout.movies_line,BR.viewModel);
        registerCell(MovieListCowerFlowViewModel.class, R.layout.movies_cover_flow,BR.viewModel);


        this.activity = activity;
        this.picasso = picasso;
    }

    @Override
    protected void onBind(ViewDataBinding viewDataBinding) {
        HorisontalListFragment horisontalListFragment = new HorisontalListFragment();
        horisontalListFragment.getView();

        if(viewDataBinding instanceof  MoviesLineBinding){


            MoviesLineBinding moviesLineBinding = (MoviesLineBinding) viewDataBinding;
            moviesLineBinding.moviesLinerRV.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false));
            moviesLineBinding.moviesLinerRV.setAdapter(new MoviesLineAdapter(picasso));

        }
        if(viewDataBinding instanceof MoviesCoverFlowBinding){

            pagerAdapter = new MoviesCowerFlowFragmentAdapter(activity.getSupportFragmentManager(),picasso);
            MoviesCoverFlowBinding moviesCoverFlowBinding = (MoviesCoverFlowBinding)viewDataBinding;
            moviesCoverFlowBinding.pager.setAdapter(pagerAdapter);
            moviesCoverFlowBinding.pager.setPageTransformer(false,new ParalaxPagerTransformer());

        }
    }


    public void itemSelected(MoviesLineViewModel moviesLineViewModel, View v){

    }

    @Override
    public void reload(@Nullable SwipeRefreshLayout refreshLayout) {

    }
}
