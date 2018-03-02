package com.base.sdk.app;

import android.app.Application;

import com.base.sdk.util.log.LogUtil;

/**
 * Created by Administrator on 2018/3/2/002.
 */

public class BaseApplication extends Application {



    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //内存泄露检测
//        if (LeakCanary.isInAnalyzerProcess(this)) return;
//        refWatcher = LeakCanary.install(this);
        LogUtil.init(true);//初始化Log打印，true表示显示打印，false表示不显示打印
//        EventBusUtil.openIndex();//开启Index加速
    }

    private static Application instance;
    public static Application getInstance() {
        return instance;
    }

    /**
     * 内存泄露检测
     */
//    private RefWatcher refWatcher;
//
//    public static RefWatcher getRefWatcher(Context context) {
//        MyApplication application = (MyApplication) context.getApplicationContext();
//        return application.refWatcher;
//    }
}
