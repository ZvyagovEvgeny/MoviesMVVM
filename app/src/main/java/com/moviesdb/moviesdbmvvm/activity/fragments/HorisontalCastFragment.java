package com.moviesdb.moviesdbmvvm.activity.fragments;

import android.support.v7.widget.LinearLayoutManager;

import com.moviesdb.moviesdbmvvm.BR;
import com.moviesdb.moviesdbmvvm.R;
import com.moviesdb.moviesdbmvvm.activity.adapter.CastListAdapter;
import com.moviesdb.moviesdbmvvm.activity.adapter.PicassoWrapper;
import com.moviesdb.moviesdbmvvm.databinding.HorisontalListFragmentBinding;
import com.moviesdb.moviesdbmvvm.viewmodel.movie_details.CastListVIewModel;
import com.squareup.picasso.Picasso;

public class HorisontalCastFragment extends MyFragment<CastListVIewModel,HorisontalListFragmentBinding>{

    private Picasso picasso;

    public HorisontalCastFragment(){
        super( R.layout.horisontal_list_fragment, BR.viewModel);
    }

    public void setPicasso(Picasso picasso){
        this.picasso = picasso;
    }

    @Override
    protected void onBind(HorisontalListFragmentBinding bind) {

        super.onBind(bind);
        bind.recyclerView.setAdapter(new CastListAdapter(new PicassoWrapper(picasso),getContext()));
        bind.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
    }
}
