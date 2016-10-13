package com.cczq.missionforce.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cczq.missionforce.Model.Mission;
import com.cczq.missionforce.R;

import java.util.ArrayList;

/**
 * Created by Shyuan on 2016/10/11.
 */


public class MissionListAdapter extends BaseAdapter {

    private class MissionViewHolder {
        //  public ImageView animal;
        public TextView missionNameTextView;
        public TextView groupNameTextView;
        public TextView timeTextView;
        // public ImageView speaker;
    }


    public ArrayList<Mission> missionData = new ArrayList<Mission>();

    private LayoutInflater mInflater = null;

    public MissionListAdapter(Context context) {
        super();
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return missionData.size();
    }

    @Override
    public Object getItem(int position) {
        return missionData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MissionViewHolder holder = null;
        if (convertView == null) {
            holder = new MissionViewHolder();
            convertView = mInflater.inflate(R.layout.mission, null);
            holder.missionNameTextView = (TextView) convertView.findViewById(R.id.cn_word);
            holder.groupNameTextView = (TextView) convertView.findViewById(R.id.en_word);
            holder.timeTextView = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        } else {
            holder = (MissionViewHolder) convertView.getTag();
        }
        holder.missionNameTextView.setText(missionData.get(position).missionNameText);
        holder.groupNameTextView.setText(missionData.get(position).groupNameText);
        holder.timeTextView.setText(missionData.get(position).timeText);
        return convertView;
    }


}

