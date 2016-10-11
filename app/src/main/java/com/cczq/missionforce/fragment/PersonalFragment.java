package com.cczq.missionforce.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cczq.missionforce.R;

/**
 * Created by Shyuan on 2016/10/11.
 */

public class PersonalFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_personal, container, false);
    }
}
