package com.hjq.demo.aop;

import android.app.Application;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.hjq.demo.R;
import com.hjq.demo.bean.UserInfoDoKV;
import com.hjq.demo.common.MyApplication;
import com.hjq.demo.helper.ActivityStackManager;
import com.hjq.demo.ui.activity.LoginActivity;
import com.hjq.toast.ToastUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2020/01/11
 * desc   : 检测是否登录
 */
@Aspect
public class CheckLoginAspect {

    /**
     * 方法切入点
     */
    @Pointcut("execution(@com.hjq.demo.aop.CheckLogin * *(..))")
    public void method() {
    }

    /**
     * 在连接点进行方法替换
     */
    @Around("method() && @annotation(checkLogin)")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint, CheckLogin checkLogin) throws Throwable {
        if (UserInfoDoKV.get().getToken() == null || UserInfoDoKV.get().getToken().equals("")) {
            Intent intent = new Intent("com.gesoft.admin.loginout");
            MyApplication.getMContext().sendBroadcast(intent);
            return;
        }
        //执行原方法
        joinPoint.proceed();
    }
}