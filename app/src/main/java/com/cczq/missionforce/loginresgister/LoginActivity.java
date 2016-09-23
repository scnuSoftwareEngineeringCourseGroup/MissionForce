package com.cczq.missionforce.loginresgister;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cczq.missionforce.MainActivity;
import com.cczq.missionforce.R;
import com.cczq.missionforce.loginresgister.utils.SessionManager;

/**
 * 登陆的Activity
 * Created by Shyuan on 2016/9/23.
 */

public class LoginActivity extends Activity {

    //便于logCat tag输出的tag
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button loginBtn;
    private Button linkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog progressDialog;
    private SessionManager sessionManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.btnLogin);
        linkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        //初始化progressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        //初始化SessionManager
        sessionManager = new SessionManager(getApplicationContext());

        //检查用户是否已登陆
        if (sessionManager.isLoggedIn()) {
            //如果用户已登陆，跳转到MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        //登陆按钮被点击
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                // 检查是否输入空值
                if (email.trim().length() > 0 && password.trim().length() > 0) {
                    // login user
                    checkLogin(email, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        //跳转到注册页面
        linkToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 检查登陆认证
     * @param email
     * @param password
     */
    private void checkLogin(final String email,final String password)
    {
        //用来取消请求
        String tag_string_req = "req_login";
        progressDialog.setMessage("Logging in ...");
        showDialog();

        //StringRequest strReq = new StringRequest(Method.POST,Config_URL.)

    }


    /**
     * 显示进度窗口
     */
    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    /**
     * 隐藏进度窗口
     */
    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
