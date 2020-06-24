package com.wjx.android.wanandroidmvvm.base.view

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.billy.android.swipe.SmartSwipeBack
import com.hjq.bar.TitleBar
import com.hjq.bar.style.TitleBarLightStyle
import com.hjq.demo.R
import com.hjq.demo.action.SwipeAction
import com.hjq.demo.helper.ActivityStackManager
import com.hjq.demo.http.model.RequestHandler
import com.hjq.demo.http.server.ReleaseServer
import com.hjq.demo.http.server.TestServer
import com.hjq.demo.other.AppConfig
import com.hjq.demo.ui.activity.CrashActivity
import com.hjq.demo.ui.activity.HomeActivity
import com.hjq.http.EasyConfig
import com.hjq.http.config.IRequestServer
import com.hjq.toast.ToastInterceptor
import com.hjq.toast.ToastUtils
import com.hjq.umeng.UmengClient
import com.kingja.loadsir.core.LoadSir
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.tencent.bugly.crashreport.CrashReport
import com.wjx.android.mvvm.common.utils.Constant
import com.wjx.android.mvvm.common.utils.SPreference
import com.wjx.android.wanandroidmvvm.common.callback.EmptyCallBack
import com.wjx.android.wanandroidmvvm.common.callback.ErrorCallBack
import com.wjx.android.wanandroidmvvm.common.callback.LoadingCallBack
import leavesc.hello.dokv.DoKV
import leavesc.hello.dokv_imp.MMKVDoKVHolder
import okhttp3.OkHttpClient

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/22
 * Time: 14:27
 */
open class BaseApplication : Application() {
    companion object {
        lateinit var instance : BaseApplication
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        SPreference.setContext(applicationContext)
        initMode()
        LoadSir.beginBuilder()
            .addCallback(ErrorCallBack())
            .addCallback(LoadingCallBack())
            .addCallback(EmptyCallBack())
            .commit()

        initSDK(this)

    }

    private fun initMode() {
        var isNightMode: Boolean by SPreference(Constant.NIGHT_MODE, false)
        AppCompatDelegate.setDefaultNightMode(if (isNightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }

    /**
     * 初始化一些第三方框架
     */
    open fun initSDK(application: Application?) {

        //数据储存
        DoKV.init(MMKVDoKVHolder(application))

        // 友盟统计、登录、分享 SDK
        UmengClient.init(application)

        // 吐司工具类
        ToastUtils.init(application)

        // 设置 Toast 拦截器
        ToastUtils.setToastInterceptor(object : ToastInterceptor() {
            override fun intercept(toast: Toast, text: CharSequence): Boolean {
                val intercept = super.intercept(toast, text)
                if (intercept) {
                    Log.e("Toast", "空 Toast")
                } else {
                    Log.i("Toast", text.toString())
                }
                return intercept
            }
        })

        // 标题栏全局样式
        TitleBar.initStyle(object : TitleBarLightStyle(application) {
            override fun getBackground(): Drawable {
                return ColorDrawable(getColor(R.color.colorPrimary))
            }

            override fun getBackIcon(): Drawable {
                return getDrawable(R.drawable.ic_back_black)
            }
        })

        // Bugly 异常捕捉
        CrashReport.initCrashReport(application, AppConfig.getBuglyId(), false)

        // Crash 捕捉界面
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM)
                .enabled(true)
                .trackActivities(true)
                .minTimeBetweenCrashesMs(2000) // 重启的 Activity
                .restartActivity(HomeActivity::class.java) // 错误的 Activity
                .errorActivity(CrashActivity::class.java) // 设置监听器
                //.eventListener(new YourCustomEventListener())
                .apply()

        // 设置全局的 Header 构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context: Context?, layout: RefreshLayout? -> ClassicsHeader(context).setEnableLastTime(false) }
        // 设置全局的 Footer 构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context: Context?, layout: RefreshLayout? -> ClassicsFooter(context).setDrawableSize(20f) }

        // Activity 栈管理初始化
        ActivityStackManager.getInstance().init(application)

        // 网络请求框架初始化
        val server: IRequestServer
        server = if (AppConfig.isDebug()) {
            TestServer()
        } else {
            ReleaseServer()
        }
        EasyConfig.with(OkHttpClient()) // 是否打印日志
                .setLogEnabled(AppConfig.isDebug()) // 设置服务器配置
                .setServer(server) // 设置请求处理策略
                .setHandler(RequestHandler()) // 设置请求重试次数
                .setRetryCount(3) // 添加全局请求参数
                //.addParam("token", "6666666")
                // 添加全局请求头
                //.addHeader("time", "20191030")
                // 启用配置
                .into()

        // Activity 侧滑返回
        SmartSwipeBack.activitySlidingBack(application) { activity: Activity? ->
            if (activity is SwipeAction) {
                return@activitySlidingBack (activity as SwipeAction).isSwipeEnable
            }
            true
        }
    }
}