package com.mcmc.ray.scan.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mcmc.ray.scan.R;
import com.mcmc.ray.scan.beans.UserBean;
import com.mcmc.ray.scan.http.APIServiceImpl;
import com.mcmc.ray.scan.http.BaseRequest;
import com.mcmc.ray.scan.http.BaseResponse;
import com.mcmc.ray.scan.order.OrderActivity;
import com.mcmc.ray.scan.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import coder.mylibrary.base.ActivityManager;
import coder.mylibrary.base.BaseFragment;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ASIMO on 2017/11/29.
 */

public class LoginFragment extends BaseFragment{
    Unbinder unbinder;
    @BindView(R.id.et_userName)
    EditText userNameEditText;
    @BindView(R.id.et_password)
    EditText passwordEditText;
    @BindView(R.id.btn_login)
    Button login;

    public static LoginFragment getInstance() {
        LoginFragment splashFragment = new LoginFragment();
        return splashFragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
    }

    @OnClick({R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                startActivity(new Intent(getActivity(), OrderActivity.class));
                ActivityManager.getInstance().finishActivity();
                UserBean userBean=new UserBean();
                userBean.setUsername(userNameEditText.getText().toString());
                userBean.setPassword(passwordEditText.getText().toString());
                BaseRequest<UserBean> request = new BaseRequest<UserBean>();
                request.setRequest("login");
                request.setData(userBean);
                final Gson gson=new Gson();
                String obj=gson.toJson(request);
                RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),obj);
                APIServiceImpl.getInstance().login(body).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new rx.Observer<BaseResponse<UserBean>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(BaseResponse<UserBean> userBeanBaseResponse) {
                                ToastUtil.showShort(getHoldingActivity(),gson.toJson(userBeanBaseResponse));
                                if(userBeanBaseResponse.getStatus().equals("success")) {
                                    startActivity(new Intent(getActivity(), OrderActivity.class));
                                    ActivityManager.getInstance().finishActivity();
                                }
                            }
                        });
                //ToastUtil.showShort(getHoldingActivity(),obj);
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
