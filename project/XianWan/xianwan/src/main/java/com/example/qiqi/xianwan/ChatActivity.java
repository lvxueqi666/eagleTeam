package com.example.qiqi.xianwan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hyphenate.easeui.ui.EaseChatFragment;

public class ChatActivity extends AppCompatActivity {

    private EaseChatFragment chatFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //use EaseChatFratFragment
        chatFragment = new EaseChatFragment();
        //pass parameters to chat fragment
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.contentFl, chatFragment).commit();
    }
}
