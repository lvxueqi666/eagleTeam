package com.example.qiqi.xianwan.meadapter.xianwanpic;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.entity.Headpic;
import com.example.qiqi.xianwan.initHuanXin.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.qiqi.xianwan.LoginActivity.USERACCOUNT;
import static com.example.qiqi.xianwan.initHuanXin.MyApplication.Headpiclist;

public class UpLoadFileTask extends AsyncTask<String, Void, String> {
    private Context context;
    private String filePath;
    private String hostIp;


    public UpLoadFileTask(Context context, String filePath) {
        this.context = context;
        this.filePath = filePath;
    }

    /**
     * 访问服务器，上传pdf文件，接收响应并返回
     */
    @Override
    protected String doInBackground(String... strings) {
        //1. 创建OKHttpClient对象
        OkHttpClient client = new OkHttpClient();
        //2. 创建Request对象
        //2.1 得到对应的mime类型
        MediaType type = MediaType.parse("images/jpg");
        //2.2 创建RequestBody的对象
        File file = new File(filePath);
        RequestBody body = RequestBody.create(
                file,
                type
        );
        //2.3 创建请求对象
        Request request = new Request.Builder()
                //设置请求头发送用户识别id，用于服务器端创建唯一图片名
                .header("userId",USERACCOUNT)
                .url(strings[0])
                .post(body)
                .build();
        //3. 创建Call对象
        Log.i("zay","adad");
        Call call = client.newCall(request);
        //4. 发起请求并接收响应
        Response response = null;
        try {
            response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
//        super.onPostExecute(s);

        headpic();
        MyApplication query = new MyApplication();
        query.setEaseUIProviders();
        Toast.makeText(
                context,
                s,
                Toast.LENGTH_SHORT
        ).show();
    }
    public void headpic(){
        hostIp = context.getResources().getString(R.string.hostStr);
        new Thread(){
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://"+ hostIp +":8080/XianWanService/Android4Headpic")
                        .build();
                Call call = okHttpClient.newCall(request);
                try {
                    Headpiclist.clear();
                    Response response = call.execute();
                    String message = response.body().string();
                    Log.i("aaa","a"+message);
                    if(message != null){
                        JSONArray jsonArray = new JSONArray(message);
                        for(int i = 0; i < jsonArray.length();i++) {
                            String objStr = jsonArray.getString(i);
                            JSONObject jsonObject = new JSONObject(objStr);
                            Headpic pic = new Headpic();
                            pic.setUserAccount(jsonObject.getString("userAccount"));
                            pic.setAddress(jsonObject.getString("address"));
                            Headpiclist.add(pic);
                        }
                    }
                    Log.i("picture","头像遍历完毕"+Headpiclist.get(1).getAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}

