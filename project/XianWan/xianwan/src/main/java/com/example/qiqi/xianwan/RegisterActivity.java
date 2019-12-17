package com.example.qiqi.xianwan;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qiqi.xianwan.utils.CodeUtils;
import com.example.qiqi.xianwan.utils.HttpUtils;
import com.example.qiqi.xianwan.utils.ToastUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//import static com.example.qiqi.xianwan.LoginActivity.i;

public class RegisterActivity extends AppCompatActivity {
    private EditText ed_registerName;
    private EditText ed_registerAccount;
    private EditText ed_registerPassword;
    private EditText ed_psw_again;
    private EditText ed_checked;
    private ImageView iv_checked;
    private ImageView iv_registerBack;
    private ImageView iv_registerName;
    private ImageView iv_registerAccount;
    private ImageView iv_registerPassword;
    private ImageView iv_psw_again;
    private Button btn_register;
    private TextView tv_checked;
    private CustomOnClickListenrer listener;
    private Bitmap bitmap;
    private String code;
    private String checked;
    private String registerName;
    private String registerAccount;
    private String registerPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getViews();

        registerAccount = ed_registerAccount.getText().toString().trim();
        registerPassword = ed_registerPassword.getText().toString().trim();

        //编辑用户名文本监听
        ed_registerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if((start+count) != 0){
                    iv_registerName.setImageResource(R.drawable.right);
                }else {
                    iv_registerName.setImageResource(R.drawable.warning);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().equals("")) {
                    iv_registerName.setImageResource(R.drawable.warning);
                }else if(!s.toString().trim().equals("")){
                    registerName = s.toString().trim();
                }
            }
        });

        //编辑账户文本监听
        ed_registerAccount.addTextChangedListener(new TextWatcher() {
            private Resources resources = getResources();
            private final String hostIp = resources.getString(R.string.hostStr);
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length() >= 6 && s.toString().trim().length() <= 12){
                    registerAccountWithOkHttp("http://"+hostIp+":8080/XianWanService/AccountServlet",s.toString().trim());
                }else {
                    ToastUtils.showToast(RegisterActivity.this,"账户长度不能低于6位多于12位",Toast.LENGTH_SHORT);
                    iv_registerAccount.setImageResource(R.drawable.warning);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().equals("")) {
                    iv_registerAccount.setImageResource(R.drawable.warning);
                }else if(!s.toString().trim().equals("")){
                    registerAccount = s.toString().trim();
                }
            }
        });

        //编辑密码文本监听
        ed_registerPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if((start+count) >= 6 && (start+count) <= 15){
                    iv_registerPassword.setImageResource(R.drawable.right);
                }else {
                    iv_registerPassword.setImageResource(R.drawable.warning);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().equals("")) {
                    iv_registerPassword.setImageResource(R.drawable.warning);
                }else if(!s.toString().trim().equals("")){
                    registerPassword = s.toString().trim();
                }
            }
        });

        //编辑密码确认文本监听
        ed_psw_again.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(registerPassword.equals(s.toString().trim())){
                    iv_psw_again.setImageResource(R.drawable.right);
                }else {
                    ToastUtils.showToast(RegisterActivity.this,"确认密码与原密码不匹配",Toast.LENGTH_SHORT);
                    iv_psw_again.setImageResource(R.drawable.warning);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        registerListners();

        //获取工具类生成的图片验证码对象
        bitmap = CodeUtils.getInstance().createBitmap();
        //获取当前图片的验证码的对应内容，用于校验
        code = CodeUtils.getInstance().getCode();
        iv_checked.setImageBitmap(bitmap);
    }

    //获取监听
    private void registerListners() {
        listener = new CustomOnClickListenrer();
        btn_register.setOnClickListener(listener);
        tv_checked.setOnClickListener(listener);
        iv_registerBack.setOnClickListener(listener);

    }

    //获取id
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
        iv_registerName = findViewById(R.id.iv_registerName);
        iv_registerAccount = findViewById(R.id.iv_registerAccount);
        iv_registerPassword = findViewById(R.id.iv_registerPassword);
        iv_psw_again = findViewById(R.id.iv_psw_again);
    }

    class CustomOnClickListenrer implements View.OnClickListener{
        private Resources resources = getResources();
        private final String hostIp = resources.getString(R.string.hostStr);
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_register:
                    checked = ed_checked.getText().toString().trim().toLowerCase();
                    code = code.toString().trim().toLowerCase();
                    if(!checked.equals(code)){
                        String address = "http://"+hostIp+":8080/XianWanService/RegisterServlet";
                        Register(registerAccount,registerPassword);
                        registerWithOkHttp(address,registerAccount,registerPassword,registerName);
                    }else {
                        ToastUtils.showToast(RegisterActivity.this,"验证码错误",Toast.LENGTH_SHORT);
                    }
                    RegisterActivity.this.finish();
                    break;
                case R.id.tv_checked:
                    bitmap = CodeUtils.getInstance().createBitmap();
                    code = CodeUtils.getInstance().getCode();
                    iv_checked.setImageBitmap(bitmap);
                    ToastUtils.showToast(RegisterActivity.this,"验证码已刷新",Toast.LENGTH_SHORT);
                    break;
                case R.id.iv_registerback:
                    finish();
                    break;
            }
        }
    }

    //注册查重
    private void registerAccountWithOkHttp(String address,String account) {
        HttpUtils.registerAccounWithOkHttp(address, account, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(responseData.equals("true")) {
                            ToastUtils.showToast(RegisterActivity.this, "用户已存在！", Toast.LENGTH_SHORT);
                            iv_registerAccount.setImageResource(R.drawable.warning);
                        }else{
                            iv_registerAccount.setImageResource(R.drawable.right);
                        }
                    }
                });
            }
        });
    }

    //实现注册
    private void registerWithOkHttp(String address,String registerAccount,String registerPassword,String registerName) {
        HttpUtils.registerWithOkHttp(address, registerAccount, registerPassword,registerName, new Callback() {
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
                            ToastUtils.showToast(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT);
                            Intent intentRegister = new Intent(RegisterActivity.this,TagsActivity.class);
                            startActivity(intentRegister);
                        }else {
                            ToastUtils.showToast(RegisterActivity.this,"注册错误！",Toast.LENGTH_SHORT);
                        }
                    }
                });
            }
        });
    }
    //环信注册；
    public  void Register(String count,String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(count,password);
                    Log.i("sss:","注册成功");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.i("sss:","注册失败"+e.getMessage()+"--"+e.getErrorCode());
                }
            }
        }).start();
    }

}

