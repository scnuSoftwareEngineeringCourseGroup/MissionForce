package com.cczq.missionforce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.cczq.missionforce.Model.Mission;

import cn.iwgang.countdownview.CountdownView;
import io.feeeei.circleseekbar.CircleSeekBar;

/**
 * Created by Shyuan on 2016/10/13.
 */

public class countDownActivity extends Activity {


    private Mission mission;
    private CircleSeekBar mSecondSeekbar;
    private CircleSeekBar mMinuteSeekbar;

    private countDownActivity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);

        mSecondSeekbar = (CircleSeekBar) findViewById(R.id.seek_second);
        mMinuteSeekbar = (CircleSeekBar) findViewById(R.id.seek_minute);

        Intent intent = getIntent();
        mission = (Mission) intent.getSerializableExtra("mission");

        Toast.makeText(this,
                mission.missionNameText + mission.groupNameText + Integer.toString(mission.time) + Integer.toString(mission.MID), Toast.LENGTH_LONG).show();

        final CountdownView mCvCountdownView = (CountdownView) findViewById(R.id.countdownView);
        //时间改变的回调函数  基本单位平均是10毫秒
        mCvCountdownView.setOnCountdownIntervalListener(10*60, new CountdownView.OnCountdownIntervalListener() {
            @Override
            public void onInterval(CountdownView cv, long remainTime) {

                //  int Second = (mSecondSeekbar.getCurProcess() + 1) % 100;
                //    mSecondSeekbar.setCurProcess(Second);
            //    int Minute = (mMinuteSeekbar.getCurProcess() + 1) % 1000;
            //    mMinuteSeekbar.setCurProcess(Minute);
                  Log.d("debug","onInterval");

            }
        });

        start();
        mCvCountdownView.setTag("countDownView");
        mCvCountdownView.start(mission.time * 1000 * 60);
    }

    private void start() {
        //1秒钟的线程
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    mHandler.sendEmptyMessage(i);
                    if (i == 100)
                        i = 0;
                    SystemClock.sleep(10);
                }
            }
        }.start();

        //十秒钟的线程
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i <= 1000; i++) {
                    mMinuteHandler.sendEmptyMessage(i);
                    if (i == 1000)
                        i = 0;
                    SystemClock.sleep(10);
                }
            }
        }.start();
    }


    private Handler mMinuteHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int value = msg.what;
            mMinuteSeekbar.setCurProcess(value);
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int value = msg.what;
            mSecondSeekbar.setCurProcess(value);
        }
    };
}
