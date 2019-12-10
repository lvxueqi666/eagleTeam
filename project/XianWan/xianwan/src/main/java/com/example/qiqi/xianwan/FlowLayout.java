package com.example.qiqi.xianwan;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class FlowLayout extends ViewGroup {
    List<String> tags = new ArrayList<>();
    int count = 0;

    public FlowLayout(Context context) {
        super(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //每一行的子View
    private List<List<View>> mAllChildViews = new ArrayList<>();
    //每一行的高度
    private List<Integer> mLineHeight = new ArrayList<>();

    /**
     * 测量所有子View大小,确定ViewGroup的宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //由于onMeasure会执行多次,避免重复的计算控件个数和高度,这里需要进行清空操作
        mAllChildViews.clear();
        mLineHeight.clear();

        //父控件传进来的宽度和高度以及对应的测量模式，获取总宽度,包含padding值
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //除去左右padding的宽度,作为比较是否换行的宽度
        //MeasureSpec.EXACTLY模式时适用，MeasureSpec.AT_MOST模式使用sizeWidth
        int noPaddingWidth = sizeWidth - getPaddingLeft() - getPaddingRight();

        //如果当前ViewGroup的宽高为wrap_content的情况
        int width = getPaddingLeft() + getPaddingRight();//自己测量的宽度,padding值+子view宽度
        int height = getPaddingTop() + getPaddingBottom();//自己测量的高度
        //记录当前行的宽度和高度
        int lineWidth = 0;
        int lineHeight = 0;

        //记录当前行的子view
        List<View> lineViews = new ArrayList<>();


        //获取FlowTagLayout中子view的个数
        int childCount = getChildCount();
        //遍历子View
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            //测量子View的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //得到子View的LayoutParams
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            //子View占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            //子View占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            //判断是否换行(MeasureSpec.EXACTLY和MeasureSpec.AT_MOST要区别处理)
            if (lineWidth + childWidth <= (modeWidth == MeasureSpec.EXACTLY ? noPaddingWidth : sizeWidth)) {//不换行
                //累加行宽
                lineWidth += childWidth;
                //得到最大行高
                lineHeight = Math.max(lineHeight, childHeight);
                //添加子View到当前行lineViews
                lineViews.add(child);
            } else {//换行
                //当前行的子view存储到mAllChildViews
                mAllChildViews.add(lineViews);
                //记录上一行的行高
                mLineHeight.add(lineHeight);
                //对比得到最大的宽度
                width = Math.max(width, lineWidth);
                //记录行高
                height += lineHeight;
                //新建下一行的子View集合
                lineViews = new ArrayList<>();
                //重置换行后的行宽
                lineWidth = childWidth;
                //重置换行后的行高
                lineHeight = childHeight;
                //添加到下一行的View集合
                lineViews.add(child);
            }
            //处理最后一个子View的情况
            if (i == childCount - 1) {
                //当前行的子view集合存储到mAllChildViews
                mAllChildViews.add(lineViews);
                //记录行高
                mLineHeight.add(lineHeight);
                //对比得到最大的宽度，最终的宽度
                width = Math.max(width, lineWidth);
                //记录行高，最终的高度
                height += lineHeight;
            }
            child.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView tv = (TextView) view;
                    if(count < 3){
                        if(tv.getBackground().getCurrent().getConstantState().equals(getResources().getDrawable(R.drawable.radius).getConstantState())){
                            tv.setBackgroundResource(R.drawable.shape);
                            count++;
                        }else{
                            tv.setBackgroundResource(R.drawable.radius);
                            count--;
                        }
                    }else{
                        if(tv.getBackground().getCurrent().getConstantState().equals(getResources().getDrawable(R.drawable.shape).getConstantState())){
                            tv.setBackgroundResource(R.drawable.radius);
                            count--;
                        }
                    }

                    String tag = tv.getText().toString().trim();
                    tags.add(tag);
                }
            });
        }
        //wrap_content
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width,
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        //FlowTagLayout的Padding值
        int left = getPaddingLeft();
        int top = getPaddingTop();
        //遍历所有子view
        for (int i = 0; i < mAllChildViews.size(); i++) {
            //每行行高
            int lineHeight = mLineHeight.get(i);
            //每一行内的子View
            List<View> viewList = mAllChildViews.get(i);
            for (int j = 0; j < viewList.size(); j++) {
                View view = viewList.get(j);
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
                int vl = left + marginLayoutParams.leftMargin;
                int vt = top + marginLayoutParams.topMargin;
                int vr = vl + view.getMeasuredWidth();
                int vb = vt + view.getMeasuredHeight();
                view.layout(vl, vt, vr, vb);
                left += view.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}

