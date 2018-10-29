package com.moviesdb.moviesdbmvvm.activity.fragments;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.moviesdb.moviesdbmvvm.BR;
import com.moviesdb.moviesdbmvvm.R;
import com.moviesdb.moviesdbmvvm.activity.adapter.CastListAdapter;
import com.moviesdb.moviesdbmvvm.activity.adapter.PicassoWrapper;
import com.moviesdb.moviesdbmvvm.application.App;
import com.moviesdb.moviesdbmvvm.databinding.HorisontalListFragmentBinding;
import com.moviesdb.moviesdbmvvm.viewmodel.movie_details.CastListViewModel;
import com.squareup.picasso.Picasso;

import io.reactivex.Observable;

public class HorizontalCastFragment extends MyFragment<CastListViewModel,HorisontalListFragmentBinding>{

    public HorizontalCastFragment(){
        super( R.layout.horisontal_list_fragment, BR.viewModel);
    }

    public static HorizontalCastFragment newInstance(Observable<CastListViewModel> viewModel){
        HorizontalCastFragment i = new HorizontalCastFragment();
        i.setViewModel(viewModel);
        return i;
    }

    public static View getInitedView(HorisontalListFragmentBinding bind, Context context){
        Picasso picasso = App.create(context).getMoviesDBComponent().getPicasso();
        bind.recyclerView.setAdapter(new CastListAdapter(new PicassoWrapper(picasso),context));
        bind.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        return bind.getRoot();
    }

    @Override
    protected void onBind(HorisontalListFragmentBinding bind) {

        super.onBind(bind);
        getInitedView(bind,getContext());
    }
}
