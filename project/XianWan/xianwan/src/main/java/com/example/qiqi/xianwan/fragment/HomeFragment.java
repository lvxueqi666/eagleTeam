package com.example.qiqi.xianwan.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.SearchActivity;
import com.example.qiqi.xianwan.homeadapter.BookRecyclerAdapter;
import com.example.qiqi.xianwan.homeadapter.GlideImageLoader;
import com.example.qiqi.xianwan.homeadapter.MyRecyclerAdapter;
import com.example.qiqi.xianwan.homeadapter.RecyclerViewSpacesItemDecoration;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private View view;
    private TabLayout tabLayout;
    private TabLayoutListener tabLayoutListener;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private MyRecyclerAdapter myRecyclerAdapter;
    private BookRecyclerAdapter bookRecyclerAdapter;
    private List<String> commodityId;
    private List<String> images;
    private List<String> introductions;
    private List<String> price;
    private List<String> icon;
    private List<String> titles;
    private List<String> userAccount;
    private List<String> userName;
    private List<String> attr;
    private List<String> showLike;
    private List<String> bookCommodityId;
    private List<String> bookImages;
    private List<String> bookIntroductions;
    private List<String> bookPrice;
    private List<String> bookIcon;
    private List<String> bookUserAccount;
    private List<String> bookUserName;
    private List<String> bookAttr;
    private List<String> bookShowLike;
    private List<String> bannerImg;
    private Banner banner;
    private int mListStyle = 0;
    private GridLayoutManager mLayoutManager;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Handler handler;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view != null){
            ViewGroup parent = (ViewGroup) view.getParent();
            if(parent != null){
                parent.removeView(view);
            }
        }else {
            view = inflater.inflate(R.layout.home_fragment_layout,container,false);
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    String json = (String) msg.obj;
                    Log.e("jsonjsonjson",json);
                    try {
                        JSONArray jsonArray = new JSONArray(json);
                        for(int i = 0; i < jsonArray.length();i++){
                            String objStr = jsonArray.getString(i);
                            JSONObject jsonObject = new JSONObject(objStr);
                            commodityId.add(jsonObject.getString("id"));
                            images.add(jsonObject.getString("image"));
                            introductions.add(jsonObject.getString("introduce"));
                            price.add(jsonObject.getString("price"));
                            icon.add(jsonObject.getString("icon"));
                            userName.add(jsonObject.getString("userName"));
                            userAccount.add(jsonObject.getString("userAccount"));
                            attr.add(jsonObject.getString("attr"));
                            showLike.add(jsonObject.getString("showLike"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    loadInitBooks();
                    initTabLayout();
                    initRecyclerView();
                }
            };
            initData();
            initBookData();
            asyncFormOp();
            findView();
            initRefreshLayout();
            initBanner();

            TextView tv = view.findViewById(R.id.tv_search);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),SearchActivity.class);
                    startActivity(intent);
                }
            });
        }

        return view;
    }

    private void initData(){
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

    private void initBookData(){
        bookCommodityId = new ArrayList<>();
        bookImages = new ArrayList<>();
        bookIntroductions = new ArrayList<>();
        bookPrice = new ArrayList<>();
        bookIcon = new ArrayList<>();
        bookUserName = new ArrayList<>();
        bookUserAccount = new ArrayList<>();
        bookAttr = new ArrayList<>();
        bookShowLike = new ArrayList<>();
    }


    private void findView() {
        banner = view.findViewById(R.id.banner);
        tabLayout = view.findViewById(R.id.tabLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        refreshLayout = view.findViewById(R.id.refreshLayout);
    }

    private void initRefreshLayout() {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(this);
    }

    private void initBanner() {
        bannerImg = new ArrayList<>();
        titles = new ArrayList<>();
        bannerImg.add("http://img4.imgtn.bdimg.com/it/u=4078564255,2150323891&fm=26&gp=0.jpg");
        bannerImg.add("http://img4.imgtn.bdimg.com/it/u=1380751069,2379422871&fm=26&gp=0.jpg");
        bannerImg.add("http://img1.imgtn.bdimg.com/it/u=2276673180,3353400665&fm=26&gp=0.jpg");
        titles.add("1");
        titles.add("2");
        titles.add("3");
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(bannerImg);
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

    private void initTabLayout(){
        tabLayoutListener = new TabLayoutListener();
        tabLayout.setOnTabSelectedListener(tabLayoutListener);
        tabLayout.addTab(tabLayout.newTab().setText("玩具").setTag(0));
        tabLayout.addTab(tabLayout.newTab().setText("书籍").setTag(1));

    }

    private void initRecyclerView() {
        Resources resources = getActivity().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;//px获取
        final float fontScale = getActivity().getResources().getDisplayMetrics().density;
        int itemWidth = (int) (170 * fontScale + 0.5f);
        int leftDecoration = (width - 2 * itemWidth) / 4;

        HashMap<String,Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION,20);
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,10);
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION,leftDecoration);
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION,leftDecoration);

        mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        if(recyclerView.getItemDecorationCount() == 0)
            recyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if(!recyclerView.canScrollVertically(1)){
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(mListStyle == 0){
                                    loadMoreData("toy");
                                }else{
                                    loadMoreData("book");
                                }

                            }
                        }, 500);
                        updateRecyclerView();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    private void updateRecyclerView() {
        if (userName.size() > 0) {
            if(mListStyle == 0){
                myRecyclerAdapter.updateList(commodityId,images,introductions,price,icon,userName,userAccount,attr,showLike, true);
            }else {
                bookRecyclerAdapter.updateList(bookCommodityId,bookImages,bookIntroductions,bookPrice,bookIcon,bookUserName,bookUserAccount,bookAttr,bookShowLike, true);
            }

        } else {
            myRecyclerAdapter.updateList(null,null,null,null,null, null,null,null,null,false);
        }
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        if(mListStyle == 0) {
            myRecyclerAdapter.resetDatas();
        }else {
            bookRecyclerAdapter.resetDatas();
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mListStyle == 0){
                    loadMoreData("toy");
                }else{
                    loadMoreData("book");
                }
            }
        }, 500);
        refreshLayout.setRefreshing(false);
        updateRecyclerView();
    }

    class TabLayoutListener implements TabLayout.OnTabSelectedListener {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            Log.e("anle","anle");
            mListStyle = (int)tab.getTag();
            if(mListStyle == 0) {
                myRecyclerAdapter = new MyRecyclerAdapter(getActivity(),commodityId,images,introductions,price,icon,userName,userAccount,attr,showLike,mListStyle,userName.size() > 0 ? true : false);
                recyclerView.setAdapter(myRecyclerAdapter);
                myRecyclerAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                    }
                });
            }

            if(mListStyle == 1) {
                bookRecyclerAdapter = new BookRecyclerAdapter(getActivity(),bookCommodityId,bookImages,bookIntroductions,bookPrice,bookIcon,bookUserName,bookUserAccount,bookAttr,bookShowLike,mListStyle,bookUserName.size() > 0 ? true : false);
                recyclerView.setAdapter(bookRecyclerAdapter);
                bookRecyclerAdapter.setOnItemClickListener(new BookRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                    }
                });
            }
            recyclerView.getAdapter().notifyDataSetChanged();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }

    private void asyncFormOp() {
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request;

                FormBody formBody = new FormBody.Builder()
                        .add("type", "toy")
                        .build();
                request = new Request.Builder()
                        .url("http://"+hostIp+":8080/XianWanService/HomeForAndroid")
                        .post(formBody)
                        .build();

                //Call
                Call call = okHttpClient.newCall(request);

                Response response;

                try {
                    response = call.execute();
                    String message =  response.body().string();
                    wrapperMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void loadMoreData(final String type) {
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request;
                if(type.equals("toy")){
                    initData();
                    FormBody formBody = new FormBody.Builder()
                            .add("type", "toy")
                            .build();
                    request = new Request.Builder()
                            .url("http://" + hostIp + ":8080/XianWanService/HomeForAndroid")
                            .post(formBody)
                            .build();
                }else{
                    initBookData();
                    FormBody formBody = new FormBody.Builder()
                            .add("type", "book")
                            .build();
                    request = new Request.Builder()
                            .url("http://"+hostIp+":8080/XianWanService/HomeForAndroid")
                            .post(formBody)
                            .build();
                }

                Call call = okHttpClient.newCall(request);

                Response response;
                try {
                    response = call.execute();
                    String message = response.body().string();
                    if(mListStyle == 0) {
                        try {
                            JSONArray jsonArray = new JSONArray(message);
                            for(int i = 0; i < jsonArray.length();i++){
                                String objStr = jsonArray.getString(i);
                                JSONObject jsonObject = new JSONObject(objStr);
                                commodityId.add(jsonObject.getString("id"));
                                images.add(jsonObject.getString("image"));
                                introductions.add(jsonObject.getString("introduce"));
                                price.add(jsonObject.getString("price"));
                                icon.add(jsonObject.getString("icon"));
                                userName.add(jsonObject.getString("userName"));
                                userAccount.add(jsonObject.getString("userAccount"));
                                attr.add(jsonObject.getString("attr"));
                                showLike.add(jsonObject.getString("showLike"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                        try {
                            JSONArray jsonArray = new JSONArray(message);
                            for(int i = 0; i < jsonArray.length();i++){
                                String objStr = jsonArray.getString(i);
                                JSONObject jsonObject = new JSONObject(objStr);
                                bookCommodityId.add(jsonObject.getString("id"));
                                bookImages.add(jsonObject.getString("image"));
                                bookIntroductions.add(jsonObject.getString("introduce"));
                                bookPrice.add(jsonObject.getString("price"));
                                bookIcon.add(jsonObject.getString("icon"));
                                bookUserName.add(jsonObject.getString("userName"));
                                bookUserAccount.add(jsonObject.getString("userAccount"));
                                bookAttr.add(jsonObject.getString("attr"));
                                bookShowLike.add(jsonObject.getString("showLike"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void loadInitBooks() {
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request;
                FormBody formBody = new FormBody.Builder()
                        .add("type", "book")
                        .build();
                request = new Request.Builder()
                        .url("http://" + hostIp + ":8080/XianWanService/HomeForAndroid")
                        .post(formBody)
                        .build();

                Call call = okHttpClient.newCall(request);

                Response response;
                try {
                    response = call.execute();
                    String message = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(message);
                        for(int i = 0; i < jsonArray.length();i++){
                            String objStr = jsonArray.getString(i);
                            JSONObject jsonObject = new JSONObject(objStr);
                            bookCommodityId.add(jsonObject.getString("id"));
                            bookImages.add(jsonObject.getString("image"));
                            bookIntroductions.add(jsonObject.getString("introduce"));
                            bookPrice.add(jsonObject.getString("price"));
                            bookIcon.add(jsonObject.getString("icon"));
                            bookUserName.add(jsonObject.getString("userName"));
                            bookUserAccount.add(jsonObject.getString("userAccount"));
                            bookAttr.add(jsonObject.getString("attr"));
                            bookShowLike.add(jsonObject.getString("showLike"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
