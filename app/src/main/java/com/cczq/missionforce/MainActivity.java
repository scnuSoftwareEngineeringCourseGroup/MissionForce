package com.cczq.missionforce;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cczq.missionforce.fragment.GroupFragment;
import com.cczq.missionforce.fragment.MissionFragment;
import com.cczq.missionforce.fragment.PersonalFragment;
import com.cczq.missionforce.fragment.SettingsFragment;
import com.cczq.missionforce.loginresgister.utils.SessionManager;
import com.cczq.missionforce.utils.SQLiteHandler;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final long RIPPLE_DURATION = 250;
    private SQLiteHandler db;
    private SessionManager session;
    private GuillotineAnimation guillotineAnimation;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.content_hamburger)
    View contentHamburger;
//    @BindView(R.id.btnLogout)
//    Button btnLogout;
//
//    @OnClick(R.id.btnLogout)
//    void Logout() {
//        logoutUser();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        if (findViewById(R.id.fragment_container) != null) {

            //如果savedInstanceState不为空，说明只是从先前状态恢复，不必做任何工作直接返回
            if (savedInstanceState != null) {
                return;
            }
        }

        //创建Fragmen
        PersonalFragment firstFragment = new PersonalFragment();
        //取Intent的附加数据作为fragment构造函数的参数
        firstFragment.setArguments(getIntent().getExtras());
        //把fragment嵌入到容器
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();


        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);
        //GuillotineBuilder的第一个参数为菜单的View,
        //第二个参数为关闭菜单的View也就是菜单布局中的按钮,
        //第三个参数为打开菜单的View也就是主页面中的按钮
        guillotineAnimation = new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

        guillotineMenu.setOnClickListener(this);
        findViewById(R.id.personal).setOnClickListener(this);
        findViewById(R.id.group).setOnClickListener(this);
        findViewById(R.id.mission).setOnClickListener(this);
        findViewById(R.id.settings).setOnClickListener(this);

    }

    private void logoutUser() {
     /*   session.setLogin(false);
        db.deleteUsers();
        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();*/
        Log.d("click!logOut!", "debug");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.personal)
            jumpToPersonalFragment();
        if (v.getId() == R.id.group)
            jumpToGroupFragment();
        if (v.getId() == R.id.mission)
            jumpToMissionFragment();
        if (v.getId() == R.id.settings)
            jumpToSettingFragment();
    }

    private void jumpToPersonalFragment() {
        Log.d("jumpToPersonalFragment!", "debug");
        ((TextView) findViewById(R.id.titleBar)).setText("个人中心");
        PersonalFragment newFragment = new PersonalFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.commit();
        guillotineAnimation.close();
    }

    private void jumpToGroupFragment() {
        Log.d("jumpToGroupFragment!", "debug");
        GroupFragment newFragment = new GroupFragment();
        Bundle args = new Bundle();
        // args.putInt(ArticleFragment.ARG_POSITION,position);
        //newFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //文章fragment--newFragment完全取代容器上的一个fragment
        transaction.replace(R.id.fragment_container, newFragment);
        //增加一个返回堆栈，用户可以导航回来
        //transaction.addToBackStack(null);
        //提交事务
        transaction.commit();
        //关闭guillotineAnimation

        ((TextView) findViewById(R.id.titleBar)).setText("小组");
        guillotineAnimation.close();

    }

    private void jumpToMissionFragment() {
        Log.d("jumpToMissionFragment!", "debug");
        ((TextView) findViewById(R.id.titleBar)).setText("任务");
        MissionFragment newFragment = new MissionFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.commit();
        guillotineAnimation.close();
    }

    private void jumpToSettingFragment() {
        Log.d("jumpToSettingFragment!", "debug");
        SettingsFragment newFragment = new SettingsFragment();
        Bundle args = new Bundle();
        // args.putInt(ArticleFragment.ARG_POSITION,position);
        //newFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //文章fragment--newFragment完全取代容器上的一个fragment
        transaction.replace(R.id.fragment_container, newFragment);
        //增加一个返回堆栈，用户可以导航回来
        //transaction.addToBackStack(null);
        //提交事务
        transaction.commit();
        //关闭guillotineAnimation
        ((TextView) findViewById(R.id.titleBar)).setText("设置");
        guillotineAnimation.close();
    }


}
