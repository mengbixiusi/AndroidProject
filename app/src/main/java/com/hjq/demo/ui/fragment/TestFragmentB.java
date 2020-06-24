package com.hjq.demo.ui.fragment;

import android.view.View;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.hjq.demo.R;
import com.hjq.demo.aop.SingleClick;
import com.hjq.demo.common.MyFragment;
import com.hjq.demo.http.glide.GlideApp;
import com.hjq.demo.ui.activity.HomeActivity;
import com.hjq.demo.ui.activity.TestActivity;
import com.hjq.widget.view.CountdownView;
import com.hjq.widget.view.SwitchButton;

import butterknife.BindView;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目自定义控件展示
 */
public final class TestFragmentB extends MyFragment<HomeActivity>
        implements SwitchButton.OnCheckedChangeListener {

    @BindView(R.id.iv_test_circle)
    PhotoView mCircleView;

    @BindView(R.id.sb_test_switch)
    SwitchButton mSwitchButton;
    @BindView(R.id.cv_test_countdown)
    CountdownView mCountdownView;

    public static TestFragmentB newInstance() {
        return new TestFragmentB();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test_b;
    }

    @Override
    protected void initView() {
        mSwitchButton.setOnCheckedChangeListener(this);

        setOnClickListener(R.id.cv_test_countdown);
    }

    @Override
    protected void initData() {
//        GlideApp.with(this)
//                .load(R.drawable.bg_launcher)
//                .circleCrop()
//                .into(mCircleView);

        mCircleView.setImageResource(R.drawable.bg_launcher);
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cv_test_countdown) {
            toast(R.string.common_code_send_hint);
            mCountdownView.start();

            startActivity(TestActivity.class);
        }
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    /**
     * {@link SwitchButton.OnCheckedChangeListener}
     */

    @Override
    public void onCheckedChanged(SwitchButton button, boolean isChecked) {
        toast(isChecked);
    }
}