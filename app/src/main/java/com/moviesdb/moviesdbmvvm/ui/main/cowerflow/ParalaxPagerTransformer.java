package com.moviesdb.moviesdbmvvm.ui.main.cowerflow;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.moviesdb.moviesdbmvvm.R;

public class ParalaxPagerTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(@NonNull View view, float position) {
        int pageWidth = view.getWidth();
        View posterLayout = view.findViewById(R.id.posterLayout);
        if(posterLayout!=null){
            posterLayout.setTranslationX( position * pageWidth);
        }
    }


}
