package com.example.qiqi.xianwan.meadapter.wofabu;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.entity.Commodity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.qiqi.xianwan.LoginActivity.USERACCOUNT;

public class fabuAdapter extends BaseAdapter {

    private List<Commodity> commodities;
    private int itemLayoutId;
    private Context context;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Bundle bundle = msg.getData();
                    int position = bundle.getInt("posiyion");
                    commodities.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(
                            context,
                            (String)msg.obj,
                            Toast.LENGTH_SHORT
                    ).show();
                    break;
            }


        }

    };




    public fabuAdapter(Context context, List<Commodity> commodities, int itemLayoutId){
        this.context = context;
        this.commodities = commodities;
        this.itemLayoutId = itemLayoutId;

    }

    @Override
    public int getCount() {
        if(null != commodities){
            return commodities.size();
        }else{
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if(null != commodities){
            return commodities.get(position);
        }else{
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(null == convertView) {

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(itemLayoutId, null);
        }
        ImageView imageView = convertView.findViewById(R.id.fabu_img);
        TextView introduce = convertView.findViewById(R.id.fabu_introduce);
        TextView textView=convertView.findViewById(R.id.tv_price);
        Button button=convertView.findViewById(R.id.delete_fabu);

        Commodity commodity=commodities.get(position);

        RequestOptions options = new RequestOptions().skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(context).load(commodity.getImage()).apply(options).transform(new GlideRoundTransform(context,10)).into(imageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实现删除数据
                deleteFromSQlCommodity(position);

            }
        });


       introduce.setText(commodity.getIntroduce());
       textView.setText(commodity.getPrice());
        return convertView;
    }

    private void deleteFromSQlCommodity(int position) {
        Resources resources = context.getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request;
                String id =  ""+commodities.get(position).getId();
                FormBody formBody = new FormBody.Builder()
                        .add("id", id)
                        .build();
                request = new Request.Builder()
                        .url("http://" + hostIp + ":8080/XianWanService/deleteCommodity")
                        .post(formBody)
                        .build();

                //Call
                Call call = okHttpClient.newCall(request);
                Response response;
                try {
                    response = call.execute();
                    Message message1 = new Message();
                    message1.what = 1;
                    message1.obj = response.body().string();
                    Bundle bundle = new Bundle();
                    bundle.putInt("position",position);
                    message1.setData(bundle);
                    handler.sendMessage(message1);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
