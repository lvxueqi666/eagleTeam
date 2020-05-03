package com.example.qiqi.xianwan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.qiqi.xianwan.DiandiActivity;
import com.example.qiqi.xianwan.R;

public class ChildrenFragment extends Fragment {

    private View view;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view != null){
            ViewGroup parent = (ViewGroup) view.getParent();
            if(parent != null){
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.childern_fragment_layout,container,false);
            findView();
            setOnclick();
        }
        return view;
    }


    private void findView(){
        btn1 = view.findViewById(R.id.child_btn1);
        btn2 = view.findViewById(R.id.child_btn2);
        btn3 = view.findViewById(R.id.child_btn3);
    }

    private void setOnclick(){
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getContext(), DiandiActivity.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
