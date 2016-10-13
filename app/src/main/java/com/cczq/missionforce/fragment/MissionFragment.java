package com.cczq.missionforce.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cczq.missionforce.R;
import com.cczq.missionforce.utils.MissionListAdapter;
import com.cczq.missionforce.widget.SwipeRefreshListFragment;

import java.util.List;

/**
 * Created by Shyuan on 2016/10/11.
 */

public class MissionFragment extends SwipeRefreshListFragment {

    private static final String LOG_TAG = MissionFragment.class.getSimpleName();

    private static final int LIST_ITEM_COUNT = 20;

    private MissionListAdapter missionListAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        missionListAdapter = new MissionListAdapter(getActivity());
        setListAdapter(missionListAdapter);
        setColorScheme(R.color.greenyellow, R.color.lawngreen, R.color.mediumspringgreen, R.color.lightgreen);

        //下拉刷新
        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");

                initiateRefresh();
            }
        });
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        System.out.println("Click On List Item!!!");
        super.onListItemClick(l, v, position, id);
    }

    private void initiateRefresh() {
        Log.i(LOG_TAG, "initiateRefresh");
        new BackgroundTask().execute();
    }

    private void onRefreshComplete() {
        //Log.i(LOG_TAG, "onRefreshComplete");

        // Remove all items from the ListAdapter, and then replace them with the new items
//        ArrayAdapter&lt;String&gt; adapter = (ArrayAdapter&lt;String&gt;) getListAdapter();
//        adapter.clear();
//        for (String cheese : result) {
//            adapter.add(cheese);
//        }

        // Stop the refreshing indicator
        setRefreshing(false);
    }
    private class BackgroundTask extends AsyncTask<Void, Void, List<String>> {

        static final int TASK_DURATION = 3 * 1000; // 3 seconds


        @Override
        protected List<String> doInBackground(Void... params) {
            // Sleep for a small amount of time to simulate a background-task
            try {
                Log.i(LOG_TAG, "thread to sleep");
                Thread.sleep(TASK_DURATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
            // Return a new random list of cheeses
            //  return Cheeses.randomList(LIST_ITEM_COUNT);
        }

        @Override
        protected void onPostExecute(List<String> result) {
            super.onPostExecute(result);
            Log.i(LOG_TAG, "end to post");
            // Tell the Fragment that the refresh has completed
            // onRefreshComplete(result);
            onRefreshComplete();
        }

    }
}