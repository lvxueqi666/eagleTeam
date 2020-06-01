package com.example.qiqi.xianwan.Diandi;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;


import com.example.qiqi.xianwan.DiandiActivity;
import com.example.qiqi.xianwan.R;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//import static com.example.qiqi.xianwan.LoginActivity.USERACCOUNT;
//import static com.example.qiqi.xianwan.LoginActivity.USERNAME;

public class FaBuActivity extends AppCompatActivity {
    private List<String> pathList = new ArrayList<>();
    private GridLayout LL_dongtai;
    private CustomOnClickListener listener;
    private int index = 0;
    private String imagePath;

    public static String USERNAME="123";
    public static String USERACCOUNT="xixi";
    public static int count = 0;

    private Button btn_tj;
    private Button btn_contentfabu;
    private Button btn_back1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabupic);

        getView();
        getRegister();

    }
    private void getView() {
        LL_dongtai = findViewById(R.id.dongtai);
        btn_tj = findViewById(R.id.btn_tj);
        btn_contentfabu=findViewById(R.id.btn_contentfabu);
        btn_back1=findViewById(R.id.btn_back1);
    }
    private void getRegister() {
        listener = new CustomOnClickListener();
        btn_tj.setOnClickListener(listener);
        btn_contentfabu.setOnClickListener(listener);
        btn_back1.setOnClickListener(listener);
    }
    private class CustomOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_tj:
                    if (ContextCompat.checkSelfPermission(FaBuActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(FaBuActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    } else {
                        if (index < 9)
                            openAlbum();//打开相册
                        else {
                            Toast.makeText(getApplicationContext(), "亲，已达到最大发布数了呢！", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case R.id.btn_contentfabu:
                    Toast.makeText(FaBuActivity.this, "发布成功!", Toast.LENGTH_LONG).show();
                    Log.i("zay", "zga");
                    new Thread() {
                        @Override
                        public void run() {
                            Log.i("zay", "zgaaa");
                            fabu();
                        }
                    }.start();

                    Intent intent = new Intent(FaBuActivity.this, DiandiActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_back1:
                    Intent intent1 = new Intent(FaBuActivity.this, DiandiActivity.class);
                    startActivity(intent1);
            }
        }
    }

    private void fabu() {
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        Log.i("dddd","ddd");
        //设置响应超时时间
        OkHttpClient client = new OkHttpClient.Builder()
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        //设置文件传输类型及编码格式
        MediaType mediaType = MediaType.parse("multipart/from-data;charser=utf-8");
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userId",USERACCOUNT);

        for(int i = 0;i < pathList.size();i++){
            builder.addFormDataPart("img",""+i, RequestBody.create(new File(pathList.get(i)),mediaType));
            Log.i("aaa",pathList.get(i));
        }
        RequestBody body = builder.build();
        Log.i("aaa","fialssss");
        Request request = new Request.Builder()
                //可以运行！！！
//                .url("http://"+hostIp+":8080/XianWanService/FabuPicSaveController")
                .url("http://"+hostIp+":8080/XianWanService/PicForDiandi")
                .post(body)
                .build();
        Call call = client.newCall(request);
        //4. 发起请求并接收响应
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        if(DocumentsContract.isDocumentUri(FaBuActivity.this,uri)){
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
        //将地址存放到list集合中,便于一起提交
        Log.i("zayhh",":"+imagePath);
        final Uri suolue = makePicSmaller(imagePath);
        pathList.add(suolue.getPath());

        //图像点击按钮
        final Button button = new Button(FaBuActivity.this);
        //动态添加Button按钮
        LL_dongtai.addView(button,index);
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
                Intent intent = new Intent(FaBuActivity.this,BigPic_fabu.class);
                intent.putExtra("path",suolue.getPath());
                MessageEvent messageEvent = new MessageEvent(button);
                EventBus.getDefault().postSticky(messageEvent);
                startActivity(intent);
            }
        });
    }
    public Uri makePicSmaller(String originalPic){
        File file = new File(getExternalCacheDir(),"suolue_image"+count+".jpg");
        Log.i("zay21",""+file.getPath());
        count++;
        Uri suolue = Uri.fromFile(file);
        try {
            FileOutputStream out = new FileOutputStream(file);
            BitmapFactory.Options ops = new BitmapFactory.Options();
            ops.inSampleSize = 2;
            //ops.inJustDecodeBounds = true;
            Bitmap bitmap2 = BitmapFactory.decodeFile(originalPic,ops);
            bitmap2.compress(Bitmap.CompressFormat.JPEG,100,out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return suolue;
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