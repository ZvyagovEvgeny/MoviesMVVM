package com.moviesdb.moviesdbmvvm.ui.moviedetails.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.moviesdb.moviesdbmvvm.BR;
import com.moviesdb.moviesdbmvvm.R;
import com.moviesdb.moviesdbmvvm.ui.base.adapter.PicassoWrapper;
import com.moviesdb.moviesdbmvvm.ui.base.adapter.ViewModelAdapter;
import com.moviesdb.moviesdbmvvm.ui.moviedetails.viewmodel.CastItemViewModel;

public class CastListAdapter extends ViewModelAdapter {

    private Context context;
    public CastListAdapter(PicassoWrapper picassoWrapper, Context context){
        registerCell(CastItemViewModel.class, R.layout.cast_item, BR.castItem);
        addGlobalItem(BR.picassoWrapper,picassoWrapper);
        this.context = context;
    }

    @Override
    public void reload(@Nullable SwipeRefreshLayout refreshLayout) {

    }
}
