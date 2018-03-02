package com.ys.demo.mvp.model;


import com.base.sdk.base.model.BaseModel;
import com.base.sdk.base.net.LifeCycleEvent;
import com.ys.demo.entity.UserEntity;
import com.ys.demo.net.RetrofitUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.subjects.PublishSubject;


/**
 * Created by Administrator on 2018/3/1/001.
 */

public class LoginModel extends BaseModel {

    public LoginModel() {
        super();
    }

    public void login(String username, String password, Observer<List<UserEntity>> observer, PublishSubject<LifeCycleEvent> lifecycleSubject) {
        Observable<List<UserEntity>> observable = RetrofitUtil.getmLocalService().login("login", username, password);
        RetrofitUtil.composeToSubscribe(observable, observer, lifecycleSubject);
    }

}
