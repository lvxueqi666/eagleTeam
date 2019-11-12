package com.example.qiqi.xianwan.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.homeadapter.MyRecyclerAdapter;
import com.example.qiqi.xianwan.homeadapter.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyRecyclerAdapter myRecyclerAdapter;
    private List<String> mDatas;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_layout,container,false);
        recyclerView = view.findViewById(R.id.recyclerView);
        initData();
        myRecyclerAdapter = new MyRecyclerAdapter(getContext(),mDatas);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        recyclerView.addItemDecoration(new SpacesItemDecoration(50,50));

        recyclerView.setAdapter(myRecyclerAdapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());


        return view;
    }

    private void initData() {
        mDatas = new ArrayList<String>();
        for ( int i=0; i < 10; i++) {
            mDatas.add( "item"+i);
        }
    }

}
