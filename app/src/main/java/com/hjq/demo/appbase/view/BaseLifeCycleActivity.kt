package com.wjx.android.wanandroidmvvm.base.view


import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hjq.base.BaseDialog
import com.hjq.demo.R
import com.hjq.demo.helper.ActivityStackManager
import com.hjq.demo.ui.activity.LoginActivity
import com.hjq.demo.ui.dialog.MessageDialog
import com.hjq.http.EasyLog
import com.kingja.loadsir.callback.SuccessCallback
import com.wjx.android.mvvm.common.state.State
import com.wjx.android.mvvm.common.state.StateType
import com.wjx.android.mvvm.common.utils.CommonUtil
import com.wjx.android.mvvm.viewmodel.BaseViewModel
import com.wjx.android.wanandroidmvvm.common.callback.EmptyCallBack
import com.wjx.android.wanandroidmvvm.common.callback.ErrorCallBack
import com.wjx.android.wanandroidmvvm.common.callback.LoadingCallBack

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/22
 * Time: 16:30
 */
abstract class BaseLifeCycleActivity<VM : BaseViewModel<*>> : BaseActivity() {
    protected lateinit var mViewModel: VM

    override fun initView() {
        showLoading()

        mViewModel = ViewModelProvider(this).get(CommonUtil.getClass(this))

        mViewModel.loadState.observe(this, observer)

        // 初始化View的Observer
        initDataObserver()
    }

    abstract fun initDataObserver()


    open fun showLoading() {
        loadService.showCallback(LoadingCallBack::class.java)
    }

    open fun showSuccess() {
        loadService.showCallback(SuccessCallback::class.java)
    }

    open fun showError(msg: String) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
        loadService.showCallback(ErrorCallBack::class.java)
    }

    open fun showTip(msg: String) {
        if (!TextUtils.isEmpty(msg)) {

            MessageDialog.Builder(this)
                    .setTitle(R.string.title) // 标题可以不用填写
                    .setMessage(msg)
                    .setConfirm(R.string.done)
                    .setCancel("取消") // 设置 null 表示不显示取消按钮
                    //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                    .setListener(object : MessageDialog.OnListener {
                        override fun onConfirm(dialog: BaseDialog) {
                            val intent1 = Intent(this@BaseLifeCycleActivity, LoginActivity::class.java)
                            startActivity(intent1)
                            ActivityStackManager.getInstance().finishAllActivities() // 销毁所有活动
                        }

                        override fun onCancel(dialog: BaseDialog) {}
                    })
                    .show()

            false
        }
        loadService.showCallback(SuccessCallback::class.java)
    }

    open fun showEmpty() {
        loadService.showCallback(EmptyCallBack::class.java)
    }

    /**
     * 分发应用状态
     */
    private val observer by lazy {
        Observer<State> {
            it?.let {
                EasyLog.json("===>"+it.code.toString())
                when (it.code) {
                    StateType.SUCCESS -> showSuccess()
                    StateType.LOADING -> showLoading()
                    StateType.ERROR -> showTip(it.message)
                    StateType.NETWORK_ERROR -> showError("网络出现问题啦")
                    StateType.TIP -> showTip(it.message)
                    StateType.EMPTY -> showEmpty()
                }
            }
        }
    }
}