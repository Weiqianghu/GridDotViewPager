package com.weiqianghu.grid_dot_view_pager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 2016/5/11.
 */
public class DotViewPagerAdapter<T> extends PagerAdapter {
    private Context mContext;
    private List<List> mPage = new ArrayList<>();
    private GridView mGridView;
    private int mNumColumns = 3;

    private int mItemLayoutId;

    private ConvertView<T> mConvertView;

    private AdapterView.OnItemClickListener mOnItemClickListener;

    private DotViewPagerAdapter(Context context) {
        this.mContext = context;
    }

    public DotViewPagerAdapter(Context context, List<List> page, int itemLayoutId, int numColumns, ConvertView<T> convertView, AdapterView.OnItemClickListener onItemClickListener) {
        this(context);
        this.mPage = page;
        this.mItemLayoutId = itemLayoutId;
        this.mNumColumns = numColumns;
        this.mConvertView = convertView;
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getCount() {
        return mPage.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gridview_layout, null);

        mGridView = (MeasureGridView) view.findViewById(R.id.grid_view);
        mGridView.setNumColumns(mNumColumns);
        mGridView.setAdapter(new CommonAdapter<T>(mContext, mPage.get(position), mItemLayoutId) {
            @Override
            public void convert(final ViewHolder helper, T item) {
                mConvertView.setConvertView(helper, item);
            }
        });
        if (mOnItemClickListener != null) {
            mGridView.setOnItemClickListener(mOnItemClickListener);
        }
        container.addView(view);
        return view;
    }

}
