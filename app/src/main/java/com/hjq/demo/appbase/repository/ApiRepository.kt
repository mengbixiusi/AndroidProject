package com.wjx.android.wanandroidmvvm.base.repository

import com.hjq.demo.http.ApiService
import com.wjx.android.mvvm.network.RetrofitFactory
import com.wjx.android.mvvm.repository.BaseRepository

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/25
 * Time: 20:40
 */
abstract class ApiRepository : BaseRepository() {
    protected val apiService: ApiService by lazy {
        RetrofitFactory.instance.create(ApiService::class.java)
    }
}