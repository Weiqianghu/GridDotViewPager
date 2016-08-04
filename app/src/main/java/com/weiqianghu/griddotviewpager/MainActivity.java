package com.weiqianghu.griddotviewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.weiqianghu.grid_dot_view_pager.ConvertView;
import com.weiqianghu.grid_dot_view_pager.GridDotViewpager;
import com.weiqianghu.grid_dot_view_pager.HeaderItemClickListener;
import com.weiqianghu.grid_dot_view_pager.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HeaderItemClickListener<Demo> {

    private GridDotViewpager<Demo> mGridDotViewpager;
    private List<Demo> mDemos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        initViews();
    }

    private void initData() {
        for (int i = 0; i < 12; i++) {
            Demo demo = new Demo("标题" + i, "");
            mDemos.add(demo);
        }
    }

    private void initViews() {
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
    }

    @Override
    public void onItemClick(View view, Demo model) {
        Toast.makeText(MainActivity.this, model.title, Toast.LENGTH_SHORT).show();
    }
}
