package com.example.qiqi.xianwan.wofabu;
import android.Manifest;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.qiqi.xianwan.MessageEvent;
import com.example.qiqi.xianwan.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class content_fabu extends AppCompatActivity
{

    private Button btn_contentfabu_back;
    private GridLayout LL_fabuzay;
    private Button btn_newFabuzay;
    private boolean flag = true;
    private TextView tv_fahuodizay;
    private CustomOnClickListener listener;
    private String imagePath;
    private EditText ed_price;
    private EditText ed_yunfei;
    private int index = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.me_content_fabu);

        getView();
        getRegister();
        //注册EventBus
        registerEventBus();
        InitSendAddress();

    }

    private void registerEventBus() {
        EventBus.getDefault().register(this);
    }


    private void getRegister() {
        listener = new CustomOnClickListener();
        btn_newFabuzay.setOnClickListener(listener);
        btn_contentfabu_back.setOnClickListener(listener);
    }

    private void InitSendAddress() {
        locationOption();
    }
    private void locationOption() {
        final LocationClient locationClient;
        LocationClientOption locationClientOption;
        //1. 创建定位服务客户端类的对象
        locationClient =
                new LocationClient(getApplicationContext());
        //2. 创建定位客户端选项类的对象，并设置定位参数
        locationClientOption = new LocationClientOption();
        //设置定位参数
        //打开GPS
        locationClientOption.setOpenGps(true);
        //设置定位间隔时间
        locationClientOption.setScanSpan(1000);
        //设置定位坐标系
        locationClientOption.setCoorType("gcj02");
        //设置定位模式:高精度模式
        locationClientOption.setLocationMode(
                LocationClientOption.LocationMode.Hight_Accuracy
        );
        //需要定位的地址数据
        locationClientOption.setIsNeedAddress(true);
        //需要地址描述
        locationClientOption.setIsNeedLocationDescribe(true);
        //需要周边POI信息
        locationClientOption.setIsNeedLocationPoiList(true);
        //3. 将定位选项参数应用给定位服务客户端类的对象
        locationClient.setLocOption(locationClientOption);
        //4. 开始定位
        locationClient.start();
        //5. 给定位客户端端类的对象注册定位监听器
        locationClient.registerLocationListener(
                new BDAbstractLocationListener() {
                    @Override
                    public void onReceiveLocation(BDLocation bdLocation) {
                        //获取定位详细数据
                        //获取地址信息
                        String province = bdLocation.getProvince();
                        String city = bdLocation.getCity();
                        String district = bdLocation.getDistrict();
                        Log.i("zay","地址："+province+city+district);
                        flag =false;
                        Log.i("zay","flag="+flag);
                        if(flag == false){
                            tv_fahuodizay.setText("发货地："+province+" "+city+" "+district);
                            locationClient.stop();
                        }
                    }

                }
        );

    }


    private void getView() {
        ed_price=findViewById(R.id.ed_price);
        ed_price.setRawInputType(Configuration.KEYBOARD_QWERTY);
        ed_yunfei=findViewById(R.id.ed_yunfei);
        ed_yunfei.setRawInputType(Configuration.KEYBOARD_QWERTY);
        btn_contentfabu_back=findViewById(R.id.btn_contentfabu_back);
        LL_fabuzay = findViewById(R.id.LL_fabuzay);
        tv_fahuodizay = findViewById(R.id.tv_fahuodizay);
        btn_newFabuzay = findViewById(R.id.btn_newFabuzay);
    }


    private class CustomOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_newFabuzay:
                    if (ContextCompat.checkSelfPermission(content_fabu.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(content_fabu.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    } else {
                        openAlbum();//打开相册
                    }
                    break;
                case R.id.btn_contentfabu_back:
                    showCustomMessageDialog();
                    break;
            }
        }
    }

    private void showCustomMessageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("是否保存信息");
        //设置内容视图
        View view = getLayoutInflater().inflate(R.layout.me_dialog_layout,null);
        //得到自定义视图中的控件对象

        final CheckBox checkBox = view.findViewById(R.id.cb_msg);
        builder.setView(view);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setPositiveButton("发布", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//发布事件

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                showPic(data);

        }
    }

    public void showPic(Intent data) {
        imagePath = null;
        Uri uri = Uri.parse("");
        if(data != null){
            uri=data.getData();
        }
        if(DocumentsContract.isDocumentUri(content_fabu.this,uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        if(data != null){
            //动态添加Button控件
            addButton(imagePath);

           // Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            //img.setImageBitmap(bitmap);

        }

    }
    //动态添加Button控件
    private void addButton(final String imagePath) {

        //图像点击按钮
        final Button button = new Button(content_fabu.this);
        //动态添加Button按钮
        LL_fabuzay.addView(button,index);
        index++;
        //设置宽高属性
        ViewGroup.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 320;
        params.height= 320;
        button.setLayoutParams(params);
        //注入灵魂
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        Log.i("zay111","path2:"+imagePath);
        Drawable drawable = new BitmapDrawable(bitmap);
        button.setBackground(drawable);
        //设置点击事件
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(content_fabu.this,BigPic_fabu.class);
                intent.putExtra("path",imagePath);
                MessageEvent messageEvent = new MessageEvent(button);
                EventBus.getDefault().postSticky(messageEvent);
                startActivity(intent);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void onMoonEvent(MessageEvent objEvent) {
        if(objEvent.getMessage() != null){
        if (objEvent.getMessage().equals("delete")) {
            LL_fabuzay.removeView(objEvent.getButton());
            index--;
        }
        }
    }



    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

}