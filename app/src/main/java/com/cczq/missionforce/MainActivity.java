package com.cczq.missionforce;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cczq.missionforce.fragment.GroupFragment;
import com.cczq.missionforce.fragment.MissionFragment;
import com.cczq.missionforce.fragment.PersonalFragment;
import com.cczq.missionforce.fragment.SettingsFragment;
import com.cczq.missionforce.loginresgister.LoginActivity;
import com.cczq.missionforce.loginresgister.utils.SessionManager;
import com.cczq.missionforce.utils.SQLiteHandler;
import com.cczq.missionforce.widget.CanaroTextView;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnMenuItemClickListener, OnMenuItemLongClickListener {

    private static final long RIPPLE_DURATION = 250;
    private SQLiteHandler db;
    public SessionManager session;
    private GuillotineAnimation guillotineAnimation;
    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private Menu mMenu;
    // private boolean isgroup;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.content_hamburger)
    View contentHamburger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        //创建碎片管理器
        fragmentManager = getSupportFragmentManager();

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
        GroupFragment firstFragment = new GroupFragment();
        //取Intent的附加数据作为fragment构造函数的参数
        firstFragment.setArguments(getIntent().getExtras());
        //把fragment嵌入到容器
        fragmentManager.beginTransaction().add(R.id.fragment_container, firstFragment).commit();
        ((TextView) findViewById(R.id.titleBar)).setText("小组");

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

        findViewById(R.id.personal_group).setOnClickListener(this);
        findViewById(R.id.group_group).setOnClickListener(this);
        findViewById(R.id.mission_group).setOnClickListener(this);
        findViewById(R.id.settings_group).setOnClickListener(this);

        initMenuFragment();
    }

    @Override
    public void onClick(View v) {
        jumpToTheFragment(v.getId());
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        Toast.makeText(this, "Clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
        Toast.makeText(this, "Long clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        mMenu = menu;
        return true;
        //     this.menu = menu;
//        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * @param i 通过点击的资源id来跳转到想要的fragement
     */
    private void jumpToTheFragment(int i) {
        clearSelect();
        Fragment jumpToFragment = null;
        switch (i) {
            case R.id.personal_group:
                ((TextView) findViewById(R.id.titleBar)).setText("个人资料");
                ((CanaroTextView) findViewById(R.id.personal)).setTextAppearance(this, R.style.TextView_GuillotineItem_Selected);
                jumpToFragment = new PersonalFragment();
                hiddenMenu();
                break;
            case R.id.group_group:
                ((TextView) findViewById(R.id.titleBar)).setText("小组");
                ((CanaroTextView) findViewById(R.id.group)).setTextAppearance(this, R.style.TextView_GuillotineItem_Selected);
                jumpToFragment = new GroupFragment();
                // onCreateMenu();
                showMenu();
                break;
            case R.id.mission_group:
                ((TextView) findViewById(R.id.titleBar)).setText("任务");
                ((CanaroTextView) findViewById(R.id.mission)).setTextAppearance(this, R.style.TextView_GuillotineItem_Selected);
                jumpToFragment = new MissionFragment();
                hiddenMenu();
                break;
            case R.id.settings_group:
                ((TextView) findViewById(R.id.titleBar)).setText("设置");
                ((CanaroTextView) findViewById(R.id.settings)).setTextAppearance(this, R.style.TextView_GuillotineItem_Selected);
                jumpToFragment = new SettingsFragment();
                hiddenMenu();
                break;
            default:
                guillotineAnimation.close();
                return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //文章fragment--newFragment完全取代容器上的一个fragment
        transaction.replace(R.id.fragment_container, jumpToFragment);
        //增加一个返回堆栈，用户可以导航回来
        //transaction.addToBackStack(null);
        //提交事务
        transaction.commit();
        //关闭guillotineAnimation
        guillotineAnimation.close();
    }

    /**
     * 清除相应的选择
     */
    private void clearSelect() {
        ((CanaroTextView) findViewById(R.id.personal)).setTextAppearance(this, R.style.TextView_GuillotineItem);
        ((CanaroTextView) findViewById(R.id.group)).setTextAppearance(this, R.style.TextView_GuillotineItem);
        ((CanaroTextView) findViewById(R.id.mission)).setTextAppearance(this, R.style.TextView_GuillotineItem);
        ((CanaroTextView) findViewById(R.id.settings)).setTextAppearance(this, R.style.TextView_GuillotineItem);
    }

    public void logoutUser() {
        session.setLogin(false, session.UID());
        db.deleteUsers();
        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    private List<MenuObject> getMenuObjects() {
        // You can use any [resource, bitmap, drawable, color] as image:
        // item.setResource(...)
        // item.setBitmap(...)
        // item.setDrawable(...)
        // item.setColor(...)
        // You can set image ScaleType:
        // item.setScaleType(ScaleType.FIT_XY)
        // You can use any [resource, drawable, color] as background:
        // item.setBgResource(...)
        // item.setBgDrawable(...)
        // item.setBgColor(...)
        // You can use any [color] as text color:
        // item.setTextColor(...)
        // You can set any [color] as divider color:
        // item.setDividerColor(...)

        //创建菜单的Objects实例集合
        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icn_close);

        MenuObject send = new MenuObject("Send message");
        send.setResource(R.drawable.icn_1);

        MenuObject like = new MenuObject("Like profile");
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icn_2);
        like.setBitmap(b);

        MenuObject addFr = new MenuObject("Add to friends");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.icn_3));
        addFr.setDrawable(bd);

        MenuObject addFav = new MenuObject("Add to favorites");
        addFav.setResource(R.drawable.icn_4);

        MenuObject block = new MenuObject("Block user");
        block.setResource(R.drawable.icn_5);

        menuObjects.add(close);
        menuObjects.add(send);
        menuObjects.add(like);
        menuObjects.add(addFr);
        menuObjects.add(addFav);
        menuObjects.add(block);
        return menuObjects;
    }

    /**
     * 隐藏Menu
     */
    private void hiddenMenu() {
        if (null != mMenu) {
            //		MenuInflater menuInflater = getMenuInflater();
            //	    menuInflater.inflate(R.menu.activity_main, menu);
            //hidden when first time
            for (int i = 0; i < mMenu.size(); i++) {
                mMenu.getItem(i).setVisible(false);
                mMenu.getItem(i).setEnabled(false);
            }
        }
    }


    /**
     * 显示Menu
     */
    private void showMenu() {
        if (null != mMenu) {
            for (int i = 0; i < mMenu.size(); i++) {
                mMenu.getItem(i).setVisible(true);
                mMenu.getItem(i).setEnabled(true);
            }
        }
    }


}
