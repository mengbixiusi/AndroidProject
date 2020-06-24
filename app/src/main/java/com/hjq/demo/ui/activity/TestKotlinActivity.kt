package com.hjq.demo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.Observer
import com.hjq.demo.R
import com.hjq.demo.common.MyActivity
import com.hjq.demo.helper.ChangeThemeEvent
import com.hjq.demo.module.login.view.RegisterTestActivity
import com.hjq.demo.module.login.viewmodel.AccountViewModel
import com.hjq.toast.ToastUtils
import com.wjx.android.mvvm.common.utils.ColorUtil
import com.wjx.android.mvvm.common.utils.startActivity
import com.wjx.android.wanandroidmvvm.base.view.BaseLifeCycleActivity
import kotlinx.android.synthetic.main.activity_login_test.*
import org.greenrobot.eventbus.Subscribe

class TestKotlinActivity : BaseLifeCycleActivity<AccountViewModel>(), View.OnClickListener {
    override fun getLayoutId(): Int = R.layout.activity_login_test

    override fun initView() {
        super.initView()
        button_login.setOnClickListener(this)
        register_text.setOnClickListener(this)
        ivBack.setOnClickListener(this)
        initColor()

        showSuccess()
    }

    private fun initColor() {
        //login_background.setBackgroundColor(ColorUtil.getColor(this))
        button_login.setTextColor(ColorUtil.getColor(this))
    }

    override fun initDataObserver() {
        mViewModel.mLoginData.observe(this, Observer {
            it?.let { loginResponse ->
//                UserInfo.instance.loginSuccess(
//                        loginResponse.username,
//                        loginResponse.id.toString(),
//                        loginResponse.collectIds
//                )
//                finish()


                ToastUtils.show(loginResponse.username)
            }
        })
    }

    override fun showCreateReveal(): Boolean = true

    override fun showDestroyReveal(): Boolean = false

    override fun onBackPressed() = finish()

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_login -> {
                mViewModel.loginCo(account_text.text.toString(), password_text.text.toString())

            }
            R.id.register_text -> {
                startActivity<RegisterTestActivity>(this)
                finish()
            }
            R.id.ivBack -> {
                onBackPressed()
            }
        }
    }

    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        initColor()
    }
}