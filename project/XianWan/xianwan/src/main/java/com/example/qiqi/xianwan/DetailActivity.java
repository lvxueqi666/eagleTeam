package com.example.qiqi.xianwan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.qiqi.xianwan.entity.Headpic;
import com.example.qiqi.xianwan.initHuanXin.MyApplication;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.qiqi.xianwan.LoginActivity.USERACCOUNT;
import static com.example.qiqi.xianwan.LoginActivity.USERNAME;
import static com.example.qiqi.xianwan.initHuanXin.MyApplication.Headpiclist;

public class DetailActivity extends AppCompatActivity {

    private ImageView icon;
    private TextView name;
    private TextView detailPrice;
    private TextView introdu;
    private ImageView showLike;
    private TextView dianzancount;
    private ImageView shoucang;
    private Button want;
    private ImageView back;
    private LinearLayout linearLayout;
    private Handler handler;
    private List<String> detailImage;


    private String commodityId;
    private String userAccount;

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

        userAccount = intent.getStringExtra("userAccount");
        showLikes = intent.getStringExtra("showLike");

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String json = (String) msg.obj;
                Log.e("jsonjsonjson",json);
                try {
                    JSONArray jsonArray = new JSONArray(json);
                    for(int i = 0; i < jsonArray.length();i++){
                        String objStr = jsonArray.getString(i);
                        detailImage.add(objStr);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for(int i = 0; i < detailImage.size(); i++) {
                    ImageView imageView = new ImageView(getApplicationContext());
                    imageView.setPadding(30,30,30,30);
                    Glide.with(getApplicationContext()).load(detailImage.get(i)).into(imageView);
                    linearLayout.addView(imageView);
                }
            }
        };

        getImages(userAccount,images);

        Glide.with(this).load(icons).into(icon);
        name.setText(userName);
        detailPrice.setText("￥" + price);
        introdu.setText(introductions);
        dianzancount.setText(showLikes);
        adjustShowLikeStatus(userAccount,commodityId,"adjust");
        adjustCollectionStatus(userAccount,commodityId,"adjust");

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
                    modifyShowLikeCount(userAccount,commodityId,"add","add");
                }else{
                    showLike.setImageResource(R.drawable.dianzan);
                    int count = Integer.parseInt(showLikes);
                    count--;
                    showLikes = count + "";
                    dianzancount.setText(count+"");
                    modifyShowLikeCount(userAccount,commodityId,"minus","add");
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
                    addOrCancelCollection(userAccount,commodityId,"add");
                }else{
                    shoucang.setImageResource(R.drawable.shoucang);
                    Toast.makeText(DetailActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                    addOrCancelCollection(userAccount,commodityId,"cancel");
                }
            }
        });

        //点击我想要后进入私聊界面

        want.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(USERACCOUNT == null || USERNAME == null){
                    Intent intent1 = new Intent(getApplicationContext(),LoginActivity.class);
                    Toast.makeText(DetailActivity.this, "您还未登录", Toast.LENGTH_SHORT).show();
                    startActivity(intent1);
                }else{
                    startChat(userAccount);
                }
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
        showLike = findViewById(R.id.dianzan);
        shoucang = findViewById(R.id.shoucang);
        dianzancount = findViewById(R.id.likeCount);
        want = findViewById(R.id.detail_want);
        back = findViewById(R.id.detail_back);
        linearLayout = findViewById(R.id.linearContent);
        detailImage = new ArrayList<>();
    }

    /**
     * 跳转至与卖家的聊天界面
     */
    private void startChat(String userId){
        String abc = "111";
        Intent intent = new Intent(DetailActivity.this,ChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_USER_ID,userId);
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
        startActivity(intent);
    }

    private void modifyShowLikeCount(final String userAccount, final String commodityId,final String addOrCancel,final String operate){
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request;
                FormBody formBody = new FormBody.Builder()
                        .add("commodityId", commodityId)
                        .add("userAccount",userAccount)
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

    private void addOrCancelCollection(final String userAccount, final String commodityId, final String operate) {
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request;
                FormBody formBody = new FormBody.Builder()
                        .add("userAccount", userAccount)
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

    private void adjustCollectionStatus(final String userAccount, final String commodityId, final String operate){
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request;
                FormBody formBody = new FormBody.Builder()
                        .add("userAccount", userAccount)
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

    private void adjustShowLikeStatus(final String userAccount, final String commodityId, final String operate){
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request;
                FormBody formBody = new FormBody.Builder()
                        .add("userAccount", userAccount)
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

    private void getImages(String userAccount,String  firstUrl) {
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request;

                FormBody formBody = new FormBody.Builder()
                        .add("userAccount", userAccount)
                        .add("firstUrl",firstUrl)
                        .build();
                request = new Request.Builder()
                        .url("http://" + hostIp + ":8080/XianWanService/DetailImageOperate")
                        .post(formBody)
                        .build();

                //Call
                Call call = okHttpClient.newCall(request);

                Response response;

                try {
                    response = call.execute();
                    String message =  response.body().string();
                    wrapperMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void wrapperMessage(String info){
        Message msg = Message.obtain();
        msg.obj = info;
        handler.sendMessage(msg);
    }
}
