package com.example.qiqi.xianwan.homeadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qiqi.xianwan.R;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private List<String> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public MyRecyclerAdapter(Context context,List<String> datas){
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_holder_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.showIv.setImageResource(R.drawable.me);
        holder.introduce.setText("该商品是假的");
        holder.price.setText("￥1000");
        holder.userName.setText(mDatas.get(position));
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView showIv;
        TextView introduce;
        TextView price;
        TextView userName;

        public MyViewHolder(View itemView) {
            super(itemView);
            showIv = itemView.findViewById(R.id.showIv);
            introduce = itemView.findViewById(R.id.introduce);
            price = itemView.findViewById(R.id.price);
            userName = itemView.findViewById(R.id.userName);
        }
    }
}
