package com.moviesdb.moviesdbmvvm.activity.adapter;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v4.util.Pair;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

import android.widget.EditText;
import android.widget.ImageView;

import com.moviesdb.moviesdbmvvm.R;
import com.moviesdb.moviesdbmvvm.activity.adapter.editable.TextWatcherAdapter;
import com.moviesdb.moviesdbmvvm.viewmodel.main.movies_line.MovieListItemViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

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

    @BindingAdapter("data")
    public static void bind(ViewPager viewPager,List<MovieListItemViewModel> data){
        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter==null)return;
        if(adapter instanceof MoviesCowerFlowFragmentAdapter){
            ((MoviesCowerFlowFragmentAdapter) adapter).insertList(data);
        }
    }

    @BindingAdapter({"imageUrl","picasso"})
    public static void imageBind(ImageView imageView,  String url, Picasso picasso){
        picasso.get()
                .load(url)
                /*.placeholder(R.drawable.dravable_animation)*/
                .into(imageView);
    }

}
