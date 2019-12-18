package com.example.qiqi.xianwan.meadapter.wofabu;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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
    private int i;
    private ListView listView;
    private Button fabu;
    private AlertDialog dialog;
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
            //item点击事件
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //跳转到详情页
                    Intent intent=new Intent();
                    intent.setClass(wofabu.this,DetailActivity.class);
                    intent.putExtra("commodityId",commodities.get(i).getId()+"");
                    intent.putExtra("images",commodities.get(i).getImage());
                    intent.putExtra("introductions",commodities.get(i).getIntroduce());
                    intent.putExtra("price",commodities.get(i).getPrice());
                    intent.putExtra("icon",commodities.get(i).getIcon());
                    intent.putExtra("userName",commodities.get(i).getUserName());
                    intent.putExtra("userAccount",commodities.get(i).getUserAccount());
                    intent.putExtra("showLike",commodities.get(i).getShowLike());
                    startActivity(intent);

                }

            });
               listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                   @Override
                   public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                       setAlertDialog(view,i);
                       dialog.show();

                       return true;
                   }
               });

        }

    };

    private void setAlertDialog(View view,int i) {

        LayoutInflater factory = LayoutInflater.from(getApplicationContext());
// 引入一个外部布局
        View contview = factory.inflate(R.layout.me_updatecommodity, null);
         final EditText edit =  contview
                .findViewById(R.id.edit_dialog);// 找到该外部布局对应的EditText控件
        Button btOK = (Button) contview.findViewById(R.id.btOK_dialog);

        String id=commodities.get(i).getId()+"";
        btOK.setOnClickListener(new View.OnClickListener() {// 设置按钮的点击事件

            @Override
            public void onClick(View v) {
                String price=edit.getText().toString().trim();
                if (edit.getText().toString()!=null){
                    updatePrice(id,price);
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(
                            getApplicationContext(),
                            "请输入价格！！！",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
        dialog = new AlertDialog.Builder(wofabu.this).setView(contview)
                .create();

    }

    private void updatePrice(final String id,final  String price) {
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request;
                FormBody formBody = new FormBody.Builder()
                       .add("id",id)
                        .add("price",price)
                        .build();
                request = new Request.Builder()
                        .url("http://" + hostIp + ":8080/XianWanService/updatePrice")
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

            }
        }
    }
    private void wrapperMessage(String info){
        Message msg = Message.obtain();
        msg.obj = info;
        handler.sendMessage(msg);
    }


}
