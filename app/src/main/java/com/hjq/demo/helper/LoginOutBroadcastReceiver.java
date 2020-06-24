package com.hjq.demo.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.FragmentActivity;

import com.hjq.base.BaseDialog;
import com.hjq.demo.ui.activity.LoginActivity;
import com.hjq.demo.ui.dialog.MessageDialog;
import com.hjq.toast.ToastUtils;

public class LoginOutBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {

        ToastUtils.show("别忘了清除登录信息");
        new MessageDialog.Builder((FragmentActivity) context)
                .setTitle("提示") // 标题可以不用填写
                .setMessage("请先登录")
                .setConfirm("确定")
                .setCancel("取消") // 设置 null 表示不显示取消按钮
                //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                .setListener(new MessageDialog.OnListener() {

                    @Override
                    public void onConfirm(BaseDialog dialog) {

                        Intent intent1 = new Intent(context, LoginActivity.class);
                        context.startActivity(intent1);
                        ActivityStackManager.getInstance().finishAllActivities();  // 销毁所有活动
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {


                    }
                })
                .show();
    }
}
