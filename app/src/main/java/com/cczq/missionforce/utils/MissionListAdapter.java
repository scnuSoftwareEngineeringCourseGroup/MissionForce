package com.cczq.missionforce.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cczq.missionforce.R;

/**
 * Created by Shyuan on 2016/10/11.
 */

class MissionViewHolder {
    //  public ImageView animal;
    public TextView missonNameText;
    public TextView groupNameText;
    public TextView timeText;
    // public ImageView speaker;
}


public class MissionListAdapter extends BaseAdapter {

    private LayoutInflater mInflater = null;

    public MissionListAdapter(Context context) {
        super();
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
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
            // holder.animal = (ImageView) convertView.findViewById(R.id.animal);
            holder.missonNameText = (TextView) convertView.findViewById(R.id.cn_word);
            holder. groupNameText = (TextView) convertView.findViewById(R.id.en_word);
            holder.timeText = (TextView) convertView.findViewById(R.id.time);
            // holder.speaker = (ImageView) convertView.findViewById(R.id.speaker);

            convertView.setTag(holder);
        } else {
            holder = (MissionViewHolder) convertView.getTag();
        }
        //    holder.animal.setImageResource(R.drawable.ic_activity_active);
        holder.missonNameText.setText("任务名");
        holder.groupNameText.setText("小组");
        holder.timeText.setText("50分钟");
        //   holder.speaker.setImageResource(R.drawable.ic_activity_active);

        //     holder.speaker.setOnClickListener(new View.OnClickListener(){

        //          @Override
//            public void onClick(View v) {
//                //System.out.println("Click on the speaker image on ListItem ");
        //          }
//        });

        return convertView;
    }
}
