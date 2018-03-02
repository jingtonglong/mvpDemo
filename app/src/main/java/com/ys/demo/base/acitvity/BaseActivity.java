package com.ys.demo.base.acitvity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.view.View;

import com.base.sdk.R;
import com.base.sdk.base.net.LifeCycleEvent;
import com.base.sdk.util.ActivityStackManager;
import com.base.sdk.util.CommonUtil;
import com.base.sdk.util.ToastUtil;
import com.base.sdk.util.log.LogUtil;

import org.zackratos.ultimatebar.UltimateBar;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;


/**
 * description: Activity的基类，包含Activity栈管理，状态栏/导航栏颜色设置，销毁时取消网络请求等
 * 子类需要进行ButterKnife绑定
 * 注：不将BaseActivity放入sdkmodule是因为bButterKnife使用问题
 */

public abstract class BaseActivity extends AbstractActivity implements IBaseActivity {


    //页面的堆栈管理
    private ActivityStackManager mStackManager;
    //状态栏导航栏颜色工具类
    private UltimateBar ultimateBar;

    //用于控制retrofit的生命周期，以便在destroy或其他状态时终止网络请求
    public PublishSubject<LifeCycleEvent> lifecycleSubject = PublishSubject.create();

    //该方法用于提供lifecycleSubject（相当于实现了IBaseView中的getLifeSubject抽象方法）。
    //方便Presenter中直接通过IBaseView获取lifecycleSubject，而不用每次都作为参数传递过去
    public PublishSubject<LifeCycleEvent> getLifeSubject() {
        return lifecycleSubject;
    }

    //一般的rxjava使用场景下，控制Observable的生命周期
    public <T> ObservableTransformer<T, T> controlLife(final LifeCycleEvent event) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable<LifeCycleEvent> lifecycleObservable = lifecycleSubject.filter(new Predicate<LifeCycleEvent>() {
                    @Override
                    public boolean test(LifeCycleEvent lifeCycleEvent) throws Exception {
                        //当生命周期为event状态时，发射事件
                        return lifeCycleEvent.equals(event);
                    }
                }).take(1);
                return upstream.takeUntil(lifecycleObservable);
            }
        };
    }
    protected Activity context;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        init();
        setContentLayout();//由具体的activity实现，设置内容布局ID
        ButterKnife.bind(this);
        initBarColor();//初始化状态栏/导航栏颜色，需在设置了布局后再调用
        initView();//由具体的activity实现，做视图相关的初始化
        obtainData();//由具体的activity实现，做数据的初始化
        initEvent();//由具体的activity实现，做事件监听的初始化
    }

    private void init() {
        mStackManager = ActivityStackManager.getInstance();
        mStackManager.pushOneActivity(this);

    }

    private void initBarColor() {
        int color = getResourceColor(R.color.colorPrimary);
        setBarColor(color, 0, color, 0);
    }


    public UltimateBar getUltimateBar() {
        if (ultimateBar == null) {
            ultimateBar = new UltimateBar(this);
        }
        return ultimateBar;
    }

    //设置状态栏、导航栏颜色，第二个参数控制透明度，布局内容不占据状态栏空间
    public void setBarColor(int statusColor, int statusAlpha, int navColor, int navAlpha) {
        getUltimateBar().setColorBar(statusColor, statusAlpha, navColor, navAlpha);
    }



    @Override
    protected void onPause() {
        lifecycleSubject.onNext(LifeCycleEvent.PAUSE);
        super.onPause();
    }

    @Override
    protected void onStop() {
        lifecycleSubject.onNext(LifeCycleEvent.STOP);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mStackManager.popOneActivity(this);
        super.onDestroy();
        lifecycleSubject.onNext(LifeCycleEvent.DESTROY);
    }

    /**
     * 隐藏输入法
     */
    public void hideInput() {
        View view = getWindow().peekDecorView();
        CommonUtil.hideSoftInput(getContext(), view);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public int getResourceColor(@ColorRes int colorId) {
        return ResourcesCompat.getColor(getResources(), colorId, null);
    }

    @Override
    public String getResourceString(@StringRes int stringId) {
        return getResources().getString(stringId);
    }

    @Override
    public String getResourceString(@StringRes int id, Object... formatArgs) {
        return getResources().getString(id, formatArgs);
    }

    @Override
    public Drawable getResourceDrawable(@DrawableRes int id) {
        return ResourcesCompat.getDrawable(getResources(), id, null);
    }

    @Override
    public void onLowMemory() {
        LogUtil.e("内存不足");
        super.onLowMemory();
    }

    public void showToast(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            ToastUtil.TextToast(getContext(),msg);
        }
    }

    /**
     * 显示进度
     */
    public void showLoadingDialog(String msg) {
        if (context != null) {
            if (pd == null) {
                pd = new ProgressDialog(context);
            }
            pd.setMessage(msg);
            pd.setCanceledOnTouchOutside(false);
            pd.show();
        }
    }

    /**
     * 关闭进度
     */
    public void dismissLoadingDialog() {
        if (context != null && pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }

}
