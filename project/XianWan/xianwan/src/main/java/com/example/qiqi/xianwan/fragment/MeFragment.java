package com.example.qiqi.xianwan.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.ilike.Ilike;
import com.example.qiqi.xianwan.person_content.person_content;
import com.example.qiqi.xianwan.wofabu.wofabu;
import com.example.qiqi.xianwan.womaichu.womaichu;
import com.example.qiqi.xianwan.womaidao.womaidao;
import com.example.qiqi.xianwan.xianwanpic.headpicoption;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.File;

public class MeFragment extends Fragment {
    private SmartRefreshLayout srl;
    private ImageView fabu;
    private ImageView maichu;
    private ImageView maidao;
    private ImageView head_img_zay;
    private RelativeLayout rl_fabu;
    private RelativeLayout ilike;
    private LinearLayout background;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.me_fragment_layout,container,false);
        srl = view.findViewById(R.id.srl);
        fabu=view.findViewById(R.id.iv_fabu);
        maichu=view.findViewById(R.id.iv_maichu);
        maidao=view.findViewById(R.id.iv_maidao);
        head_img_zay = view.findViewById(R.id.head_img_zay);
        rl_fabu=view.findViewById(R.id.rl_fabu);
        ilike=view.findViewById(R.id.ilike);
        background= view.findViewById(R.id.background);
        InitHeadPic();
        head_img_zay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),headpicoption.class);
                startActivity(intent);

            }
        });
        //我发布的点击事件
        rl_fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),wofabu.class);
                startActivity(intent);
            }
        });


        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),person_content.class);
                startActivity(intent);

            }
        });

        //我收藏的点击事件
        ilike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),Ilike.class);
                startActivity(intent);
            }
        });
        srl.setReboundDuration(1000);
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshData();
                srl.finishRefresh();
                /*
                 * shuaxin
                 * */
            }
        });
        fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),wofabu.class);
                startActivity(intent);
            }
        });
        maichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),womaichu.class);
                startActivity(intent);
            }
        });
        maidao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),womaidao.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void InitHeadPic() {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .fallback(R.drawable.ic_launcher_background)
                .override(400)
                .circleCrop();
        if(new File(getContext().getFilesDir().getAbsolutePath()+"/UserPic.jpg").exists()){
            Glide.with(this)
                    .load(getContext().getFilesDir().getAbsolutePath()+"/UserPic.jpg")
                    .apply(requestOptions)
                    .into(head_img_zay);
        }
        else {
            Glide.with(this)
                    .load(R.mipmap.sss)
                    .apply(requestOptions)
                    .into(head_img_zay);
        }

    }

    private void refreshData() {
       InitHeadPic();
    }

}



