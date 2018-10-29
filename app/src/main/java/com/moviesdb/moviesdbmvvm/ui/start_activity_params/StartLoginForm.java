package com.moviesdb.moviesdbmvvm.ui.start_activity_params;

import android.content.Context;
import android.content.Intent;

import com.moviesdb.moviesdbmvvm.ui.login.LoginActivity;

public class StartLoginForm extends AnotherActivity {
    @Override
    public void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
