package com.moviesdb.moviesdbmvvm.ui.main.cowerflow;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moviesdb.moviesdbmvvm.R;
import com.moviesdb.moviesdbmvvm.ui.main.adapter.MoviesCowerFlowFragmentAdapter;
import com.moviesdb.moviesdbmvvm.ui.main.viewmodel.movies_line.MovieListItemViewModel;

import com.moviesdb.moviesdbmvvm.databinding.MovieCoverFlowItemBinding;
import com.squareup.picasso.Picasso;

public class PageFragment extends Fragment{

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    int pageNumber;
    int backColor;
    static final String SAVE_PAGE_NUMBER = "save_page_number";
    private Picasso picasso;
    private MoviesCowerFlowFragmentAdapter cowerFlowFragmentAdapter;

    public void setCowerFlowFragmentAdapter(MoviesCowerFlowFragmentAdapter cowerFlowFragmentAdapter) {
        this.cowerFlowFragmentAdapter = cowerFlowFragmentAdapter;
    }

    public void setPicasso(Picasso picasso) {
        this.picasso = picasso;
    }

    private MovieListItemViewModel viewModel;


    public static PageFragment newInstance() {
        return new PageFragment();
    }

    public void setViewModel(MovieListItemViewModel viewModel){
        this.viewModel = viewModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MovieCoverFlowItemBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.movie_cover_flow_item, container, false);
        View view = binding.getRoot();
        //here data must be an instance of the class MarsDataProvider
        binding.setViewModel(viewModel);
        binding.setAdapter(cowerFlowFragmentAdapter);
        return view;
    }

}
