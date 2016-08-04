package com.weiqianghu.grid_dot_view_pager;

import android.view.View;

/**
 * Created by huweiqiang on 2016/7/27.
 */
public interface HeaderItemClickListener<T> {
    void onItemClick(View view, T model);
}
