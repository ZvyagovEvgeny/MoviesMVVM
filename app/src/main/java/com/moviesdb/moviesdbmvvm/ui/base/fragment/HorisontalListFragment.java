package com.moviesdb.moviesdbmvvm.ui.base.fragment;

import android.support.v7.widget.LinearLayoutManager;

import com.moviesdb.moviesdbmvvm.BR;
import com.moviesdb.moviesdbmvvm.databinding.HorisontalListFragmentBinding;
import com.moviesdb.moviesdbmvvm.R;
import com.moviesdb.moviesdbmvvm.ui.base.fragment.MyFragment;

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
