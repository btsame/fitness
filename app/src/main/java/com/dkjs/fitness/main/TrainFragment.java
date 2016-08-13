package com.dkjs.fitness.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessFragment;

/**
 * Created by administrator on 16/7/10.
 */
public class TrainFragment extends FitnessFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_timer, container, false);
        return view;
    }
}
