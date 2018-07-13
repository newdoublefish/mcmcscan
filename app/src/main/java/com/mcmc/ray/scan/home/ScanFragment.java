package com.mcmc.ray.scan.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mcmc.ray.scan.R;
import com.mcmc.ray.scan.beans.OrderBean;
import com.mcmc.ray.scan.beans.UserBean;
import com.mcmc.ray.scan.http.APIServiceImpl;
import com.mcmc.ray.scan.http.BaseRequest;
import com.mcmc.ray.scan.http.BaseResponse;
import com.mcmc.ray.scan.util.LogUtil;
import com.mcmc.ray.scan.util.ToastUtil;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import coder.mylibrary.base.BaseFragment;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import okhttp3.RequestBody;

public class ScanFragment extends BaseFragment {
    private static ScanFragment scanFragment=null;
    private int REQUEST_CODE = 1;
    private Unbinder unbinder;
    @BindView(R.id.scanButton)
    Button scanButton;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.jsonButton)
    Button jsonButton;
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

    @OnClick({R.id.scanButton,R.id.loginButton,R.id.jsonButton})
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
                        }
                    });
                ToastUtil.showShort(getHoldingActivity(),obj);
                break;
            case R.id.jsonButton:
                OrderBean orderBean = new OrderBean();
                orderBean.setFactory("广州锐速");
                orderBean.setName("ECPADF-1234");
                orderBean.setTotal(100);
                List<OrderBean.Project> projects= new ArrayList<OrderBean.Project>();
                OrderBean.Project op = new OrderBean.Project();
                op.setName("上板");
                op.setSn("xxxx");
                op.setVendor("广州锐速");
                List<OrderBean.Attribute> attributes = new ArrayList<OrderBean.Attribute>();
                OrderBean.Attribute oa = new OrderBean.Attribute();
                oa.setName("装置编号");
                oa.setSn("xxxxx");
                attributes.add(oa);
                op.setAttribute(attributes);
                List<OrderBean.Procedure> procedures = new ArrayList<>();
                OrderBean.Procedure obp = new OrderBean.Procedure();
                obp.setName("控制盒");
                obp.setSn("0000012312312312");
                obp.setVendor("广州锐速");
                procedures.add(obp);
                op.setProcedure(procedures);
                projects.add(op);
                orderBean.setProject(projects);
                Gson gson1 = new Gson();
                String orderBeanStr =gson1.toJson(orderBean);
                LogUtil.d(orderBeanStr);

                OrderBean orderBean1 = gson1.fromJson(orderBeanStr,OrderBean.class);
                LogUtil.d(orderBean1.getProject().get(0).getName());

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
