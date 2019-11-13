package com.example.qiqi.xianwan.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.homeadapter.GlideImageLoader;
import com.example.qiqi.xianwan.homeadapter.MyRecyclerAdapter;
import com.example.qiqi.xianwan.homeadapter.RecyclerViewSpacesItemDecoration;
import com.example.qiqi.xianwan.homeadapter.SpaceItem;
import com.example.qiqi.xianwan.homeadapter.SpacesItemDecoration;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyRecyclerAdapter myRecyclerAdapter;
    private List<String> mDatas;
    private List<String> images;
    private List<String> titles;
    private Banner banner;
    private SearchView mSearchView;
    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_layout,container,false);
        mSearchView = view.findViewById(R.id.searchView);
        listView = view.findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1));
        listView.setTextFilterEnabled(true);

        banner = view.findViewById(R.id.banner);
        initBanner();
        recyclerView = view.findViewById(R.id.recyclerView);
        initData();
        myRecyclerAdapter = new MyRecyclerAdapter(getContext(),mDatas);
        initRecyclerView();



        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    listView.setFilterText(newText);
                }else{
                    listView.clearTextFilter();
                }
                return false;
            }
        });


        return view;
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for ( int i=0; i < 10; i++) {
            mDatas.add( "item"+i);
        }
    }

    private void initBanner(){
        images = new ArrayList<>();
        titles = new ArrayList<>();
        images.add("http://img2.imgtn.bdimg.com/it/u=3003105586,231331792&fm=26&gp=0.jpg");
        images.add("http://img2.imgtn.bdimg.com/it/u=3003105586,231331792&fm=26&gp=0.jpg");
        images.add("http://img2.imgtn.bdimg.com/it/u=3003105586,231331792&fm=26&gp=0.jpg");
        titles.add("1");
        titles.add("2");
        titles.add("3");
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    private void initRecyclerView(){

        Resources resources = getActivity().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        final float fontScale = getActivity().getResources().getDisplayMetrics().scaledDensity;
        int itemWidth = (int) (170 * fontScale + 0.5f);
        int leftDecoration = (width - 2 * itemWidth) / 4;

        HashMap<String,Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION,20);
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,10);
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION,leftDecoration);
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION,leftDecoration);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        recyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));

        recyclerView.setAdapter(myRecyclerAdapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setHasFixedSize(true);

        recyclerView.setNestedScrollingEnabled(false);
    }

}
