package com.example.qiqi.xianwan.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.game.wuziqi.WiziqiActivity;
import com.example.qiqi.xianwan.game.xiangqi.chess.ChessGame;

public class GameActivity extends AppCompatActivity {
    private Button btnStarGame;
    private Button btnStartXiangqi;
    private CustomOnClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        btnStarGame = findViewById(R.id.btn_wuziqistart);
        btnStartXiangqi = findViewById(R.id.btn_xiangqistart);

        registerListenrs();
    }

    private void registerListenrs() {
        listener = new CustomOnClickListener();
        btnStarGame.setOnClickListener(listener);
        btnStartXiangqi.setOnClickListener(listener);
    }

    class CustomOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_wuziqistart:
                    Intent intent= new Intent();
                    intent.setClass(GameActivity.this, WiziqiActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_xiangqistart:
                    Intent intent2= new Intent();
                    intent2.setClass(GameActivity.this, ChessGame.class);
                    startActivity(intent2);
                    break;
            }
        }
    }
}
