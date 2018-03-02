package com.ys.demo.mvp.presenter;

import android.text.TextUtils;

import com.base.sdk.base.net.SimpleObserver;
import com.base.sdk.base.present.BasePresenter;
import com.ys.demo.mvp.model.LoginModel;
import com.ys.demo.entity.UserEntity;
import com.ys.demo.mvp.view.api.ILoginView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/1/001.
 */

public class LoginPresent extends BasePresenter<ILoginView> {

    LoginModel loginModel;

    public LoginPresent (ILoginView iLoginView) {
        super(iLoginView);
        this.loginModel = new LoginModel();
    }

    public void login(String username, String password) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            mIView.loginFail("用户名与密码都不能为空！");
            return;
        }
        mIView.showLoadingDialog("登陆中");
        loginModel.login(username, password, new SimpleObserver<List<UserEntity>>(){
            @Override
            public void onNext(List<UserEntity> userEntity) {
                mIView.dismissLoadingDialog();
                mIView.loginSuccess(userEntity.get(0));
            }

            @Override
            public void onError(int errType, String errMessage) {
                mIView.dismissLoadingDialog();
                mIView.loginFail(errMessage);
            }
        } , mIView.getLifeSubject());
    }

}
