package com.moviesdb.moviesdbmvvm.activity.fragments;

import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.moviesdb.moviesdbmvvm.BR;
import com.moviesdb.moviesdbmvvm.R;
import com.moviesdb.moviesdbmvvm.activity.Command;
import com.moviesdb.moviesdbmvvm.activity.IMenuCallbackListener;
import com.moviesdb.moviesdbmvvm.activity.IMenuHandler;
import com.moviesdb.moviesdbmvvm.activity.MenuCommandBindings;
import com.moviesdb.moviesdbmvvm.activity.adapter.PicassoWrapper;
import com.moviesdb.moviesdbmvvm.application.App;
import com.moviesdb.moviesdbmvvm.databinding.MovieDetailsMainInfoBinding;
import com.moviesdb.moviesdbmvvm.viewmodel.movie_details.MainInfoViewModel;
import com.squareup.picasso.Picasso;

import java.util.Observable;

import timber.log.Timber;

public class DetailsHeaderFragment extends MyFragment<MainInfoViewModel,MovieDetailsMainInfoBinding> {

    private Context context;

    public DetailsHeaderFragment() {
        super(R.layout.movie_details_main_info, BR.viewModel);
    }

    public static DetailsHeaderFragment newInstance(io.reactivex.Observable<MainInfoViewModel>viewModelObservable){
        DetailsHeaderFragment i = new DetailsHeaderFragment();
        i.setViewModel(viewModelObservable);
        return i;
    }

    @Override
    protected void onBind(MovieDetailsMainInfoBinding bind) {
        Picasso picasso = App.create(getContext()).getMoviesDBComponent().getPicasso();
        bind.setPicassoWrapper(new PicassoWrapper(picasso));
        bind.menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenuButonClicked(v);
            }
        });

    }

    public void onMenuButonClicked(View view){

        MainInfoViewModel viewModel = getViewModel();
        PopupMenu popupMenu = new PopupMenu(getContext(),view);

        MenuCommandBindings menuCommandBindings = menuCommandBindings(popupMenu,R.menu.movies_details_menu);

        menuCommandBindings.addBinding( R.id.addToWatchList,viewModel.onAddMovieToWishListClick, MenuCommandBindings.EnableBinding.enabled);
        menuCommandBindings.onPrepareOptionsMenu(popupMenu.getMenu());

        popupMenu.show();
    }

    public MenuCommandBindings menuCommandBindings(PopupMenu popupMenu, int menuId){

        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(menuId,popupMenu.getMenu());
        MenuCommandBindings menuCommandBindings = new MenuCommandBindings();
        popupMenu.setOnMenuItemClickListener((item)->menuCommandBindings.onOptionsItemSelected(item.getItemId()));
        return menuCommandBindings;
    }

}
