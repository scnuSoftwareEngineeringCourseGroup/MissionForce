package com.cczq.missionforce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cczq.missionforce.Model.Mission;
import com.john.waveview.WaveView;

import cn.iwgang.countdownview.CountdownView;
import io.feeeei.circleseekbar.CircleSeekBar;

/**
 * Created by Shyuan on 2016/10/13.
 */

public class countDownActivity extends Activity {


    private Mission mission;
    private CircleSeekBar mSecondSeekbar;
    private CircleSeekBar mMinuteSeekbar;
    private TextView missionNameTextView;
    private TextView missionDescriptionTextView;
    private WaveView waveView;
    private Button btnCommitMission;
    private int remainPer;

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
        waveView = (WaveView) findViewById(R.id.wave_view);
        missionNameTextView = (TextView) findViewById(R.id.missionName);
        missionDescriptionTextView = (TextView) findViewById(R.id.missionDescription);
        btnCommitMission = (Button)findViewById(R.id.btn_commitMission);

        Intent intent = getIntent();
        mission = (Mission) intent.getSerializableExtra("mission");
        waveView.setProgress(0);
        remainPer = 0;
        missionNameTextView.setText(mission.missionNameText);
        missionDescriptionTextView.setText(mission.missionDescriptionText);

        final CountdownView mCvCountdownView = (CountdownView) findViewById(R.id.countdownView);
        //时间改变的回调函数  基本单位平均是10毫秒
        mCvCountdownView.setOnCountdownIntervalListener(10 * 60, new CountdownView.OnCountdownIntervalListener() {
            @Override
            public void onInterval(CountdownView cv, long remainTime) {
                double missionTime = mission.time * 1000 * 60;
                remainPer = (int) (((missionTime - remainTime) / missionTime) * 100);
                waveView.setProgress(remainPer);
            }
        });
        btnCommitMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

//        //1秒钟的线程  Wave
//        new Thread() {
//            @Override
//            public void run() {
//                while (remainPer != 100) {
//                   // Log.d("debug", "remainPer" + Integer.toString(remainPer));
//                    int num = remainPer - 3;
//                    if (num >= 0) {
//                        Log.d("debug", "num = " + Integer.toString(num));
//                        for (int i = num; i <= num + 6; i++) {
//                            mWaveHandler.sendEmptyMessage(i);
//                            SystemClock.sleep(100);
//                        }
//                        for (int i = num + 6; i >= num; i--) {
//                            mWaveHandler.sendEmptyMessage(i);
//                            SystemClock.sleep(100);
//                        }
//                    }
//                }
//                Log.d("debug", "end Wave");
//            }
//        }.start();
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

//    private Handler mWaveHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            int value = msg.what;
//            waveView.setProgress(value);
//        }
//    };
}
