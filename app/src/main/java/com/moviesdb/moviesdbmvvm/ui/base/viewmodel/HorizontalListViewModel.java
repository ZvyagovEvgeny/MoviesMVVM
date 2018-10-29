package com.moviesdb.moviesdbmvvm.ui.base.viewmodel;

import android.databinding.BaseObservable;

import android.databinding.ObservableField;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class HorizontalListViewModel<VM> extends BaseObservable{

    public List<VM> objects;
    public ObservableField<String> listTitle = new ObservableField<>();
    public ObservableField<String> seeMoreText = new ObservableField<>();

    private PublishSubject<HorizontalListViewModel<VM>> onSeeMoreItemsSubject =
            PublishSubject.create();

    public Observable<HorizontalListViewModel<VM>> getOnSeeMoreItemsSubject() {
        return onSeeMoreItemsSubject;
    }

    public HorizontalListViewModel(List<VM> objects, String listTitle, String seeMoreText) {
        this.objects =(objects);
        this.listTitle.set(listTitle);
        this.seeMoreText.set(seeMoreText);
        notifyChange();
    }

    public void onSeeMoreItems(){
        onSeeMoreItemsSubject.onNext(this);
    }

}
