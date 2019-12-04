package com.example.qiqi.xianwan.homeadapter;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;



import java.util.HashMap;

/**
 * @ClassName: RecyclerViewSpacesItemDecoration
 * @Description: RecyclerView间距工具类
 * @Author: likw
 * @CreateDate: 2019/8/27 17:31
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/8/27 17:31
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class RecyclerViewSpacesItemDecoration extends RecyclerView.ItemDecoration {

    private HashMap<String, Integer> mSpaceValueMap;
    public static final String TOP_DECORATION = "top_decoration";
    public static final String BOTTOM_DECORATION = "bottom_decoration";
    public static final String LEFT_DECORATION = "left_decoration";
    public static final String RIGHT_DECORATION = "right_decoration";

    public RecyclerViewSpacesItemDecoration(HashMap<String, Integer> mSpaceValueMap) {
        this.mSpaceValueMap = mSpaceValueMap;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (mSpaceValueMap.get(TOP_DECORATION) != null)
            outRect.top = mSpaceValueMap.get(TOP_DECORATION);
        if (mSpaceValueMap.get(LEFT_DECORATION) != null)

            outRect.left = mSpaceValueMap.get(LEFT_DECORATION);
        if (mSpaceValueMap.get(RIGHT_DECORATION) != null)
            outRect.right = mSpaceValueMap.get(RIGHT_DECORATION);
        if (mSpaceValueMap.get(BOTTOM_DECORATION) != null)

            outRect.bottom = mSpaceValueMap.get(BOTTOM_DECORATION);
    }
}
