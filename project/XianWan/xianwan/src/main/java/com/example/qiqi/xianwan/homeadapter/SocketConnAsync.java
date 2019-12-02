package com.example.qiqi.xianwan.homeadapter;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SocketConnAsync extends AsyncTask<String, Void, String> {

    private String json;

    public interface AsyncResponse {
        void onDataReceivedSuccess(String string);
        void onDataReceivedFailed();
    }

    public AsyncResponse asyncResponse;
    public void setOnAsyncResponse(AsyncResponse asyncResponse)
    {
        this.asyncResponse = asyncResponse;
    }


    @Override
    protected String doInBackground(String... strings) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Log.e("jinlaile","haha");
        //Request(Post、FormBody）
        FormBody formBody = new FormBody.Builder()
                .add("name", "lww")
                .add("pwd", "1233")
                .build();
        Request request = new Request.Builder()
                .url("http://10.7.89.121:8080/XianWan/HomeForAndroid")
                .post(formBody)
                .build();
        //Call
        Call call = okHttpClient.newCall(request);

        Response response = null;
        //异步请求
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
        //super.onPostExecute(s);
        //Log.e("ssss:",json);
        if(s != null){
            Log.e("keyichuanchuqu","keyi");
            Log.e("keyichuanchuqu",s);
            asyncResponse.onDataReceivedSuccess(s);
        }else{
            asyncResponse.onDataReceivedFailed();
            Log.e("meijinlai","bukeyi");
        }
    }
}
