package com.apps.android.prasannsinghal.pictomapper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Prasann Singhal on 3/14/2018.
 */

public class Coins extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.coins,container,false);
        return view;
    }
}
