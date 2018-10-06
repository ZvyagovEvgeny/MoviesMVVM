package com.moviesdb.moviesdbmvvm.activity.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;

import com.moviesdb.moviesdbmvvm.R;
import com.moviesdb.moviesdbmvvm.viewmodel.MoviesLineViewModel;
import com.moviesdb.moviesdbmvvm.databinding.MoviesLineBinding;
import com.squareup.picasso.Picasso;
import com.moviesdb.moviesdbmvvm.BR;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class MainActivityRecyclerViewAdapter extends ViewModelAdapter{

    Picasso picasso;


    Context context;
    private Map<Object, ViewModelAdapter> adapterMap = new Hashtable<>();
    public List<Pair<Object,ViewModelAdapter>> itemsList;


    public MainActivityRecyclerViewAdapter(Picasso picasso,Context context){
        registerCell(MoviesLineViewModel.class, R.layout.movies_line,BR.lineViewModel);
        this.context = context;
    }

    @Override
    protected void onBind(ViewDataBinding viewDataBinding) {
        if(viewDataBinding instanceof  MoviesLineBinding){
            MoviesLineBinding moviesLineBinding = (MoviesLineBinding) viewDataBinding;
            moviesLineBinding.moviesLinerRV.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            moviesLineBinding.moviesLinerRV.setAdapter(new MoviesLineAdapter(picasso));

        }
    }

    public void itemSelected(MoviesLineViewModel moviesLineViewModel, View v){

    }

    @Override
    public void reload(@Nullable SwipeRefreshLayout refreshLayout) {

    }
}
