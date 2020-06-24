package com.wjx.android.wanandroidmvvm.common.callback

import com.hjq.demo.R
import com.kingja.loadsir.callback.Callback

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/25
 * Time: 19:26
 */

class EmptyCallBack : Callback() {
    override fun onCreateView(): Int = R.layout.layout_empty
}