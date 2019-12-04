package com.example.qiqi.xianwan;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qiqi.xianwan.runtimepermissions.PermissionsResultAction;
import com.example.qiqi.xianwan.utils.HttpUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

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

    private String userAccount;
    private String userPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /**
         * 请求所有必要的权限----原理就是获取清单文件中申请的权限
         */
        com.example.qiqi.xianwan.runtimepermissions.PermissionsManager.getInstance().
                requestAllManifestPermissionsIfNecessary(this,
                        new PermissionsResultAction() {
                            @Override
                            public void onGranted() {
//              Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onDenied(String permission) {
                                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
                            }
                        });


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
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_login:
                    String loginAddress = "http://"+hostIp+":8080/XianWanService/LoginServlet";
                    userAccount = ed_loginAccount.getText().toString().trim();
                    userPassword = ed_loginPassword.getText().toString().trim();
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
                            Login(userAccount,userPassword);
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
    //环信登录
    public void Login(String userAccount,String userPassword){
        EMClient.getInstance().login(userAccount,userPassword, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.i("sss:","环信用户："+userAccount+"--已登录");
                finish();
            }

            @Override
            public void onError(int i, String s) {
                Log.i("sss:","登录失败"+i+','+s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

}
