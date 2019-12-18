package com.example.qiqi.xianwan.meadapter.wofabu;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qiqi.xianwan.DetailActivity;
import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.entity.Commodity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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

public class wofabu extends AppCompatActivity
{
    private ListView listView;
    private Button fabu;
    private Button btn_wofabu_back;
    private SmartRefreshLayout wofabu_srl;
    private CustomeClickListener listener;

    private com.example.qiqi.xianwan.meadapter.wofabu.fabuAdapter fabuAdapter;
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


            fabuAdapter = new fabuAdapter(getApplicationContext(),
                    commodities,
                    R.layout.me_fabu_item);
            listView.setAdapter(fabuAdapter);

            //item点击事
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Commodity commodity = commodities.get(i);
                    //跳转到详情页
                    Intent intent=new Intent(getApplicationContext(), DetailActivity.class);
                    intent.putExtra("commodityId",commodity.getId()+"");
                    intent.putExtra("images",commodity.getImage());
                    intent.putExtra("introductions",commodity.getIntroduce());
                    intent.putExtra("price",commodity.getPrice());
                    intent.putExtra("icon",commodity.getIcon());
                    intent.putExtra("userName",commodity.getUserName());
                    intent.putExtra("userAccount",commodity.getUserAccount());
                    intent.putExtra("showLike",commodity.getShowLike());
                    startActivity(intent);
                }
            });

        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.me_wofabu);
        if(USERACCOUNT!=null) {
            getViews();
            asyncFormOp();
            regListener();
        }

    }

    private void regListener() {
        listener = new CustomeClickListener();
        fabu.setOnClickListener(listener);
        btn_wofabu_back.setOnClickListener(listener);
        wofabu_srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                asyncFormOp();
                wofabu_srl.finishRefresh();
                Toast.makeText(
                        getApplicationContext(),
                        "刷新完成",
                        Toast.LENGTH_SHORT
                ).show();

            }

        });
    }




    private void asyncFormOp(){
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Log.e("shuaxin", "shuaxin");
                Request request;
                //Request(Post、FormBody）
                FormBody formBody = new FormBody.Builder()
                        .add("userAccount", USERACCOUNT)
                        .build();
                request = new Request.Builder()
                        .url("http://"+hostIp+":8080/XianWanService/fabuforAndroid")
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


    private void getViews() {
        fabu=findViewById(R.id.fabu);
        btn_wofabu_back=findViewById(R.id.btn_wofabu_back);
        wofabu_srl=findViewById(R.id.wofabu_srl);
        listView=findViewById(R.id.fabu_listview);
    }

    private class CustomeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_wofabu_back:
                    finish();
                    break;

                case R.id.fabu:
                    Intent intent3=new Intent();
                    intent3.setClass(wofabu.this,content_fabu.class);
                    startActivity(intent3);
                    break;

            }
        }
    }
    private void wrapperMessage(String info){
        Message msg = Message.obtain();
        msg.obj = info;
        handler.sendMessage(msg);
    }


}
