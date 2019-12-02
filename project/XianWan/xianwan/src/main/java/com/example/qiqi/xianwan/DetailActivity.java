package com.example.qiqi.xianwan;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class DetailActivity extends AppCompatActivity {

    private ImageView icon;
    private TextView name;
    private TextView detailPrice;
    private TextView introdu;
    private ImageView img1;
    private ImageView img2;
    private ImageView showLike;
    private TextView dianzancount;
    private ImageView shoucang;
    private Button want;


    private String commodityId;
    private String userId;

    private String showLikes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getView();

        Intent intent = getIntent();
        commodityId = intent.getStringExtra("commodityId");
        String images = intent.getStringExtra("images");
        String introductions = intent.getStringExtra("introductions");
        String price = intent.getStringExtra("price");
        String icons = intent.getStringExtra("icon");
        String userName = intent.getStringExtra("userName");
        userId = intent.getStringExtra("userId");
        showLikes = intent.getStringExtra("showLike");

        Glide.with(this).load(icons).into(icon);
        name.setText(userName);
        detailPrice.setText("￥" + price);
        introdu.setText(introductions);
        Glide.with(this).load(images).into(img1);
        Glide.with(this).load(images).into(img2);
        dianzancount.setText(showLikes);

        //点赞操作，需要在数据库中进行修改

        showLike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(showLike.getDrawable().getCurrent().getConstantState().equals(getResources().getDrawable(R.drawable.dianzan).getConstantState())){
                    showLike.setImageResource(R.drawable.dianzan1);
                    int count = Integer.parseInt(showLikes);
                    count++;
                    showLikes = count + "";
                    dianzancount.setText(count+"");
                    modifyShowLikeCount(commodityId,"add");
                }else{
                    showLike.setImageResource(R.drawable.dianzan);
                    int count = Integer.parseInt(showLikes);
                    count--;
                    showLikes = count + "";
                    dianzancount.setText(count+"");
                    modifyShowLikeCount(commodityId,"minus");
                }
            }
        });

        //收藏操作，需要在数据库中进行修改

        shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shoucang.getDrawable().getCurrent().getConstantState().equals(getResources().getDrawable(R.drawable.shoucang).getConstantState())){
                    shoucang.setImageResource(R.drawable.shoucang1);
                    Toast.makeText(DetailActivity.this, "已收藏", Toast.LENGTH_SHORT).show();
                }else{
                    shoucang.setImageResource(R.drawable.shoucang);
                    Toast.makeText(DetailActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getView() {
        icon = findViewById(R.id.detail_icon);
        name = findViewById(R.id.detail_name);
        detailPrice = findViewById(R.id.detail_price);
        introdu = findViewById(R.id.detail_introduce);
        img1 = findViewById(R.id.detail_img1);
        img2 = findViewById(R.id.detail_img2);
        showLike = findViewById(R.id.dianzan);
        shoucang = findViewById(R.id.shoucang);
        dianzancount = findViewById(R.id.likeCount);
        want = findViewById(R.id.detail_want);
    }

    private void modifyShowLikeCount(final String id, final String operate){
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request;
                FormBody formBody = new FormBody.Builder()
                        .add("commodityId", id)
                        .add("operate",operate)
                        .build();
                request = new Request.Builder()
                        .url("http://" + hostIp + ":8080/XianWan/ShowLikeOperate")
                        .post(formBody)
                        .build();
                Call call = okHttpClient.newCall(request);
                try {
                    call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
