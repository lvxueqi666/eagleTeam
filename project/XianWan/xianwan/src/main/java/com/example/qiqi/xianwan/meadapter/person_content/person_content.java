package com.example.qiqi.xianwan.meadapter.person_content;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.qiqi.xianwan.meadapter.MessageEvent;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.example.qiqi.xianwan.R;
import com.lljjcoder.citypickerview.widget.CityPicker.OnCityItemClickListener;


import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;


public class person_content extends AppCompatActivity {
    private Button person_back;
    private Button finish;
    private CustomeClickListener listener;
    private RelativeLayout vip_sex;
    private RelativeLayout vip_birth;
    private RelativeLayout vip_jianjie;
    private RelativeLayout vip_job;
    private RelativeLayout vip_jobname;
    private RelativeLayout vip_location;
    private TextView tv_sex;
    private TextView tv_birth;
    private TextView tv_jianjie;
    private TextView tv_job;
    private TextView tv_jobname;
    private TextView tv_location;
    private ImageView item_bottom;
private String[] sexArry=new String[]{"MM","GG"};
    private Calendar cal;
    private int year,month,day;
    //用来赋值传递过来的对象
    MessageEvent mObjEvent;
    Handler handler = new Handler()
    {
        public void handleMessage(android.os.Message msg) {
            if(msg.what==0x123)
            {
                Toast.makeText(person_content.this, "查询数据库!", Toast.LENGTH_LONG).show();
            }
        };
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.me_person_content);
        new MyThread().start();

        //接收数据
        jieshoumesssage();
        //获取控件
        getViews();
        //注册监听器
        regListener();
        //获取当前日期
        getDate();



    }





    private void jieshoumesssage() {
        Intent intent2 = getIntent();
        //把传送进来的String类型的Message的值赋给新的变量message
        String message = intent2.getStringExtra("EXTRA_MESSAGE");
        //把布局文件中的文本框和textview链接起来
        TextView textView = (TextView) findViewById(R.id.tv_jianjie);
        //在textview中显示出来message
        textView.setText(message);
        //头像

    }

    private void getDate() {
        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);       //获取年月日时分秒
        month=cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day=cal.get(Calendar.DAY_OF_MONTH);

    }

    private void getViews() {
        item_bottom = findViewById(R.id.item_bottom);
        person_back = findViewById(R.id.person_back);
        vip_sex=findViewById(R.id.vip_sex);
        tv_sex=findViewById(R.id.tv_sex);
        tv_birth=findViewById(R.id.tv_birth);
        tv_jianjie=findViewById(R.id.tv_jianjie);
        tv_jobname=findViewById(R.id.tv_jobname);
        tv_job=findViewById(R.id.tv_job);
        tv_location=findViewById(R.id.tv_location);
        vip_birth=findViewById(R.id.vip_birth);
        vip_jianjie=findViewById(R.id.vip_jianjie);
        vip_job=findViewById(R.id.vip_job);
        vip_jobname=findViewById(R.id.vip_jobname);
        vip_location=findViewById(R.id.vip_location);
        finish=findViewById(R.id.finish);
    }


    private void regListener() {
        {
            listener = new CustomeClickListener();
            person_back.setOnClickListener(listener);
            vip_sex.setOnClickListener(listener);
            vip_birth.setOnClickListener(listener);
            vip_jianjie.setOnClickListener(listener);
            vip_job.setOnClickListener(listener);
            vip_jobname.setOnClickListener(listener);
            vip_location.setOnClickListener(listener);
            finish.setOnClickListener(listener);
        }





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
           String sms = data.getStringExtra("EXTRA_MESSAGE");
           tv_jianjie.setText(sms);
        }
        if (requestCode==2){
            String sms = data.getStringExtra("job");
            tv_job.setText(sms);
        }
        if (requestCode==3){
            String sms = data.getStringExtra("jobname");
            tv_jobname.setText(sms);
        }
    }
    private class CustomeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.person_back:
                    finish();
                    break;
                case R.id.vip_sex:
                    showSexChooseDialog();
                    break;
                case R.id.vip_birth:
                    DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker arg0, int year, int month, int day) {
                            tv_birth.setText(year+"-"+(++month)+"-"+day);      //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                        }
                    };
                    DatePickerDialog dialog=new DatePickerDialog(person_content.this, android.app.AlertDialog.THEME_HOLO_LIGHT,listener,year,month,day);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                    dialog.show();
                    break;
                case R.id.vip_jianjie:
                    EventBus.getDefault().postSticky(new MessageEvent(tv_jianjie.getText().toString()));
                    Intent intent=new Intent(person_content.this,Et_jianjie.class);
                    startActivityForResult(intent,1);
                    break;
                case R.id.vip_job:
                    EventBus.getDefault().postSticky(new MessageEvent(tv_job.getText().toString()));
                    Intent intent2=new Intent(person_content.this,List_job.class);
                    startActivityForResult(intent2,2);
                    break;
                case R.id.vip_jobname:
                    EventBus.getDefault().postSticky(new MessageEvent(tv_jobname.getText().toString()));
                    Intent intent3=new Intent(person_content.this,List_jobname.class);
                    startActivityForResult(intent3,3);
                    break;
                case R.id.vip_location:
                    //判断输入法状态
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                        selectAddress();//调用CityPicker选取区域

                    }
                    break;
                case R.id.finish:
                    Toast.makeText(person_content.this, "插入数据库!", Toast.LENGTH_LONG).show();
                    break;
            }
        }

        private void showSexChooseDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(person_content.this);// 自定义对话框
                builder.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {// 2默认的选中

                    @Override
                    public void onClick(DialogInterface dialog, int which) {// which是被选中的位置

                            tv_sex.setText(sexArry[which]);
                                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
                        }});
                        builder.show();

        }
    }

    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(person_content.this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .titleTextColor("#696969")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("河北省")
                .city("唐山市")
                .district("乐亭县")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
                tv_location.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }

            @Override
            public void onCancel() {
            }
        });
    }


    private class MyThread extends Thread{
        @Override
        public void run() {

            handler.sendEmptyMessage(0x123);
        }

    }
}
