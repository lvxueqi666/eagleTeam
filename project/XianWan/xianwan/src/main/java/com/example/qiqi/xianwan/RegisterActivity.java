package com.example.qiqi.xianwan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qiqi.xianwan.utils.CodeUtils;
import com.example.qiqi.xianwan.utils.HttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText ed_registerName;
    private EditText ed_registerAccount;
    private EditText ed_registerPassword;
    private EditText ed_psw_again;
    private EditText ed_checked;
    private ImageView iv_checked;
    private ImageView iv_registerBack;
    private Button btn_register;
    private TextView tv_checked;
    private CustomOnClickListenrer listener;
    private Bitmap bitmap;
    private String code;
    private String checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getViews();
        registerListners();

        //获取工具类生成的图片验证码对象
        bitmap = CodeUtils.getInstance().createBitmap();
        //获取当前图片的验证码的对应内容，用于校验
        code = CodeUtils.getInstance().getCode();
        iv_checked.setImageBitmap(bitmap);
    }

    private void registerListners() {
        listener = new CustomOnClickListenrer();
        btn_register.setOnClickListener(listener);
        tv_checked.setOnClickListener(listener);
        iv_registerBack.setOnClickListener(listener);
    }

    private void getViews() {
        ed_registerName = findViewById(R.id.et_registerName);
        ed_registerAccount = findViewById(R.id.et_registerAccount);
        ed_registerPassword = findViewById(R.id.et_registerPassword);
        ed_psw_again = findViewById(R.id.et_psw_again);
        ed_checked = findViewById(R.id.et_checeked);
        iv_checked = findViewById(R.id.iv_checked);
        btn_register = findViewById(R.id.btn_register);
        tv_checked = findViewById(R.id.tv_checked);
        iv_registerBack = findViewById(R.id.iv_registerback);
    }

    class CustomOnClickListenrer implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_register:
                    checked = ed_checked.getText().toString().trim().toLowerCase();
                    code = code.toString().trim().toLowerCase();
                    if(checked.equals(code)){
                        String address = "http://10.7.89.148:8080/XianWanService/RegisterServlet";
                        String registerName = ed_registerName.getText().toString().trim();
                        String registerAccount = ed_registerAccount.getText().toString().trim();
                        String registerPassword = ed_registerPassword.getText().toString().trim();
                        String registerPasswordAgain = ed_psw_again.getText().toString().trim();
                        if(registerPasswordAgain.equals(registerPassword)){
                            registerWithOkHttp(address,registerName,registerAccount,registerPassword);
                        }else {
                            Toast.makeText(
                                    RegisterActivity.this,
                                    "确认密码错误！",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }else {
                        Toast.makeText(
                                RegisterActivity.this,
                                "验证码错误",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                    break;
                case R.id.tv_checked:
                    bitmap = CodeUtils.getInstance().createBitmap();
                    code = CodeUtils.getInstance().getCode();
                    iv_checked.setImageBitmap(bitmap);
                    Toast.makeText(
                            RegisterActivity.this,
                            "验证码已刷新",
                            Toast.LENGTH_SHORT
                    ).show();
                    break;
                case R.id.iv_registerback:
                    finish();
                    break;
            }
        }
    }

    //实现注册
    private void registerWithOkHttp(String address,String registerName,String registerAccount,String registerPassword) {
        HttpUtils.registerWithOkHttp(address, registerName, registerAccount, registerPassword, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(responseData.equals("true")){
                            Toast.makeText(
                                    RegisterActivity.this,
                                    "注册成功！",
                                    Toast.LENGTH_SHORT
                            ).show();
                            Intent intentRegister = new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intentRegister);
                        }else {
                            Toast.makeText(
                                    RegisterActivity.this,
                                    "注册错误！",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                });
            }
        });
    }

}
