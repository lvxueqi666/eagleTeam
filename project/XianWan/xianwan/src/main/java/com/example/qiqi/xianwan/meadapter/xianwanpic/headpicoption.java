package com.example.qiqi.xianwan.meadapter.xianwanpic;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.qiqi.xianwan.LoginActivity;
import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.entity.User;
import com.example.qiqi.xianwan.entity.UserDetail;
import com.example.qiqi.xianwan.meadapter.MessageEvent;
import com.example.qiqi.xianwan.meadapter.person_content.Et_jianjie;
import com.example.qiqi.xianwan.meadapter.person_content.List_job;
import com.example.qiqi.xianwan.meadapter.person_content.List_jobname;
import com.example.qiqi.xianwan.meadapter.wofabu.wofabu;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.lljjcoder.citypickerview.widget.CityPicker.OnCityItemClickListener;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.qiqi.xianwan.LoginActivity.USERACCOUNT;

public class headpicoption extends AppCompatActivity {

    public String userAccount=USERACCOUNT;
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
    private TextView et_username;
    private Button cancle;
    private String[] sexArry = new String[]{"MM", "GG"};
    private Calendar cal;
    List<UserDetail> userDetails = new ArrayList<>();
    List<User> users = new ArrayList<>();
    private int year, month, day;
    //用来赋值传递过来的对象
    MessageEvent mObjEvent;
    private Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = (String) msg.obj;
            if (users.size() > 0) {
                users.removeAll(users);
            }
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    String objStr = jsonArray.getString(i);
                    JSONObject jsonObject = new JSONObject(objStr);

                    User user = new User(
                            jsonObject.getString("userAccount"),

                            jsonObject.getString("userName")


                    );

                        et_username.setText(jsonObject.getString("userName"));
                    users.add(user);
                    Log.e("name", jsonObject.getString("userName"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }

    };
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = (String) msg.obj;
            if (userDetails.size() > 0) {
                userDetails.removeAll(userDetails);
            }
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    String objStr = jsonArray.getString(i);
                    JSONObject jsonObject = new JSONObject(objStr);

                    UserDetail userDetail = new UserDetail(
                            jsonObject.getString("userAccount"),

                            jsonObject.getString("userSex"),
                            jsonObject.getString("userBirth"),
                            jsonObject.getString("userLocation"),
                            jsonObject.getString("userJianjie"),
                            jsonObject.getString("userJob"),
                            jsonObject.getString("userJobName"),
                            jsonObject.getString("userPicture")

                    );

                    tv_sex.setText(jsonObject.getString("userSex"));
                    tv_birth.setText(jsonObject.getString("userBirth"));
                    tv_location.setText(jsonObject.getString("userLocation"));
                    tv_jianjie.setText(jsonObject.getString("userJianjie"));
                    tv_job.setText(jsonObject.getString("userJob"));
                    tv_jobname.setText(jsonObject.getString("userJob"));
                    userDetails.add(userDetail);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.e("shdddduaxin", "chaxdddun");

        }

    };

    private boolean flag = false;
    private OkHttpClient okHttpClient = new OkHttpClient();
    private String hostStr = "10.7.89.139";
    private ImageView imgzay_headPic;
    private selectbasedpopupwindow popupwindow;
    public static final int TAKE_PHOTO = 1;//拍照
    public static final int CHOOSE_PHOTO = 2;//选择相册
    public static final int PICTURE_CUT = 3;//剪切图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headpicoption);



        imgzay_headPic = findViewById(R.id.imgzay_headPic);

        searchName();
        asyncFormOp();
        asyncDownOp();

        InitHeadPic();
        //接收数据
        jieshoumesssage();
        //获取控件
        getViews();
        //注册监听器
        regListener();
        //获取当前日期
        getDate();

        imgzay_headPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupwindow = new selectbasedpopupwindow(headpicoption.this);
                View parentView = LayoutInflater.from(headpicoption.this).inflate(R.layout.activity_headpicoption, null);
                popupwindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);

            }
        });

    }

    private void searchName() {
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Log.e("userName", "userName");
                Request request;
                //Request(Post、FormBody）
                FormBody formBody = new FormBody.Builder()
                        .add("userAccount", USERACCOUNT)
                        .build();
                request = new Request.Builder()
                        .url("http://" + hostIp + ":8080/XianWanService/nameForAndroid")
                        .post(formBody)
                        .build();

                //Call
                Call call = okHttpClient.newCall(request);

                Response response;

                try {
                    response = call.execute();
                    String message = response.body().string();
                    wrapperMessage2(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void wrapperMessage2(String info) {
        Message msg = Message.obtain();
        msg.obj = info;
        handler2.sendMessage(msg);
    }

    private void asyncFormOp() {
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Log.e("shuaxinxxx", "chaxunxx");
                Request request;
                //Request(Post、FormBody）
                FormBody formBody = new FormBody.Builder()
                        .add("userAccount", USERACCOUNT)
                        .build();
                request = new Request.Builder()
                        .url("http://" + hostIp + ":8080/XianWanService/detailforAndroid")
                        .post(formBody)
                        .build();

                //Call
                Call call = okHttpClient.newCall(request);

                Response response;

                try {
                    response = call.execute();
                    String message = response.body().string();
                    wrapperMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void wrapperMessage(String info) {
        Message msg = Message.obtain();
        msg.obj = info;
        handler.sendMessage(msg);
    }

    private void jieshoumesssage() {
        Intent intent2 = getIntent();
        //把传送进来的String类型的Message的值赋给新的变量message
        String message = intent2.getStringExtra("EXTRA_MESSAGE");
        //把布局文件中的文本框和textview链接起来
        TextView textView = (TextView) findViewById(R.id.tv_jianjie);
        //在textview中显示出来message
        textView.setText(message);
    }

    private void getDate() {
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);       //获取年月日时分秒
        month = cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day = cal.get(Calendar.DAY_OF_MONTH);

    }

    private void getViews() {
        person_back = findViewById(R.id.person_back);
        vip_sex = findViewById(R.id.vip_sex);
        tv_sex = findViewById(R.id.tv_sex);
        tv_birth = findViewById(R.id.tv_birth);
        tv_jianjie = findViewById(R.id.tv_jianjie);
        tv_jobname = findViewById(R.id.tv_jobname);
        tv_job = findViewById(R.id.tv_job);
        tv_location = findViewById(R.id.tv_location);
        vip_birth = findViewById(R.id.vip_birth);
        vip_jianjie = findViewById(R.id.vip_jianjie);
        vip_job = findViewById(R.id.vip_job);
        vip_jobname = findViewById(R.id.vip_jobname);
        vip_location = findViewById(R.id.vip_location);
        finish = findViewById(R.id.finish);
        et_username = findViewById(R.id.et_username);
        cancle=findViewById(R.id.cancel);
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
            cancle.setOnClickListener(listener);
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
                    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker arg0, int year, int month, int day) {
                            tv_birth.setText(year + "-" + (++month) + "-" + day);      //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                        }
                    };
                    DatePickerDialog dialog = new DatePickerDialog(headpicoption.this, android.app.AlertDialog.THEME_HOLO_LIGHT, listener, year, month, day);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                    dialog.show();
                    break;
                case R.id.vip_jianjie:
                    EventBus.getDefault().postSticky(new MessageEvent(tv_jianjie.getText().toString()));
                    Intent intent = new Intent(headpicoption.this, Et_jianjie.class);
                    startActivityForResult(intent, 4);
                    break;
                case R.id.vip_job:
                    EventBus.getDefault().postSticky(new MessageEvent(tv_job.getText().toString()));
                    Intent intent2 = new Intent(headpicoption.this, List_job.class);
                    startActivityForResult(intent2, 5);
                    break;
                case R.id.vip_jobname:
                    EventBus.getDefault().postSticky(new MessageEvent(tv_jobname.getText().toString()));
                    Intent intent3 = new Intent(headpicoption.this, List_jobname.class);
                    startActivityForResult(intent3, 6);
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
                    if (popupwindow!=null) {
                        Uri cutImageUri = popupwindow.getCutImageUri();
                        Uri suolue = makePicSmaller(cutImageUri.getPath());
                        asyncUpOp(suolue.getPath());
                    }
                    String operate = "addperson";
                    String userName = et_username.getText().toString();
                    String userSex = tv_sex.getText().toString();
                    String userBirth = tv_birth.getText().toString();
                    String userLocation = tv_location.getText().toString();
                    String userJianjie = tv_jianjie.getText().toString();
                    String userJob = tv_job.getText().toString();
                    String userJobName = tv_jobname.getText().toString();
                    if (userName!=null&&userSex!=null&&userBirth!=null&&userLocation!=null&&userJianjie!=null&&userJob!=null&&userJobName!=null) {
                        addPerson(userAccount, userName, userSex, userBirth, userLocation, userJianjie, userJob, userJobName, operate);
                        Toast.makeText(headpicoption.this, "插入数据库!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else{
                        Toast.makeText(headpicoption.this, "请输入完整信息哇！!", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.cancel:
                    USERACCOUNT=null;
                    String name =EMClient.getInstance().getCurrentUser();
                    outchat(name);
                    Intent intent4 = new Intent();
                    intent4.setClass(headpicoption.this, LoginActivity.class);
                    startActivity(intent4);

                    break;
            }
        }

        private void showSexChooseDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(headpicoption.this);// 自定义对话框
            builder.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {// 2默认的选中

                @Override
                public void onClick(DialogInterface dialog, int which) {// which是被选中的位置

                    tv_sex.setText(sexArry[which]);
                    dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
                }
            });
            builder.show();

        }
    }

    private void addPerson(final String userAccount,final String userName, final String userSex, final String userBirth, final String userLocation, final String userJianjie, final String userJob, final String userJobName, final String operate) {
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request;
                FormBody formBody = new FormBody.Builder()
                        .add("userAccount",userAccount)
                        .add("userName", userName)
                        .add("userSex", userSex)
                        .add("userBirth", userBirth)
                        .add("userLocation", userLocation)
                        .add("userJianjie", userJianjie)
                        .add("userJob", userJob)
                        .add("userJobName", userJobName)
                        .add("operate", operate)
                        .build();
                request = new Request.Builder()
                        .url("http://" + hostIp + ":8080/XianWanService/addPerson")
                        .post(formBody)
                        .build();
                Call call = okHttpClient.newCall(request);
                try {
                    call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();




    }

    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(headpicoption.this)
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
    private void InitHeadPic() {
        //asyncDownOp();
        //while(flag != true);
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .fallback(R.drawable.ic_launcher_background)
                .override(200)
                .circleCrop();
        if(new File(getFilesDir().getAbsolutePath()+"/UserPic.jpg").exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(getFilesDir().getAbsolutePath()+"/UserPic.jpg");
            Glide.with(this)
                    .load(bitmap)
                    .apply(requestOptions)
                    .into(imgzay_headPic);
        }
        else {
            Glide.with(this)
                    .load(R.mipmap.sss)
                    .apply(requestOptions)
                    .into(imgzay_headPic);
        }

    }



    private void asyncDownOp() {
        new Thread() {
            @Override
            public void run() {
                try {
                    downImg();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }


    public void downImg() throws IOException {
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        //okHttpClient = new OkHttpClient();
        //hostStr = "192.168.43.175";
        File file = new File(getFilesDir().getAbsolutePath()+"/UserPic.jpg");
        if(file.exists()){
            file.delete();
        }
        FormBody formBody = new FormBody.Builder()
                .add("userId", USERACCOUNT)
                .build();
        Request request = new Request.Builder()
                .url("http://"+hostIp+":8080/XianWanService/HeadPicSendToAndroidController")
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        InputStream in = response.body().byteStream();
        OutputStream out = new FileOutputStream(
                file
        );
        byte[] bytes = new byte[1024];
        int n = -1;
        while((n = in.read(bytes)) != -1){
            out.write(bytes, 0 , n);
            out.flush();
        }
        flag = true;
        in.close();
        out.close();
    }


    //处理启动类
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK) {
                    Log.i("zayaaa","111");
                    Uri imageUri = popupwindow.getImageUri();
                    popupwindow.cropPhoto(imageUri);
                }
                break;
            case CHOOSE_PHOTO://打开相册
                // 判断手机系统版本号
                if (Build.VERSION.SDK_INT >= 19) {
                    // 4.4及以上系统使用这个方法处理图片
                    popupwindow.handleImageOnKitKat(data);
                } else {
                    // 4.4以下系统使用这个方法处理图片
                    popupwindow.handleImageBeforeKitKat(data);
                }
                break;

            case PICTURE_CUT:
                if(resultCode == RESULT_OK){
                    Uri cutImageUri = popupwindow.getCutImageUri();
//                    if(new File(getExternalCacheDir(),"cache_img.jpg").exists()){
//                        asyncUpOp(getExternalCacheDir()+"/cache_img.jpg");
//                    }else {
//                        String imagePath = popupwindow.getImagePath();
//                        asyncUpOp(imagePath);
//                    }

                    //if(cutImageUri.getPath().contains(".jpg")){

                    RequestOptions requestOptions = new RequestOptions()
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background)
                            .fallback(R.drawable.ic_launcher_background)
                            .override(200)
                            .circleCrop()
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE);
                    Log.i("zayaaa","path:"+cutImageUri.getPath());
                    Glide.with(this)
                            .load(cutImageUri)
                            .apply(requestOptions)
                            .into(imgzay_headPic);

                    // }


                }

                break;
            case 4:
                String sms = data.getStringExtra("EXTRA_MESSAGE");
                tv_jianjie.setText(sms);
                break;
            case 5:
                String sms1 = data.getStringExtra("job");
                tv_job.setText(sms1);
                break;
            case 6:
                String sms3 = data.getStringExtra("jobname");
                tv_jobname.setText(sms3);
                break;
        }
    }
    public void asyncUpOp(String filePath1) {
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        Log.i("zay","paz:"+filePath1);
        // okHttpClient = new OkHttpClient();
        //hostStr = "192.168.43.175";
        //创建上传文件的异步任务类的对象
        UpLoadFileTask task = new UpLoadFileTask(
                this,
                filePath1
        );
        //开始执行异步任务
        task.execute("http://"+hostIp+":8080/XianWanService/HeadPicSaveController");
    }
    public Uri makePicSmaller(String originalPic){
        File file = new File(getExternalCacheDir(),"suolue_image.jpg");
        Uri suolue = Uri.fromFile(file);
        try {
            FileOutputStream out = new FileOutputStream(file);
            BitmapFactory.Options ops = new BitmapFactory.Options();
            ops.inSampleSize = 8;
            //ops.inJustDecodeBounds = true;
            Bitmap bitmap2 = BitmapFactory.decodeFile(originalPic,ops);
            bitmap2.compress(Bitmap.CompressFormat.JPEG,100,out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return suolue;
    }
    //环信退出
    public void outchat(String userAccount){
        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.i("aaa","用户"+userAccount+"退出成功！！！！！");
                finish();
            }

            @Override
            public void onError(int i, String s) {
                Log.i("aaa","用户"+userAccount+"退出失败:"+i+".."+s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

}

