package com.hjq.demo.ui.activity;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.hjq.demo.R;
import com.hjq.demo.bean.HeaderViewBean;
import com.hjq.demo.common.MyActivity;
import com.hjq.demo.ui.adapter.GridViewAdapter;
import com.hjq.demo.ui.adapter.MyViewPagerAdapter;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 可进行拷贝的副本
 */
public final class TestActivity extends MyActivity {

    ViewPager mViewpager;
    ArrayList mViewPagerGridList;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        mViewpager = findViewById(R.id.viewpager);
        PageIndicatorView pageIndicatorView = findViewById(R.id.pageIndicatorView);

        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {/*empty*/}

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {/*empty*/}
        });

    }

    @Override
    protected void initData() {

        List<HeaderViewBean> mDatas = new ArrayList<HeaderViewBean>();
        mDatas.add(new HeaderViewBean("美食", R.mipmap.ic_launcher));
        mDatas.add(new HeaderViewBean("电影", R.mipmap.ic_launcher));
        mDatas.add(new HeaderViewBean("酒店", R.mipmap.ic_launcher));
        mDatas.add(new HeaderViewBean("KTV", R.mipmap.ic_launcher));
        mDatas.add(new HeaderViewBean("外卖", R.mipmap.ic_launcher));
        mDatas.add(new HeaderViewBean("美女6", R.mipmap.ic_launcher));
        mDatas.add(new HeaderViewBean("美女7", R.mipmap.ic_launcher));
        mDatas.add(new HeaderViewBean("美女8", R.mipmap.ic_launcher));
        mDatas.add(new HeaderViewBean("帅哥", R.mipmap.ic_launcher));
        mDatas.add(new HeaderViewBean("帅哥2", R.mipmap.ic_launcher));
        mDatas.add(new HeaderViewBean("帅哥3", R.mipmap.ic_launcher));
        mDatas.add(new HeaderViewBean("帅哥4", R.mipmap.ic_launcher));
        mDatas.add(new HeaderViewBean("帅哥5", R.mipmap.ic_launcher));
        mDatas.add(new HeaderViewBean("帅哥6", R.mipmap.ic_launcher));
        mDatas.add(new HeaderViewBean("帅哥7", R.mipmap.ic_launcher));
        mDatas.add(new HeaderViewBean("帅哥8", R.mipmap.ic_launcher));
        mDatas.add(new HeaderViewBean("帅哥9", R.mipmap.ic_launcher));
        mDatas.add(new HeaderViewBean("帅哥10", R.mipmap.ic_launcher));
        mDatas.add(new HeaderViewBean("帅哥11", R.mipmap.ic_launcher));
        mDatas.add(new HeaderViewBean("帅哥12", R.mipmap.ic_launcher));
        LayoutInflater inflater = LayoutInflater.from(TestActivity.this);

        //初始化ViewPagerList : private List<View> mViewPagerGridList;
        mViewPagerGridList = new ArrayList<View>();

//塞GridView至ViewPager中：
        ViewPager mViewpager=new ViewPager(TestActivity.this);
        int pageSize = 5 * 2;
//一共的页数等于 总数/每页数量，并取整。
        int pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);
        final List<View> viewpagerList = new ArrayList<View>();
        for (int index = 0; index < pageCount; index++) {
            //每个页面都是inflate出一个新实例
            GridView grid = (GridView) inflater.inflate(R.layout.item_viewpager, mViewpager, false);
            grid.setAdapter(new GridViewAdapter(TestActivity.this, mDatas, index));
            viewpagerList.add(grid);
        }
//给ViewPager设置Adapter
        mViewpager.setAdapter(new MyViewPagerAdapter(viewpagerList));
//将ViewPager作为HeaderView设置给ListView
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics()));
        mViewpager.setLayoutParams(params);


    }
}