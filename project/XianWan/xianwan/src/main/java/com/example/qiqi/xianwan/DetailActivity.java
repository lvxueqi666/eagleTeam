package com.example.qiqi.xianwan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.EaseConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
    private ImageView back;


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
        adjustShowLikeStatus("111","222","adjust");
        adjustCollectionStatus("111","222","adjust");

        //点赞操作，需要在数据库中进行修改

        showLike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(showLike.getDrawable().getCurrent().getConstantState().equals(getResources().getDrawable(R.drawable.dianzan).getConstantState())){
                    showLike.setImageResource(R.drawable.dianzan1);
                    int count = Integer.parseInt(showLikes);
                    count++;
                    showLikes = count + "";
                    dianzancount.setText(count + "");
                    modifyShowLikeCount("111","222","add","add");
                }else{
                    showLike.setImageResource(R.drawable.dianzan);
                    int count = Integer.parseInt(showLikes);
                    count--;
                    showLikes = count + "";
                    dianzancount.setText(count+"");
                    modifyShowLikeCount("111","222","minus","add");
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
                    addOrCancelCollection("111","222","add");
                }else{
                    shoucang.setImageResource(R.drawable.shoucang);
                    Toast.makeText(DetailActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                    addOrCancelCollection("111","222","cancel");
                }
            }
        });

        //点击我想要后进入私聊界面

        want.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startChat(userId);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailActivity.this.finish();
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
        back = findViewById(R.id.detail_back);
    }

    /**
     * 跳转至与卖家的聊天界面
     */
    private void startChat(String userId){
        Intent intent = new Intent(DetailActivity.this,ChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_USER_ID,userId);
        startActivity(intent);
    }

    private void modifyShowLikeCount(final String userId, final String commodityId,final String addOrCancel,final String operate){
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request;
                FormBody formBody = new FormBody.Builder()
                        .add("commodityId", commodityId)
                        .add("currentId",userId)
                        .add("addOrCancel",addOrCancel)
                        .add("operate",operate)
                        .build();
                request = new Request.Builder()
                        .url("http://" + hostIp + ":8080/XianWanService/ShowLikeOperate")
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

    private void addOrCancelCollection(final String userId, final String commodityId, final String operate) {
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request;
                FormBody formBody = new FormBody.Builder()
                        .add("currentId", userId)
                        .add("commodityId",commodityId)
                        .add("operate",operate)
                        .build();
                request = new Request.Builder()
                        .url("http://" + hostIp + ":8080/XianWanService/CollectOperate")
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

    private void adjustCollectionStatus(final String userId, final String commodityId, final String operate){
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request;
                FormBody formBody = new FormBody.Builder()
                        .add("currentId", userId)
                        .add("commodityId",commodityId)
                        .add("operate",operate)
                        .build();

                request = new Request.Builder()
                        .url("http://" + hostIp + ":8080/XianWanService/CollectOperate")
                        .post(formBody)
                        .build();

                Call call = okHttpClient.newCall(request);

                Response response;

                try {
                    response = call.execute();
                    String message = response.body().string();
                    if(message.equals("exist")){
                        shoucang.setImageResource(R.drawable.shoucang1);
                    }else{
                        shoucang.setImageResource(R.drawable.shoucang);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void adjustShowLikeStatus(final String userId, final String commodityId, final String operate){
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request;
                FormBody formBody = new FormBody.Builder()
                        .add("currentId", userId)
                        .add("commodityId",commodityId)
                        .add("operate",operate)
                        .build();

                request = new Request.Builder()
                        .url("http://" + hostIp + ":8080/XianWanService/ShowLikeOperate")
                        .post(formBody)
                        .build();

                Call call = okHttpClient.newCall(request);

                Response response;

                try {
                    response = call.execute();
                    String message = response.body().string();
                    if(message.equals("exist")){
                        showLike.setImageResource(R.drawable.dianzan1);
                    }else{
                        showLike.setImageResource(R.drawable.dianzan);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
