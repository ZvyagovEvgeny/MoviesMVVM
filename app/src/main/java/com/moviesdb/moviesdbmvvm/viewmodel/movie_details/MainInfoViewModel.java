package com.moviesdb.moviesdbmvvm.viewmodel.movie_details;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.moviesdb.moviesdbmvvm.model.themoviedb.Genre;
import com.moviesdb.moviesdbmvvm.model.themoviedb.MovieDetail;
import com.moviesdb.moviesdbmvvm.network.ImagesUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import timber.log.Timber;

public class MainInfoViewModel extends BaseObservable {

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



}
