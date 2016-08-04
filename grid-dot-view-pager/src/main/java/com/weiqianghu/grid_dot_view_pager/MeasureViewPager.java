package com.weiqianghu.grid_dot_view_pager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author hukuan
 * @ClassName: MeasureViewPager
 * @Description: TODO 测量viewpager的高度
 * @date 2015-5-25 下午6:12:43
 */
public class MeasureViewPager extends ViewPager {
    private static final String TAG = "MeasureViewPager";

    public MeasureViewPager(Context context) {
        super(context);
    }

    public MeasureViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        //下面遍历所有child的高度
        for (int i = 0,length=getChildCount(); i < length; i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec,
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) //采用最大的view的高度。
                height = h;
        }

	    heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height,
	        View.MeasureSpec.EXACTLY);
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	  }
}
