package com.wjx.android.wanandroidmvvm.common.callback

import com.hjq.demo.R
import com.kingja.loadsir.callback.Callback

/**
 * Created with Android Studio.
 * Description:
 * @author: 王拣贤
 * @date: 2020/02/22
 * Time: 14:37
 */
class LoadingCallBack : Callback() {
    override fun onCreateView(): Int = R.layout.layout_loading
}