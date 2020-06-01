package com.example.qiqi.xianwan.ketang;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qiqi.xianwan.R;


/**
 * @describe 自定义LinearLayout，整个搜索布局都在这里
 * @author yangliu
 */

public class Search_LinearLayout extends LinearLayout {

    private Context context;

    /*UI组件*/
    private TextView tv_clear;//清空搜索历史
    private EditText et_search;//搜索框
    private TextView tv_tip;//搜索提示，eg:"搜索历史"或"搜索结果"
    private ImageView iv_search;//搜索按钮
    private ImageView back;//返回按钮
    private ScrollView scroll_showSearchResult;

    /*列表及其适配器*/
    private Search_ListView listView;
    private BaseAdapter adapter;

    /*数据库变量*/
    private RecordSQLiteOpenHelper helper;
    private SQLiteDatabase db;

    public Search_LinearLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public Search_LinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public Search_LinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    /**
     * 初始化搜索框
     * */
    private void init(){

        //初始化UI组件
        initView();

        //实例化数据库SQLiteOpenHelper子类对象
        helper = new RecordSQLiteOpenHelper(context);

        //第一次进入时，查询所有的历史记录
        queryData("");

        //"清空搜索历史"按钮
        tv_clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //清空数据库
                deleteData();
                queryData("");
            }
        });

        scroll_showSearchResult.setVisibility(VISIBLE);

        //搜索框的文本变化实时监听
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //当输入框为空时，再显示搜索历史
                if (charSequence.toString().length()==0) {
                    scroll_showSearchResult.setVisibility(VISIBLE);
                }
            }

            //输入后调用的该方法
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() == 0){
                    //若搜索框为空，则模糊搜索空字符，即显示所有的搜索历史
                    tv_tip.setText("搜索历史");
                }else {
                    tv_tip.setText("搜索结果");
                }
                //每次输入后都查询数据库并显示
                //根据输入的值去模糊查询数据库中有没有数据
                String tempName = et_search.getText().toString().trim();
                queryData(tempName);
                // 输入框，输入时将光标移到最后
                int pos = editable.length();
                Selection.setSelection(editable, pos);
            }
        });

        //键盘监听事件（回车键改成搜索键）
        et_search.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                //软键盘上的回车键，改成搜索键
                if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    // 隐藏键盘，这里getCurrentFocus()需要传入Activity对象，如果实际不需要的话就不用隐藏键盘了，免得传入Activity对象，这里就先不实现了
                    ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(view.getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    //判空操作
                    if (et_search.getText().toString().trim().length()==0){
                        Toast.makeText(context,"请输入搜索内容", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    //按完搜索键后，将当前查询的关键字保存起来，如果关键字已经存在，就不执行保存
                    boolean hasData = hasData(et_search.getText().toString().trim());
                    if (!hasData) {
                        insertData(et_search.getText().toString().trim());//数据库不存在字段，就保存
                        queryData("");
                    }
                    //根据输入的内容模糊查询商品，并跳转到另一个小界面，这个需求根据需求实现
                    Toast.makeText(context,"点击搜索", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        //列表监听
        //即当用户点击搜索历史里的字段后，会直接将结果当作搜索字段进行搜素
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                et_search.setText(name);
                scroll_showSearchResult.setVisibility(INVISIBLE);//选中item时，隐藏搜索历史
                Toast.makeText(context,"选择搜索" + name, Toast.LENGTH_SHORT).show();
            }
        });


        //点击搜索按钮
        iv_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_search.getText().toString().trim().length() == 0){
                    Toast.makeText(context,et_search.getText(), Toast.LENGTH_SHORT).show();

                    return;
                }
                boolean hasData = hasData(et_search.getText().toString().trim());
                if (!hasData){
                    insertData(et_search.getText().toString().trim());

                    //搜索后显示数据库里所有搜索历史是为了测试
                    queryData("");
                }
                //根据输入的内容模糊查询商品，并跳转到另一个界面，这个根据需求实现
            //    Toast.makeText(context, "搜索" + et_search.getText(), Toast.LENGTH_SHORT).show();
              //  Intent intent = new Intent(getContext(), SearchResultActivity.class);
             //   intent.putExtra("content",et_search.getText().toString().trim());
              //  context.startActivity(intent);
                Intent intent=new Intent(getContext(),Search_Result.class);
                intent.putExtra("introduce",et_search.getText().toString().trim());
                context.startActivity(intent);
            }
        });

    }

    /**
     * 查询数据库是否包含该数据
     * */
    private boolean hasData(String s) {
        //从record这个表里找到name = s的id
        Cursor cursor = helper.getReadableDatabase().rawQuery("select id as _id,name from records where name =?", new String[]{s});
        //判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 清空数据
     * */
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");//删除表
        db.close();
    }

    /**
     * 查询数据,并显示在ListView列表上
     * */
    private void queryData(String s) {
        //模糊搜索
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + s + "%' order by id desc",null);
//                "select id as _id from records",null);

        //创建adapter适配器对象，装入模糊搜索的结果
        adapter = new SimpleCursorAdapter(context,android.R.layout.simple_list_item_1,cursor,new String[] {"name",
        },new int[] {android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        //设置适配器
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * 插入数据
     * */
    private void insertData(String s) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values ('" + s + "')");
        db.close();
    }
    /**
     * 初始化组件
     * */
    private void initView() {
        LayoutInflater.from(context).inflate(R.layout.search_layout_ketang,this);
        et_search = findViewById(R.id.et_search);
        tv_clear = findViewById(R.id.tv_clear);
        tv_tip = findViewById(R.id.tv_tip);
        listView = findViewById(R.id.listView);
        iv_search = findViewById(R.id.iv_search);
        back = findViewById(R.id.back);
        scroll_showSearchResult = findViewById(R.id.scrllView_search);
        scroll_showSearchResult.setVisibility(INVISIBLE);
    }

}

