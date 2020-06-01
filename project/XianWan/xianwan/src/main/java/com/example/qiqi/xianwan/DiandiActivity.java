package com.example.qiqi.xianwan;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.qiqi.xianwan.Diandi.FaBuActivity;
import com.example.qiqi.xianwan.bean.Diandi;
import com.example.qiqi.xianwan.bean.MyBean;
import com.example.qiqi.xianwan.childadapter.MyAdapter;
import com.truizlop.sectionedrecyclerview.SectionedSpanSizeLookup;

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

public class DiandiActivity extends AppCompatActivity {

    private List<Diandi> list = new ArrayList<>();
    private List<MyBean> beans = new ArrayList<>();
    private Button btn_fabu;
    private Button btn_back;
    private RecyclerView rv;
    private MyAdapter adapter;
    private ImageView imageView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = (String) msg.obj;
            Log.e("DIANDIjson",json);
            try {
                JSONArray jsonArray = new JSONArray(json);
                for(int i = 0; i < jsonArray.length();i++){
                    String objStr = jsonArray.getString(i);
                    String objStr1 = objStr.substring(1,objStr.length() - 1);
                    JSONObject jsonObject = new JSONObject(objStr1);
                    String str2 = jsonObject.getString("url").toString();
                    String []str = str2.substring(2,str2.length()-2).split("\",\"");
                    Log.e("STRSTRSTR1",str[0]);
                    Log.e("STRSTRSTR2",str[1]);
                    Log.e("STRSTRSTR3",str[2]);
                    Log.e("STRSTRSTR4",str[3]);
                    MyBean myBean = new MyBean();
                    myBean.setHeader(jsonObject.getString("time"));
                    for (int j = 0; j < str.length; j++) {
                        Log.e("LENGTH",str.length + "");
                        MyBean.ItemBean itemBean = new MyBean.ItemBean();
                        String path = str[j].replaceAll("\\\\/","/");
                        itemBean.setUrl(path);
                        myBean.getList().add(itemBean);
                    }
                    beans.add(myBean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            rv.setAdapter(adapter);

        }


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diandi);
        getView();
        asyncFormOp();
        initView();


    }

    private void initView() {
        rv = findViewById(R.id.main_rv);
        adapter = new MyAdapter(this, beans);
        rv.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        SectionedSpanSizeLookup lookup = new SectionedSpanSizeLookup(adapter, layoutManager);
        layoutManager.setSpanSizeLookup(lookup);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void getView(){
        rv = findViewById(R.id.main_rv);
        btn_fabu = findViewById(R.id.btn_fabu);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), btn_fabu);
                // 通过XML文件对菜单进行填充
                popupMenu.getMenuInflater().inflate(R.menu.add_dongtai, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // TODO
                        switch (item.getItemId()){
                            case R.id.item1:
                                Intent intent = new Intent(DiandiActivity.this,FaBuActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(),"fabu",Toast.LENGTH_SHORT).show();
                                break;
                            default:
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    public void open(){
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 2);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 2) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                imageView.setImageURI(uri);
            }
        }
    }



    private void asyncFormOp() {
        Log.e("haha","进来了");
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request;

                FormBody formBody = new FormBody.Builder()
                        .add("userAccount", "xixi")
                        .build();
                request = new Request.Builder()
                        .url("http://" + hostIp + ":8080/XianWanService/AlbumForDiandi")
                        .post(formBody)
                        .build();

                Log.e("呵呵呵","发送了");

                //Call
                Call call = okHttpClient.newCall(request);

                Response response;

                try {
                    response = call.execute();
                    String message = response.body().string();
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
