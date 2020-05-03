package com.example.qiqi.xianwan.childadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.bean.MyBean;
import com.example.qiqi.xianwan.widget.MyImageView;
import com.truizlop.sectionedrecyclerview.SectionedRecyclerViewAdapter;

import java.util.List;

public class MyAdapter extends SectionedRecyclerViewAdapter<MyAdapter.MyHeaderViewHolder, MyAdapter.MyItemViewHolder, MyAdapter.MyFooterViewHolder> {

    private Context mContext;
    private List<MyBean> mList;

    public MyAdapter(Context context, List<MyBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    /**
     * header或者footer的个数
     * @return
     */
    @Override
    protected int getSectionCount() {
        return mList.size();
    }

    /**
     * 每个header或者footer中包含具体的内容个数
     * @return
     */
    @Override
    protected int getItemCountForSection(int section) {
        return mList.get(section).getList().size();
    }

    /**
     * 是否显示footer
     * @param section
     * @return
     */
    @Override
    protected boolean hasFooterInSection(int section) {
        return true;
    }

    /**
     * 渲染具体的HeaderViewHolder
     *
     * @param parent   HeaderViewHolder的容器
     * @param viewType 一个标志，我们根据该标志可以实现渲染不同类型的ViewHolder
     * @return
     */
    @Override
    protected MyHeaderViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_rv_header, parent, false);
        return new MyHeaderViewHolder(itemView);
    }

    /**
     * 渲染具体的FooterViewHolder
     *
     * @param parent   FooterViewHolder的容器
     * @param viewType 一个标志，我们根据该标志可以实现渲染不同类型的ViewHolder
     * @return
     */
    @Override
    protected MyFooterViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_rv_footer, parent, false);
        return new MyFooterViewHolder(itemView);
    }

    /**
     * 渲染具体的ItemViewHolder
     *
     * @param parent   ItemViewHolder的容器
     * @param viewType 一个标志，我们根据该标志可以实现渲染不同类型的ViewHolder
     * @return
     */
    @Override
    protected MyItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_rv_content, parent, false);
        return new MyItemViewHolder(itemView);
    }

    /**
     * 绑定HeaderViewHolder的数据。
     *
     * @param holder
     * @param section 数据源list的下标
     */
    @Override
    protected void onBindSectionHeaderViewHolder(MyHeaderViewHolder holder, int section) {
        MyBean bean = mList.get(section);
        if (null == bean)
            return;
        holder.tv_header_title.setText(bean.getHeader());
    }

    /**
     * 绑定FooterViewHolder的数据。
     *
     * @param holder
     * @param section 数据源list的下标
     */
    @Override
    protected void onBindSectionFooterViewHolder(MyFooterViewHolder holder, int section) {
        MyBean bean = mList.get(section);
        if (null == bean)
            return;
        holder.tv_footer_title.setText(bean.getFooter());
    }

    /**
     * 绑定ItemViewHolder的数据。
     *
     * @param holder
     * @param section 数据源list的下标
     */
    @Override
    protected void onBindItemViewHolder(final MyItemViewHolder holder, int section, int position) {
        MyBean bean = mList.get(section);
        if (null == bean)
            return;
        RequestOptions options = new RequestOptions().transform(new CenterCrop()).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(mContext).asBitmap().apply(options).load(bean.getList().get(position).getUrl())
                .into(holder.iv_head);
//        GlideApp.with(mContext)
//                .asBitmap()
//                .apply(options)
//                .load(bean.getList().get(position).getUrl())
//                .centerCrop()
//                .into(holder.iv_head);
    }


    /**
     * ItemViewHolder
     */
    public class MyItemViewHolder extends RecyclerView.ViewHolder {
        private MyImageView iv_head;
        private TextView tv_name;

        public MyItemViewHolder(View itemView) {
            super(itemView);
            iv_head = (MyImageView)itemView.findViewById(R.id.item_iv);
            tv_name = (TextView)itemView.findViewById(R.id.item_tv_title);
        }
    }

    /**
     * HeaderViewHolder
     */
    public class MyHeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_header_title;
        public MyHeaderViewHolder(View itemView) {
            super(itemView);
            tv_header_title = (TextView)itemView.findViewById(R.id.item_header_title);
        }
    }

    /**
     * FooterViewHolder
     */
    public class MyFooterViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_footer_title;
        public MyFooterViewHolder(View itemView) {
            super(itemView);
            tv_footer_title = (TextView)itemView.findViewById(R.id.item_footer_title);
        }
    }


}
