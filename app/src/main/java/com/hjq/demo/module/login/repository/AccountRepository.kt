package com.wjx.android.wanandroidmvvm.module.account.repository

import androidx.lifecycle.MutableLiveData
import com.wjx.android.mvvm.common.state.State
import com.wjx.android.mvvm.network.dataConvert
import com.wjx.android.wanandroidmvvm.base.repository.ApiRepository
import com.wjx.android.wanandroidmvvm.module.account.model.LoginResponse
import com.wjx.android.wanandroidmvvm.module.account.model.RegisterResponse

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/01
 * Time: 19:59
 */
class AccountRepository(val loadState: MutableLiveData<State>) : ApiRepository() {

    // 使用协程 + Retrofit2.6
    suspend fun loginCo(username: String, password: String): LoginResponse {
        return apiService.onLoginCo(username, password).dataConvert(loadState)
    }

    suspend fun registerCo(
        username: String,
        password: String,
        repassword: String
    ): RegisterResponse {
        return apiService.onRegisterCo(username, password, repassword).dataConvert(loadState)
    }
}