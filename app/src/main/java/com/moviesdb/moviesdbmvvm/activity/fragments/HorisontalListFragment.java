package com.moviesdb.moviesdbmvvm.activity.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moviesdb.moviesdbmvvm.BR;
import com.moviesdb.moviesdbmvvm.databinding.HorisontalListFragmentBinding;
import com.moviesdb.moviesdbmvvm.R;
import com.moviesdb.moviesdbmvvm.activity.cowerflow.PageFragment;
import com.moviesdb.moviesdbmvvm.databinding.HorisontalListFragmentBinding;
import com.moviesdb.moviesdbmvvm.viewmodel.HorizontalListViewModel;

public class HorisontalListFragment<T> extends MyFragment<T,HorisontalListFragmentBinding> {

    public HorisontalListFragment(){
        super( R.layout.horisontal_list_fragment, BR.viewModel);
    }

    @Override
    protected void onBind(HorisontalListFragmentBinding bind) {
        super.onBind(bind);
        bind.recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        getContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false));
    }
}
