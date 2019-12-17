package com.example.qiqi.xianwan.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.qiqi.xianwan.LoginActivity;
import com.example.qiqi.xianwan.R;

import com.example.qiqi.xianwan.entity.Commodity;
import com.example.qiqi.xianwan.meadapter.ilike.Ilike;
import com.example.qiqi.xianwan.meadapter.wofabu.wofabu;
import com.example.qiqi.xianwan.meadapter.womaichu.womaichu;
import com.example.qiqi.xianwan.meadapter.womaidao.womaidao;
import com.example.qiqi.xianwan.meadapter.xianwanpic.headpicoption;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.qiqi.xianwan.LoginActivity.USERACCOUNT;
import static com.example.qiqi.xianwan.LoginActivity.USERNAME;

public class MeFragment extends Fragment {
    private boolean flag = false;
    private SmartRefreshLayout srl;
    private ImageView fabu;
    private ImageView maichu;
    private ImageView maidao;
    private ImageView head_img_zay;
    private RelativeLayout rl_fabu;
    private RelativeLayout ilike;
    private LinearLayout background;
    private TextView ilike_size;
    private String icon;
    List<Commodity> commodities = new ArrayList<>();
    private TextView fabu_size;
    private OkHttpClient okHttpClient = new OkHttpClient();
    private String hostStr = "10.7.89.139";
    private String email;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = (String) msg.obj;
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jsonArray!=null){
                fabu_size.setText(jsonArray.length() + "");}
            else {
                fabu_size.setText(0+"");
            }
        }

    };
    private Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = (String) msg.obj;
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
           if (jsonArray!=null){
                ilike_size.setText(jsonArray.length() + "");}
                else {
                ilike_size.setText(0+"");
           }

        }

    };
    private Handler handler3 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    RequestOptions requestOptions = new RequestOptions()
                            .placeholder(R.drawable.add)
                            .error(R.mipmap.sss)
                            .fallback(R.drawable.backgroud)
                            .override(400)
                            .circleCrop()
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE);
                    Log.i("zaybbbbb",""+getContext().getFilesDir().getAbsolutePath()+"/UserPic.jpg");
                    if(new File(getContext().getFilesDir().getAbsolutePath()+"/UserPic.jpg").exists()){
                        Glide.with(getContext())
                                .load(getContext().getFilesDir().getAbsolutePath()+"/UserPic.jpg")
                                .apply(requestOptions)
                                .into(head_img_zay);
                        Log.i("zayabab","错1");
                    }
                    else {
                        Glide.with(getContext())
                                .load(R.mipmap.sss)
                                .apply(requestOptions)
                                .into(head_img_zay);
                        Log.i("zayabab","错2");
                    }
                    //head_img_zay.setImageResource(R.mipmap.back);
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.me_fragment_layout, container, false);


        srl = view.findViewById(R.id.srl);
        fabu = view.findViewById(R.id.iv_fabu);
        maichu = view.findViewById(R.id.iv_maichu);
        maidao = view.findViewById(R.id.iv_maidao);
        head_img_zay = view.findViewById(R.id.head_img_zay);
        rl_fabu = view.findViewById(R.id.rl_fabu);
        ilike = view.findViewById(R.id.ilike);
        background = view.findViewById(R.id.background);
        fabu_size = view.findViewById(R.id.fabu_size);
        ilike_size = view.findViewById(R.id.ilike_size);

       if (USERNAME!=null) {
           Log.i("zay","userId:"+USERACCOUNT);
           asyncFormOp();
           asyncFormOpilike();
           InitHeadPic();
       }
        else {
           Log.i("zay","123431");
           InitHeadPic2();
       }




        head_img_zay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (USERNAME!=null&&USERACCOUNT!=null) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), headpicoption.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        //我发布的点击事件
        rl_fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (USERNAME!=null&&USERACCOUNT!=null) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), wofabu.class);

                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });


        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (USERNAME!=null&&USERACCOUNT!=null) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), headpicoption.class);
                    startActivity(intent);
                }  else{
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        //我收藏的点击事件
        ilike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (USERNAME!=null&&USERACCOUNT!=null) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), Ilike.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        srl.setReboundDuration(1000);
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (USERNAME!=null&&USERACCOUNT!=null) {
                    refreshData();
                }
                else{
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                srl.finishRefresh();
                /*
                 * shuaxin
                 * */
            }
        });
            return view;

        }

    private void InitHeadPic2() {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.add)
                .error(R.drawable.back)
                .fallback(R.drawable.backgroud)
                .override(400)
                .circleCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(getContext())
                .load(R.mipmap.sss)
                .apply(requestOptions)
                .into(head_img_zay);

    }


    private void asyncFormOp () {
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
                            .url("http://" + hostIp + ":8080/XianWanService/fabuforAndroid")
                            .post(formBody)
                            .build();

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
        private void asyncFormOpilike () {
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
                            .url("http://" + hostIp + ":8080/XianWanService/collectForAndroid")
                            .post(formBody)
                            .build();

                    //Call
                    Call call = okHttpClient.newCall(request);
                    Response response;
                    try {
                        response = call.execute();
                        String message = response.body().string();
                        wrapperMessage2(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        private void wrapperMessage (String info){

            Message msg = Message.obtain();
            msg.obj = info;
            handler.sendMessage(msg);
        }
        private void wrapperMessage2 (String message){
            Message msg = Message.obtain();
            msg.obj = message;
            handler2.sendMessage(msg);
        }
    private void InitHeadPic() {
        new Thread() {
            @Override
            public void run() {
                if (USERACCOUNT!=null) {
                    dowm();

                    Message message = new Message();
                    message.what = 1;
                    handler3.sendMessage(message);
                }
            }
        }.start();


        }


        private void refreshData () {
        if (USERACCOUNT!=null) {
            InitHeadPic();
            //刷新我发布的数量
            asyncFormOp();
            //刷新我的收藏数量
            asyncFormOpilike();
        }
        }

        public void dowm () {
            Resources resources = getResources();
            final String hostIp = resources.getString(R.string.hostStr);
            File file = new File(getContext().getFilesDir().getAbsolutePath() + "/UserPic.jpg");
            if (file.exists()) {
                file.delete();
            }
            FormBody formBody = new FormBody.Builder()
                    .add("userId", USERACCOUNT)
                    .build();
            Request request = new Request.Builder()
                    .url("http://"+hostIp+":8080/XianWanService/HeadPicSendToAndroidController")
                    .post(formBody)
                    .build();
            Call call = okHttpClient.newCall(request);
            Response response = null;
            try {
                response = call.execute();
                InputStream in = response.body().byteStream();
                OutputStream out = new FileOutputStream(
                        file
                );
                byte[] bytes = new byte[1024];
                int n = -1;
                while ((n = in.read(bytes)) != -1) {
                    out.write(bytes, 0, n);
                    out.flush();
                }
                flag = true;
                Log.i("zayabab", "错3");
                in.close();
                out.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }




