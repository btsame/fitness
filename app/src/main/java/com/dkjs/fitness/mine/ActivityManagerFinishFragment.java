package com.dkjs.fitness.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessFragment;
import com.dkjs.fitness.main.FindFragment;


public class ActivityManagerFinishFragment extends FitnessFragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_activity_finish,null);

        return view;
    }

}
