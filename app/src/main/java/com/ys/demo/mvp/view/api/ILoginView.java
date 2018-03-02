package com.ys.demo.mvp.view.api;

import com.base.sdk.base.api.IBaseView;
import com.ys.demo.entity.UserEntity;

/**
 * Created by Administrator on 2018/3/1/001.
 */

public interface ILoginView extends IBaseView {
    void loginSuccess(UserEntity userEntity);
    void loginFail(String msg);
}
