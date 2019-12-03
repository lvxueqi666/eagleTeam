package com.example.qiqi.xianwan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qiqi.xianwan.utils.HttpUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText ed_loginAccount;
    private EditText ed_loginPassword;
    private ImageView iv_login;
    private TextView tv_registerActivity;
    private CustomOnClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getViews();

        registerListeners();

    }

    private void registerListeners() {
        listener = new CustomOnClickListener();
        iv_login.setOnClickListener(listener);
        tv_registerActivity.setOnClickListener(listener);
    }


    private void getViews() {
        ed_loginAccount = findViewById(R.id.et_loginAccount);
        ed_loginPassword = findViewById(R.id.et_loginPassword);
        iv_login = findViewById(R.id.iv_login);
        tv_registerActivity = findViewById(R.id.tv_registerActivity);
    }

    class CustomOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_login:
                    String loginAddress = "http://10.7.89.148:8080/XianWanService/LoginServlet";
                    String userAccount = ed_loginAccount.getText().toString().trim();
                    String userPassword = ed_loginPassword.getText().toString().trim();
                    loginWithOkHttp(loginAddress,userAccount,userPassword);
                    break;
                case R.id.tv_registerActivity:
                    Intent intentRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intentRegister);
                    break;
            }
        }
    }

    //实现登录
    private void loginWithOkHttp(String loginAddress, String userAccount, String userPassword) {
        HttpUtils.loginWithOkHttp(loginAddress,userAccount,userPassword, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseData = response.body().string().trim();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Log.e("hahahaha",responseData);

                        if(responseData.equals("true")){
                            Toast.makeText(
                                    LoginActivity.this,
                                    "欢迎登录",
                                    Toast.LENGTH_SHORT
                            ).show();
                            Intent intentLogin = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intentLogin);
                        }
                        else {
                            Toast.makeText(
                                    LoginActivity.this,
                                    "账户或密码错误",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                });
            }
        });
    }

}
