package com.example.qiqi.xianwan.meadapter.person_content;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.meadapter.MessageEvent;
import com.example.qiqi.xianwan.meadapter.xianwanpic.headpicoption;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Et_jianjie extends AppCompatActivity {
    private ImageView jianjie_back;
    private CustomeClickListener listener;
    private Button bn_jianjie;
private EditText edit_jianjie;
    MessageEvent mObjEvent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.me_et_jianjie);
        getViews();
        regListener();
        EventBus.getDefault().register(this);
        //处理相关数据
        if (null != mObjEvent) {
            edit_jianjie.setText(mObjEvent.getMessage());
        }
    }

    private void getViews() {
        jianjie_back = findViewById(R.id.jianjie_back);
        bn_jianjie=findViewById(R.id.bn_jianjie);
        edit_jianjie=findViewById(R.id.edit_jianjie);
    }
//重写物理返回按钮
@Override
public void onBackPressed() {
    Intent intent = new Intent(Et_jianjie.this,headpicoption.class);
    intent.putExtra("EXTRA_MESSAGE","");
    setResult(1, intent);
    finish();
}

    private void regListener() {
        {
            listener = new CustomeClickListener();
            jianjie_back.setOnClickListener(listener);
            bn_jianjie.setOnClickListener(listener);
        }


    }

    private class CustomeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.jianjie_back:
                    Intent intent = new Intent(Et_jianjie.this,headpicoption.class);
                    intent.putExtra("EXTRA_MESSAGE","");
                    setResult(1, intent);
                    finish();
                    break;
                case R.id.bn_jianjie:
                   sendMessage(view);
                    break;

            }
        }
    }
    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void onMoonEvent(MessageEvent objEvent) {
        if (null != objEvent) {
            //赋值
            this.mObjEvent = objEvent;
        }
    }


    @Override
    protected void onDestroy() {
        //移除全部粘性事件
        EventBus.getDefault().removeAllStickyEvents();
        //解绑事件
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void sendMessage(View view) {
        Intent intent = new Intent(this,headpicoption.class);
        //声明一个编辑框和布局文件中id为edit_message的编辑框链接起来。
        EditText editText = (EditText) findViewById(R.id.edit_jianjie);
        //把编辑框获取的文本赋值给String类型的message
        String message = editText.getText().toString();
        //给message起一个名字，并传给另一个activity
        intent.putExtra("EXTRA_MESSAGE",message);
        setResult(1, intent);
        //启动意图
        finish();

    }
}