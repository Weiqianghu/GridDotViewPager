package com.weiqianghu.grid_dot_view_pager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;


public class DotViewPager extends FrameLayout {
    private MeasureViewPager mViewPager;
    private PagerAdapter mTopViewpagerAdapter;
    private LinearLayout mIndicatorLayout;
    private View mTopView;
    private boolean isCycleDot = false;
    private int selected = 0;
    private Thread cycleThread;

    public DotViewPager(Context context) {
        this(context, null);
    }

    public DotViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mTopView = LayoutInflater.from(context).inflate(R.layout.viewpager, this);
        mIndicatorLayout = (LinearLayout) mTopView.findViewById(R.id.indicator_viewpager);

        mViewPager = (MeasureViewPager) mTopView.findViewById(R.id.top_viewpager);
        mViewPager.setOffscreenPageLimit(3);
    }

    private LinearLayout.LayoutParams getParam() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.leftMargin = 20;
        params.rightMargin = 20;
        params.topMargin = 5;
        params.bottomMargin = 5;
        return params;
    }

    public void setAdapter(PagerAdapter topViewpagerAdapter) {
        this.mTopViewpagerAdapter = topViewpagerAdapter;
        initData();
    }

    public void initData() {
        if (mTopViewpagerAdapter != null) {
            mViewPager.setAdapter(mTopViewpagerAdapter);
            mViewPager.setOnPageChangeListener(new pageChange());
            setDotView();
        }
    }

    public void notifyDataSetChanged() {
        setDotView();
        mTopViewpagerAdapter.notifyDataSetChanged();
        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                mViewPager.requestLayout();
            }
        });
    }

    private void setDotView() {
        int count = mTopViewpagerAdapter.getCount();
        mIndicatorLayout.removeAllViews();
        if (count > 1) {
            for (int i = 0; i < count; i++) {
                ImageView view = new ImageView(getContext());
                view.setScaleType(ScaleType.FIT_XY);
                if (i == selected) {
                    setBackground(view, getResources()
                            .getDrawable(R.drawable.customer_service_dot_d30775_7dp));
                } else {
                    setBackground(view, getResources()
                            .getDrawable(R.drawable.customer_service_dot_e9e9e9_7dp));
                }
                mIndicatorLayout.addView(view, getParam());
            }
        }
    }

    class pageChange implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int status) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            if (mIndicatorLayout == null) {
                return;
            }
            selected = position;
            int num = mIndicatorLayout.getChildCount();
            if (num <= 1) {
                return;
            }
            for (int i = 0; i < num; i++) {
                if (i == position) {
                    setBackground(mIndicatorLayout.getChildAt(i), getResources().getDrawable(R.drawable.customer_service_dot_d30775_7dp));
                } else {
                    setBackground(mIndicatorLayout.getChildAt(i), getResources().getDrawable(R.drawable.customer_service_dot_e9e9e9_7dp));
                }
            }
        }
    }

    public void stopCycle() {
        isCycleDot = false;
        mHandler.removeMessages(0x0000);
        if (cycleThread != null && !cycleThread.isInterrupted()) {
            cycleThread.interrupt();
        }
    }

    public void reStartCycle() {
        startCycle();
    }

    private void startCycle() {
        if (isCycleDot) {
            return;
        }
        isCycleDot = true;
        cycleThread = new Thread() {
            @Override
            public void run() {
                while (isCycleDot) {
                    try {
                        Thread.sleep(5000);
                        mHandler.sendEmptyMessage(0x0000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        };
        cycleThread.start();
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x0000 && isCycleDot) {
                if (mTopViewpagerAdapter == null) {
                    return;
                }
                if (mTopViewpagerAdapter.getCount() <= 1) {
                    isCycleDot = false;
                    return;
                }
                if (mTopViewpagerAdapter.getCount() - 1 <= selected) {
                    selected = 0;
                } else {
                    selected++;
                }
                mViewPager.setCurrentItem(selected);
            }
        }
    };

    private static void setBackground(View view, Drawable drawable) {
        int left = view.getPaddingLeft();
        int right = view.getPaddingRight();
        int top = view.getPaddingTop();
        int bottom = view.getPaddingBottom();
        if (Build.VERSION.SDK_INT >= 16) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
        view.setPadding(left, top, right, bottom);
    }
}
