package com.moviesdb.moviesdbmvvm.dagger.component;

import com.moviesdb.moviesdbmvvm.ui.main.MainActivity;
import com.moviesdb.moviesdbmvvm.dagger.module.MainActivityModule;
import com.moviesdb.moviesdbmvvm.dagger.scope.MainActivityScope;

import dagger.Component;

@MainActivityScope
@Component(dependencies = {MoviesDBComponent.class} ,modules = {MainActivityModule.class})
public interface MainActivityComponent {

    void injectMainActivity(MainActivity mainActivity);
}
