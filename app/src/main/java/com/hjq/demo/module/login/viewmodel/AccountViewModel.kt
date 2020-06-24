package com.hjq.demo.module.login.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wjx.android.mvvm.network.initiateRequest
import com.wjx.android.mvvm.viewmodel.BaseViewModel
import com.wjx.android.wanandroidmvvm.module.account.model.LoginResponse
import com.wjx.android.wanandroidmvvm.module.account.model.RegisterResponse
import com.wjx.android.wanandroidmvvm.module.account.repository.AccountRepository

class AccountViewModel(application: Application) : BaseViewModel<AccountRepository>(application) {

    // 使用协程 + Retrofit2.6以上版本
    val mLoginData: MutableLiveData<LoginResponse> = MutableLiveData()
    val mRegisterData: MutableLiveData<RegisterResponse> = MutableLiveData()

    fun loginCo(username: String, password: String) {
        initiateRequest({ mLoginData.value = mRepository.loginCo(username, password) }, loadState)
    }

    fun registerCo(username: String, password: String, repassword: String) {
        initiateRequest({ mRegisterData.value = mRepository.registerCo(username, password, repassword) }, loadState)
    }
}