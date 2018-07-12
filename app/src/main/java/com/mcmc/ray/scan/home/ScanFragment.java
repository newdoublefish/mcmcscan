package com.mcmc.ray.scan.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mcmc.ray.scan.R;
import com.mcmc.ray.scan.beans.UserBean;
import com.mcmc.ray.scan.http.APIServiceImpl;
import com.mcmc.ray.scan.http.BaseResponse;
import com.mcmc.ray.scan.util.LogUtil;
import com.mcmc.ray.scan.util.ToastUtil;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import coder.mylibrary.base.BaseFragment;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
//import io.reactivex.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import okhttp3.RequestBody;
import rx.Scheduler;

public class ScanFragment extends BaseFragment {
    private static ScanFragment scanFragment=null;
    private int REQUEST_CODE = 1;
    private Unbinder unbinder;
    @BindView(R.id.scanButton)
    Button scanButton;
    @BindView(R.id.loginButton)
    Button loginButton;
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
    }

    public static ScanFragment getInstance()
    {
        if(scanFragment == null)
            scanFragment = new ScanFragment();
        return scanFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_scan;
    }

    @OnClick({R.id.scanButton,R.id.loginButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.scanButton:
                //ToastUtil.showShort(getHoldingActivity(),"clicked");
                Intent intent = new Intent(getHoldingActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.loginButton:
                UserBean userBean=new UserBean();
                userBean.setUsername("admin");
                userBean.setPassword("hello12345");
                BaseResponse<UserBean> request = new BaseResponse<UserBean>();
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
                        }
                    });
                ToastUtil.showShort(getHoldingActivity(),obj);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getHoldingActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getHoldingActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
