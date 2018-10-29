package com.moviesdb.moviesdbmvvm.activity;

import android.view.Menu;

public interface IMenuCallbackListener {
    void onPrepareOptionsMenu(Menu menu);
    boolean onOptionsItemSelected(int menuItemId);
}
