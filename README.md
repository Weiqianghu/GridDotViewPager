# GridDotViewPager

### 介绍
  这是一个类似美团首页频道的自定义控件，如下图,可以看到这个频道有三页，可以左右滑动，如果自己手写还是比较复杂，要花点时间的，所以我做了这样一个
自定义view，使用起来方便多了。

![image](https://raw.githubusercontent.com/Weiqianghu/GridDotViewPager/master/img/meituan.png)

### 使用方法

#### 在布局文件中声明

    <com.weiqianghu.grid_dot_view_pager.GridDotViewpager
        android:id="@+id/grid_view_pager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@android:color/white"
        app:columns="4"
        app:cycle="false">
    </com.weiqianghu.grid_dot_view_pager.GridDotViewpager>

#### 在Activity中设置

        mGridDotViewpager = (GridDotViewpager<Demo>) findViewById(R.id.grid_view_pager);
        mGridDotViewpager.setDataAndView(mDemos, R.layout.dot_gridview_item, new ConvertView<Demo>() {
            @Override
            public void setConvertView(ViewHolder helper, final Demo item) {
                helper.setImageResource(R.id.img, R.mipmap.food);
                helper.setText(R.id.title, item.title);
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.this.onItemClick(v, item);
                    }
                });
            }
        });

可以自定义每块的布局和点击事件，可以设置是否需要自动滑动，可以设置每行块的列数
详细用法可以看MainActivity的代码

#### 实现效果

![image](https://raw.githubusercontent.com/Weiqianghu/GridDotViewPager/master/img/simple.png)


