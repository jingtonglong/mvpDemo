package com.ys.demo.mvp.view.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dev.base.R;
import com.ys.demo.base.acitvity.BaseActivity;
import com.ys.demo.entity.UserEntity;
import com.ys.demo.mvp.presenter.LoginPresent;
import com.ys.demo.mvp.view.api.ILoginView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements ILoginView{

    @BindView(R.id.user_name)
    EditText usernameT;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.submit)
    Button submit;
    LoginPresent loginPresent;

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        loginPresent = new LoginPresent(this);
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void initEvent() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresent.login(usernameT.getText() + "", password.getText() + "");
            }
        });
    }

    @Override
    public void loginSuccess(UserEntity userEntity) {

    }

    @Override
    public void loginFail(String msg) {
        showToast(msg);
    }


}
