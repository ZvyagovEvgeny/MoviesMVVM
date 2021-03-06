package com.moviesdb.moviesdbmvvm.ui.base.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public abstract class MyFragment<T,K extends ViewDataBinding> extends Fragment{

    int cellId;
    int layoutId;

    protected T viewModel;
    protected T getViewModel(){return viewModel;}

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private K binding;

    protected MyFragment(int layoutId, int cellId){
        this.cellId = cellId;
        this.layoutId = layoutId;
    }

    public void setViewModel(Observable<T> viewModel){

        compositeDisposable.dispose();
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(viewModel.subscribe(this::setViewModel));
    }

    private void setViewModel(T viewModel){
        if(viewModel==null)return;
        this.viewModel = viewModel;
        if (binding!=null){
            Timber.d("MyFragment: "+viewModel.getClass().getSimpleName());
            binding.setVariable(cellId,viewModel);
        }
        if(binding!=null)
            onBind(binding);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater,layoutId, container, false);

        setViewModel(viewModel);
        onBind(binding);
        View view = binding.getRoot();
        return view;

    }



    protected void onBind(K  bind){}




}
