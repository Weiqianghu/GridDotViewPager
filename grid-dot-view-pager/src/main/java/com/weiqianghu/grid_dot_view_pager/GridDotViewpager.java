package com.weiqianghu.grid_dot_view_pager;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.LinearLayout;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by huweiqiang on 2016/5/11.
 */
public class GridDotViewpager<T> extends LinearLayout {
    private List<T> mData = new ArrayList<>();

    private int mNumColumns = 3;

    private boolean mInited = false;
    private DotViewPager mDotViewpager;
    private DotViewPagerAdapter mDotViewPagerAdapter;
    private List<List> mPageList = new ArrayList<>();

    public GridDotViewpager(Context context) {
        this(context, null);
    }


    public GridDotViewpager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridDotViewpager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }


    private int mItemLayoutId;
    private ConvertView<T> mConvertView;

    public void setDataAndView(List<T> data, int itemLayoutId, ConvertView<T> convertView) {
        setData(data);
        mItemLayoutId = itemLayoutId;
        this.mConvertView = convertView;
        setPageSize();

        if (mDotViewPagerAdapter == null) {
            mDotViewPagerAdapter = new DotViewPagerAdapter(getContext(), mPageList, mItemLayoutId, mNumColumns, mConvertView, mOnItemClickListener);
            mDotViewpager.setAdapter(mDotViewPagerAdapter);
        } else {
            mDotViewpager.notifyDataSetChanged();
        }
        if (isCycle) {
            mDotViewpager.reStartCycle();
        }
    }

    public void setCycle(boolean cycle) {
        if (mInited) {
            throw new RuntimeException("setCycle 必须在 setDataAndView 之前设置");
        }
        this.isCycle = cycle;
    }

    public void notifyDataSetChanged() {
        mDotViewPagerAdapter.notifyDataSetChanged();
    }

    public void setNumColumns(int numColumns) {
        if (mInited) {
            throw new RuntimeException("setNumColumns 必须在 setDataAndView 之前设置");
        }
        if (numColumns < 1) {
            mNumColumns = 3;
            return;
        } else if (numColumns > 10) {
            mNumColumns = 10;
            return;
        }

        mNumColumns = numColumns;
    }

    private void init(AttributeSet attrs) {
        mDotViewpager = new DotViewPager(getContext());
        this.addView(mDotViewpager);

        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.GridDotViewpager);

        mNumColumns = ta.getInt(R.styleable.GridDotViewpager_columns, 3) < 3 ? 3 : ta.getInt(R.styleable.GridDotViewpager_columns, 3);
        isCycle = ta.getBoolean(R.styleable.GridDotViewpager_cycle, false);

        ta.recycle();
    }

    private void setPageSize() {
        mPageList.clear();
        List list = new ArrayList<>();
        for (int i = 0, length = mData.size(); i < length; i++) {
            if (i % (mNumColumns * 2) == 0) {
                list = new ArrayList<>();
                mPageList.add(list);
            }
            list.add(mData.get(i));
        }
    }

    private void setData(List<T> data) {
        mData.clear();
        mData.addAll(data);
    }


    /**
     * 设置后点击事件响应不够灵敏
     */
    private AdapterView.OnItemClickListener mOnItemClickListener;

    @Deprecated
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        if (mInited) {
            throw new RuntimeException("setOnItemClickListener 必须在 setDataAndView 之前设置");
        }
        this.mOnItemClickListener = onItemClickListener;
    }


    private boolean isCycle = false;


}
