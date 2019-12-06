package com.example.qiqi.xianwan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import java.util.List;

public class ConversationActivity extends AppCompatActivity {


    private EaseConversationListFragment easeConversationListFragment;
    private EMMessageListener emMessageListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        easeConversationListFragment = new EaseConversationListFragment();
        easeConversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(ConversationActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()));
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.contentFl, easeConversationListFragment).commit();

        emMessageListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                Log.i("wtw","成功接受到消息1");
                easeConversationListFragment.refresh();
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                Log.i("wtw","成功接受到消息1");
                easeConversationListFragment.refresh();
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> messages) {
            }

            @Override
            public void onMessageRecalled(List<EMMessage> messages) {
                easeConversationListFragment.refresh();
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        EMClient.getInstance().chatManager().addMessageListener(emMessageListener);
        Log.i("wtw","注册消息监听器1");
    }

    @Override
    protected void onPause() {
        super.onPause();
        EMClient.getInstance().chatManager().removeMessageListener(emMessageListener);
        Log.i("wtw","取消注册消息监听器1");
    }
}
