package com.example.qiqi.xianwan.meadapter.womaichu;

import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.qiqi.xianwan.R;

public class womaichu extends AppCompatActivity
{
    private Button maichu_back;
    private CustomeClickListener listener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.me_womaichu);
        getViews();
        regListener();

    }

    private void getViews() {
        maichu_back = findViewById(R.id.maichu_back);
    }


    private void regListener() {
        {
            listener = new CustomeClickListener();
            maichu_back.setOnClickListener(listener);
        }


    }

    private class CustomeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.maichu_back:
                    finish();
                    break;
            }
        }
    }
}