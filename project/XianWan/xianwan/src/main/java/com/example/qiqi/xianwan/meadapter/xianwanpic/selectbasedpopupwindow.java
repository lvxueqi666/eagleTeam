package com.example.qiqi.xianwan.xianwanpic;


import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.qiqi.xianwan.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import okhttp3.OkHttpClient;

import static android.app.Activity.RESULT_OK;

public class selectbasedpopupwindow extends PopupWindow{
    private OkHttpClient okHttpClient;
    private String hostStr;
    private Button btnzay_OpenCamera;
    private Button btnzay_OpenAlbum;
    private Button btnzay_cencel;
    private View popView;
    private CustomOnclickListener listener;
    public Activity preContext;
    public static final int TAKE_PHOTO = 1;//拍照
    public static final int CHOOSE_PHOTO = 2;//选择相册
    public static final int PICTURE_CUT = 3;//剪切图片
    private Uri imageUri;//相机拍照图片保存地址
    private Uri cutImageUri;//裁剪后的照片存放地址
    private String imagePath;//打开相册选择照片的路径

    public Uri getImageUri() {
        return imageUri;
    }

    public Uri getCutImageUri() {
        return cutImageUri;
    }

    public String getImagePath() {
        return imagePath;
    }

    public selectbasedpopupwindow(final Activity context){
        super(context);
        this.preContext = context;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popView = inflater.inflate(R.layout.selectbasedpopupwindow,null);
        getView();
        getRegister();
        this.setContentView(popView);

    }

    private void getRegister() {
        listener = new CustomOnclickListener();
        btnzay_cencel.setOnClickListener(listener);
        btnzay_OpenCamera.setOnClickListener(listener);
        btnzay_OpenAlbum.setOnClickListener(listener);
    }

    private void getView() {
        btnzay_cencel = popView.findViewById(R.id.btnzay_cencel);
        btnzay_OpenCamera = popView.findViewById(R.id.btnzay_OpenCamera);
        btnzay_OpenAlbum = popView.findViewById(R.id.btnzay_OpenAlbum);
    }
    class CustomOnclickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnzay_OpenCamera:
                    Log.i("zayaaa","133");
                    openCamera();
                    break;
                case R.id.btnzay_OpenAlbum:
                    if (ContextCompat.checkSelfPermission(preContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(preContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    } else {
                        openAlbum();//打开相册
                    }
                    break;
                case R.id.btnzay_cencel:
                    dismiss();
                    break;
            }
        }
    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        preContext.startActivityForResult(intent, CHOOSE_PHOTO);
    }

    private void openCamera() {
        //创建File对象,用于存储拍照后的图片
        //存放在cache目录下
        File cacheimage = new File(preContext.getExternalCacheDir(),"cache_img.jpg");
        try {
            if(cacheimage.exists()){
                cacheimage.delete();
            }
            cacheimage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //进行版本号判断
        if(Build.VERSION.SDK_INT < 24){
            imageUri = Uri.fromFile(cacheimage);
        }
        else {
            imageUri = FileProvider.getUriForFile(preContext,"com.example.qiqi.xianwan.xianwanpic.fileprovider",cacheimage);

        }

        //启动相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        preContext.startActivityForResult(intent,TAKE_PHOTO);

    }
    //
    //裁剪图片
    public void cropPhoto(Uri uri){
        Log.i("zayaaa","123");
        //创建File对象，用于存储裁剪后的图片，避免更改原图
        File file = new File(preContext.getExternalCacheDir(),"crop_image.jpg");
        try {
            if(file.exists()){
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cutImageUri = Uri.fromFile(file);

        Log.i("zay","uri"+cutImageUri);
        Log.i("zay","uri1:"+cutImageUri.getPath());
        Intent intent = new Intent("com.android.camera.action.CROP");
        //24之后需要额外赋予权限
        if(Build.VERSION.SDK_INT >= 24){
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        //设置类型
        intent.setDataAndType(uri,"image/*");
        //裁剪图片的宽高比例
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        //可裁剪标识
        intent.putExtra("crop","true");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,cutImageUri);
        preContext.startActivityForResult(intent,PICTURE_CUT );


    }

    public void handleImageBeforeKitKat(Intent data) {
        Uri uri = Uri.parse("");
        if(data != null){
            uri=data.getData();
        }
        imagePath = getImagePath(uri, null);
        if(data != null){
            cropPhoto(uri);

        }
    }

    public void handleImageOnKitKat(Intent data) {
        imagePath = null;
        Uri uri = Uri.parse("");
        if(data != null){
            uri=data.getData();
        }
        // 如果是document类型的Uri，则通过document id处理
        if(DocumentsContract.isDocumentUri(preContext,uri)){
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
            cropPhoto(uri);

        }



    }
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = preContext.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

}

