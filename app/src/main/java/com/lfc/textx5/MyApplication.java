package com.lfc.textx5;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

/**
 * Created by LFC
 * on 2018/8/3.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //增加这句话
        QbSdk.initX5Environment(this, cb);
//        ExceptionHandler.getInstance().initConfig(this);
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                Log.d("app", "onDownloadFinish");
            }

            @Override
            public void onInstallFinish(int i) {
                Log.d("app", "onInstallFinish");
            }

            @Override
            public void onDownloadProgress(int i) {
                Log.d("app", "onDownloadProgress:" + i);
            }
        });
    }

    /**
     * 搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
     */
    QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

        @Override
        public void onViewInitFinished(boolean arg0) {
            //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            Log.e("APPAplication", " onViewInitFinished is " + arg0);
        }

        @Override
        public void onCoreInitFinished() {
            Log.e("APPAplication", " onCoreInitFinished");
        }
    };

}
