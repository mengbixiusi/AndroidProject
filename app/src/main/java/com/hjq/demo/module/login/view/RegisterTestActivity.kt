package com.hjq.demo.module.login.view

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.hjq.demo.R
import com.hjq.demo.helper.ChangeThemeEvent
import com.hjq.demo.module.login.viewmodel.AccountViewModel
import com.hjq.demo.ui.activity.LoginActivity
import com.wjx.android.mvvm.common.utils.ColorUtil
import com.wjx.android.mvvm.common.utils.startActivity
import com.wjx.android.wanandroidmvvm.base.view.BaseLifeCycleActivity
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register_test.*
import org.greenrobot.eventbus.Subscribe

class RegisterTestActivity : BaseLifeCycleActivity<AccountViewModel>(), View.OnClickListener {
    override fun getLayoutId(): Int = R.layout.activity_register_test

    override fun initView() {
        super.initView()
        button_register.setOnClickListener(this)
        login_text.setOnClickListener(this)
        ivBack.setOnClickListener(this)
        initColor()
        showSuccess()
    }

    private fun initColor() {
        //register_background.setBackgroundColor(ColorUtil.getColor(this))
        button_register.setTextColor(ColorUtil.getColor(this))
    }

    override fun initDataObserver() {
        mViewModel.mRegisterData.observe(this, Observer {
            it.let {
                Toast.makeText(this, "注册成功->保存用户信息", Toast.LENGTH_SHORT).show()
//                UserInfo.instance.loginSuccess(it.username, it.id.toString(), it.collectIds)
//                finish()
            }
        })
    }

    override fun showCreateReveal(): Boolean = true

    override fun showDestroyReveal(): Boolean = false

    override fun onBackPressed() = finish()

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_register -> {
                mViewModel.registerCo(
                    account_text.text.toString(),
                    password_text.text.toString(),
                    repassword_text.text.toString()
                )
            }
            R.id.login_text -> {
                startActivity<LoginActivity>(this)
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
