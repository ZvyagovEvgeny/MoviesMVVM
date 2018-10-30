package com.moviesdb.moviesdbmvvm.ui.moviedetails.viewmodel;

import android.databinding.ObservableField;

import com.moviesdb.moviesdbmvvm.data.model.themoviedb.Genre;
import com.moviesdb.moviesdbmvvm.data.model.themoviedb.MovieDetail;

import com.moviesdb.moviesdbmvvm.utils.ImagesUtils;
import com.moviesdb.moviesdbmvvm.ui.base.viewmodel.ViewModelBase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import timber.log.Timber;

public class MainInfoViewModel extends ViewModelBase {

    public ObservableField<MovieDetail> movie = new ObservableField<>();
    public ObservableField<String> backdropPath = new ObservableField<>("");
    public ObservableField<String> runtime = new ObservableField<>("");

    public ObservableField<String> realiseYear = new ObservableField<>();

    public ObservableField<String>  genres = new ObservableField<>("");


    public MainInfoViewModel(MovieDetail movieDetail) {
        this.movie.set(movieDetail);
        realiseYear.set(String.valueOf(convert(movieDetail.getReleaseDate()).getYear()));
        runtime.set(getRuntime());
        genres.set(parseGenresList(movieDetail.getGenres()));
        backdropPath.set(ImagesUtils.getImagePath(movieDetail.getBackdropPath()));
        refreshCommands();
    }

    private String parseGenresList (List<Genre> genreList){

        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;

        for(Genre genre: genreList){
            stringBuilder.append(" ");
            stringBuilder.append(genre.getName());
            count++;
            if(count>3)break;
        }
        return stringBuilder.toString();
    }

    private String getRuntime(){
        try{
            Integer min =  movie.get().getRuntime();
            return String.valueOf(min);
        }catch (NullPointerException e){
            Timber.d(e);
        }
        return "";

    }

    private Date convert(String dtStart){
        if(dtStart==null || dtStart.isEmpty())return null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(dtStart);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void onMenuButtonClick(){

    }


    public CommandVM onAddMovieToWishListClick = new CommandVM() {

        @Override
        public void refresh() {
            isEnabled(false);
        }

        @Override
        public void execute() {
            Timber.d("onAddMovieToWishListClick");
        }
    };



}
