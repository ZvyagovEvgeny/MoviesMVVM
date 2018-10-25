package com.moviesdb.moviesdbmvvm.activity.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.moviesdb.moviesdbmvvm.BR;
import com.moviesdb.moviesdbmvvm.R;
import com.moviesdb.moviesdbmvvm.viewmodel.movie_details.CastItemViewModel;

public class CastListAdapter extends ViewModelAdapter{

    private Context context;
    public CastListAdapter(PicassoWrapper picassoWrapper,Context context){
        registerCell(CastItemViewModel.class, R.layout.cast_item, BR.castItem);
        addGlobalItem(BR.picassoWrapper,picassoWrapper);
        this.context = context;
    }

    @Override
    public void reload(@Nullable SwipeRefreshLayout refreshLayout) {

    }
}
