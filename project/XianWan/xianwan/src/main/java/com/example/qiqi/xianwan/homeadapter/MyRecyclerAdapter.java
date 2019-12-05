package com.example.qiqi.xianwan.homeadapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qiqi.xianwan.DetailActivity;
import com.example.qiqi.xianwan.MainActivity;
import com.example.qiqi.xianwan.R;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int mListStyle;
    //数据源
    private List<String> commodityId;
    private List<String> images;
    private List<String> introductions;
    private List<String> price;
    private List<String> icon;
    private List<String> userName;
    private List<String> userAccount;
    private List<String> attr;
    private List<String> showLike;

    private Context mContext;
    private LayoutInflater inflater;

    private int normalType = 0;     // 第一种ViewType，正常的item
    private int footType = 1;       // 第二种ViewType，底部的提示View

    private boolean hasMore = true;   // 变量，是否有更多数据
    private boolean fadeTips = false; // 变量，是否隐藏了底部的提示

    private Handler mHandler = new Handler(Looper.getMainLooper()); //获取主线程的Handler

    //私有属性
    private OnItemClickListener onItemClickListener = null;

    //setter方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //回调接口
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public MyRecyclerAdapter(Context context,List<String> commoId,List<String> images,List<String> introductions,List<String> price,List<String> icon,List<String> userName,List<String> userAccount,List<String> attribute,List<String> showLikes,int listStyle,boolean hasMore){
        this.mContext = context;
        this.commodityId = commoId;
        this.images = images;
        this.introductions = introductions;
        this.price = price;
        this.icon = icon;
        this.userName = userName;
        this.userAccount = userAccount;
        this.attr = attribute;
        this.showLike = showLikes;
        this.mListStyle = listStyle;
        inflater = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 根据返回的ViewType，绑定不同的布局文件，这里只有两种
        if (viewType == normalType) {
            return new NormalHolder(LayoutInflater.from(mContext).inflate(R.layout.view_holder_item, null));
        } else {
            return new FootHolder(LayoutInflater.from(mContext).inflate(R.layout.footview, null));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        //实现点击效果
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
                    Log.e("size::::::::::::",price.size() + "");
                    Log.e("position:::::::",position+"");
                    Intent intent = new Intent();
                    intent.putExtra("commodityId",commodityId.get(position));
                    intent.putExtra("images",images.get(position));
                    intent.putExtra("introductions",introductions.get(position));
                    intent.putExtra("price",price.get(position));
                    intent.putExtra("icon",icon.get(position));
                    intent.putExtra("userName",userName.get(position));
                    intent.putExtra("userAccount",userAccount.get(position));
                    intent.putExtra("showLike",showLike.get(position));
                    intent.setClass(mContext, DetailActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });
        // 如果是正常的item，直接设置TextView的值
        if (holder instanceof NormalHolder) {
            if(mListStyle == 0){
                Glide.with(mContext).load(images.get(position)).into(((NormalHolder) holder).showIv);
                ((NormalHolder) holder).introduce.setText(introductions.get(position));
                ((NormalHolder) holder).price.setText(price.get(position));
                ((NormalHolder) holder).mIcon.setImageResource(R.drawable.xinming);
                ((NormalHolder) holder).userName.setText(userName.get(position));
            }else{
                Glide.with(mContext).load(images.get(position)).into(((NormalHolder) holder).showIv);
                ((NormalHolder) holder).introduce.setText(introductions.get(position));
                ((NormalHolder) holder).price.setText(price.get(position));
                ((NormalHolder) holder).mIcon.setImageResource(R.drawable.qzuozhu);
                ((NormalHolder) holder).userName.setText(userName.get(position));
            }
        } else {
            // 之所以要设置可见，是因为我在没有更多数据时会隐藏了这个footView
            ((FootHolder) holder).tips.setVisibility(View.VISIBLE);
            // 只有获取数据为空时，hasMore为false，所以当我们拉到底部时基本都会首先显示“正在加载更多...”
            if (hasMore == true) {
                // 不隐藏footView提示
                fadeTips = false;
                if (userName.size() > 0) {
                    // 如果查询数据发现增加之后，就显示正在加载更多
                    ((FootHolder) holder).tips.setText("正在加载更多...");
                }
            } else {
                if (userName.size() > 0) {
                    // 如果查询数据发现并没有增加时，就显示没有更多数据了
                    ((FootHolder) holder).tips.setText("没有更多数据了");

                    // 然后通过延时加载模拟网络请求的时间，在500ms后执行
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 隐藏提示条
                            ((FootHolder) holder).tips.setVisibility(View.GONE);
                            // 将fadeTips设置true
                            fadeTips = true;
                            // hasMore设为true是为了让再次拉到底时，会先显示正在加载更多
                            hasMore = true;
                        }
                    }, 500);
                }
            }
        }
    }


    @Override
    public int getItemCount() {
        return userName.size() + 1;
    }

    // 自定义方法，获取列表中数据源的最后一个位置，比getItemCount少1，因为不计上footView
    public int getRealLastPosition() {
        return userName.size();
    }

    // 根据条目位置返回ViewType，以供onCreateViewHolder方法内获取不同的Holder
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return footType;
        } else {
            return normalType;
        }
    }

    // 正常item的ViewHolder，用以缓存findView操作
    class NormalHolder extends RecyclerView.ViewHolder {
        ImageView showIv;
        ImageView mIcon;
        TextView introduce;
        TextView price;
        TextView userName;

        public NormalHolder(View itemView) {
            super(itemView);
            showIv = itemView.findViewById(R.id.showIv);
            introduce = itemView.findViewById(R.id.introduce);
            price = itemView.findViewById(R.id.price);
            mIcon = itemView.findViewById(R.id.icons);
            userName = itemView.findViewById(R.id.userName);
        }
    }

    // // 底部footView的ViewHolder，用以缓存findView操作
    class FootHolder extends RecyclerView.ViewHolder {
        private TextView tips;

        public FootHolder(View itemView) {
            super(itemView);
            tips = itemView.findViewById(R.id.tips);
        }
    }

    // 暴露接口，改变fadeTips的方法
    public boolean isFadeTips() {
        return fadeTips;
    }

    // 暴露接口，下拉刷新时，通过暴露方法将数据源置为空
    public void resetDatas() {
        commodityId = new ArrayList<>();
        images = new ArrayList<>();
        introductions = new ArrayList<>();
        price = new ArrayList<>();
        icon = new ArrayList<>();
        userName = new ArrayList<>();
        userAccount = new ArrayList<>();
        attr = new ArrayList<>();
        showLike = new ArrayList<>();
    }

    // 暴露接口，更新数据源，并修改hasMore的值，如果有增加数据，hasMore为true，否则为false
    public void updateList(List<String> commoId,List<String> image,List<String> introduction,List<String> prices,List<String> icons,List<String> name,List<String> account, List<String> attribute,List<String> showLikes,boolean hasMore) {
        // 在原有的数据之上增加新数据
        if (name != null) {
            commodityId.addAll(commoId);
            images.addAll(image);
            introductions.addAll(introduction);
            price.addAll(prices);
            icon.addAll(icons);
            userName.addAll(name);
            userAccount.addAll(account);
            attr.addAll(attribute);
            showLike.addAll(showLikes);
        }
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }
}
