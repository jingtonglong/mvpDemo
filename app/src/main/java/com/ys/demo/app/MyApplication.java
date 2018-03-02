package com.ys.demo.app;


import com.base.sdk.app.BaseApplication;
import com.ys.demo.net.RetrofitUtil;

/**
 * date:    2017/9/13
 * description: 全局初始化操作
 *
 * 开发框架的各个模块用法与介绍，请到我的博客 http://www.jianshu.com/u/2ebe42698573 进行查看
 *
 */

public class MyApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();

        RetrofitUtil.init(this);//初始化retrofit
    }

}
