package com.cczq.missionforce.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cczq.missionforce.R;
import com.cczq.missionforce.utils.MissionListAdapter;

/**
 * Created by Shyuan on 2016/10/11.
 */

public class MissionFragment extends SwipeRefreshListFragemnt {

    private MissionListAdapter missionListAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        missionListAdapter = new MissionListAdapter (getActivity());
        setListAdapter(missionListAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mission, container, false);
    }



    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        System.out.println("Click On List Item!!!");
        super.onListItemClick(l, v, position, id);
    }
}