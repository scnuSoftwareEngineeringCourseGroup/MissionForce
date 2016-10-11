package com.cczq.missionforce;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.cczq.missionforce.loginresgister.utils.SessionManager;
import com.cczq.missionforce.utils.SQLiteHandler;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final long RIPPLE_DURATION = 250;
    private SQLiteHandler db;
    private SessionManager session;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.content_hamburger)
    View contentHamburger;
    @BindView(R.id.btnLogout)
    Button btnLogout;

    @OnClick(R.id.btnLogout)
    void Logout() {
        logoutUser();
    }

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

        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);
        //GuillotineBuilder的第一个参数为菜单的View,
        //第二个参数为关闭菜单的View也就是菜单布局中的按钮,
        //第三个参数为打开菜单的View也就是主页面中的按钮
        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
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
    }

    private void jumpToGroupFragment() {
        Log.d("jumpToGroupFragment!", "debug");
    }

    private void jumpToMissionFragment() {
        Log.d("jumpToMissionFragment!", "debug");
    }

    private void jumpToSettingFragment() {
        Log.d("jumpToSettingFragment!", "debug");
    }


}
