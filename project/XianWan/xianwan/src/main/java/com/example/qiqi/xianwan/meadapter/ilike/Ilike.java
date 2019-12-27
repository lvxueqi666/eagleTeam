
package com.example.qiqi.xianwan.meadapter.ilike;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qiqi.xianwan.DetailActivity;
import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.entity.Commodity;

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

public class Ilike extends AppCompatActivity {
    private ListView listView;
    private ImageView ilike_back;
    private CustomeClickListener listener;
    private collectAdapter collectAdapter;
    List<Commodity> commodities = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json =  (String)msg.obj;
            if(commodities.size() > 0){
                commodities.removeAll(commodities);
            }
            try {
                JSONArray jsonArray = new JSONArray(json);
                for(int i = 0; i < jsonArray.length();i++){
                    String objStr = jsonArray.getString(i);
                    JSONObject jsonObject = new JSONObject(objStr);

                    Commodity commodity = new Commodity(
                            jsonObject.getLong("id"),
                            jsonObject.getString("image"),
                            jsonObject.getString("introduce"),
                            jsonObject.getString("price"),
                            jsonObject.getString("tag"),
                            jsonObject.getString("userAccount"),
                            jsonObject.getString("icon"),
                            jsonObject.getString("userName"),
                            jsonObject.getString("attr"),
                            jsonObject.getString("showLike")
                    );
                    commodities.add(commodity);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            collectAdapter = new collectAdapter(getApplicationContext(),
                    commodities,
                    R.layout.me_collect_item);
            listView.setAdapter(collectAdapter);

            //item点击事件
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //跳转到详情页
                    Intent intent=new Intent();
                    intent.putExtra("commodityId",commodities.get(i).getId()+"");
                    intent.putExtra("images",commodities.get(i).getImage());
                    intent.putExtra("introductions",commodities.get(i).getIntroduce());
                    intent.putExtra("price",commodities.get(i).getPrice());
                    intent.putExtra("icon",commodities.get(i).getIcon());
                    intent.putExtra("userName",commodities.get(i).getUserName());
                    intent.putExtra("userAccount",commodities.get(i).getUserAccount());
                    intent.putExtra("showLike",commodities.get(i).getShowLike());
                    intent.setClass(Ilike.this,DetailActivity.class);
                    startActivity(intent);

                }
            });
        }

    };




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.me_ilike);
        asyncFormOp();
        getViews();
        regListener();

    }

    private void asyncFormOp() {
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request;

                FormBody formBody = new FormBody.Builder()
                        .add("userAccount", USERACCOUNT)
                        .build();
                request = new Request.Builder()
                        .url("http://"+hostIp+":8080/XianWanService/collectForAndroid")
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

    private void wrapperMessage(String info) {
        Message msg = Message.obtain();
        msg.obj = info;
        handler.sendMessage(msg);
    }

    private void getViews() {
        ilike_back = findViewById(R.id.ilike_back);
        listView=findViewById(R.id.collect_listview);
    }


    private void regListener() {
        {
            listener = new CustomeClickListener();
            ilike_back.setOnClickListener(listener);
        }


    }

    private class CustomeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ilike_back:
                    finish();
                    break;
            }
        }
    }
}


