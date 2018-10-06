package com.moviesdb.moviesdbmvvm.activity.adapter;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.design.circularreveal.CircularRevealWidget;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;

import android.widget.EditText;

import com.moviesdb.moviesdbmvvm.Movie;
import com.moviesdb.moviesdbmvvm.R;
import com.moviesdb.moviesdbmvvm.activity.adapter.editable.TextWatcherAdapter;
import com.moviesdb.moviesdbmvvm.viewmodel.MoviesLineViewModel;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

public class ObservableAdapters {

    @BindingAdapter({"app:binding"})
    public static void bindEditText(EditText view,
                                    final ObservableField<String> observableField) {

        Pair<ObservableField<String>, TextWatcherAdapter> pair =
                (Pair) view.getTag(R.id.bound_observable);
        if (pair == null || pair.first != observableField) {
            if (pair != null) {
                view.removeTextChangedListener(pair.second);
            }
            TextWatcherAdapter watcher = new TextWatcherAdapter() {
                public void onTextChanged(CharSequence s,
                                          int start, int before, int count) {
                    observableField.set(s.toString());
                }
            };
            view.setTag(R.id.bound_observable,
                    new Pair<>(observableField, watcher));
            view.addTextChangedListener(watcher);
        }
        String newValue = observableField.get();
        if (!view.getText().toString().equals(newValue)) {
            view.setText(newValue);
        }
    }

    @BindingAdapter("data")
    public static void bind(RecyclerView recyclerView, List<?> data) {
        if(recyclerView.getAdapter()==null)return;
        if(recyclerView.getAdapter() instanceof ViewModelAdapter){
            ((ViewModelAdapter) recyclerView.getAdapter()).insertList(data);
        }
    }
}
