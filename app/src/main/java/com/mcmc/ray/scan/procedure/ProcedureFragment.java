package com.mcmc.ray.scan.procedure;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewStub;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.mcmc.ray.scan.R;
import com.mcmc.ray.scan.beans.OrderBean;
import com.mcmc.ray.scan.util.LogUtil;
import com.mcmc.ray.scan.util.ToastUtil;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import coder.mylibrary.base.BaseFragment;

public class ProcedureFragment extends BaseFragment {
    private Unbinder unbinder;
    private static ProcedureFragment procedureFragment=null;
    @BindView(R.id.attr_recycler_view)
    EasyRecyclerView attrRecyclerView;
    @BindView(R.id.procedure_recycler_view)
    EasyRecyclerView procedureRecyclerView;
    private ProcedureAdapter procedureAdapter;
    private AttrAdapter attrAdapter;
    private List<OrderBean> orderBeans;
    private int REQUEST_ATTR = 1;
    private int REQUEST_PROCEDURE = 2;
    private int currentPosition;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //TODO 更新UI
                    //如果有数据
                    System.out.println("msg = " + msg.obj.toString());

                    orderBeans.get(0).getProject().get(0).getProcedure().get(currentPosition).setVendor(msg.obj.toString());
                    procedureAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
            return false;
        }
    }) ;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        initRecyclerView();
        initData();
        procedureAdapter.addAll(orderBeans.get(0).getProject().get(0).getProcedure());
        attrAdapter.addAll(orderBeans.get(0).getProject().get(0).getAttribute());
    }

    void initData()
    {
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
        OrderBean.Attribute oa;
        for(int i=0;i<10;i++) {
            oa = new OrderBean.Attribute();
            oa.setName("装置编号"+i);
            oa.setSn("xxxxx");
            attributes.add(oa);
        }
        op.setAttribute(attributes);
        List<OrderBean.Procedure> procedures = new ArrayList<>();
        OrderBean.Procedure obp;
        List<String> vendors;
        for(int i=0;i<10;i++) {
            obp = new OrderBean.Procedure();
            obp.setName("控制盒"+i);
            obp.setSn("0000012312312312");
            obp.setVendor("广州锐速");
            vendors = new ArrayList<String>();
            vendors.add("广州锐速");
            vendors.add("珠海西格码");
            obp.setDefaultVendor(vendors);
            procedures.add(obp);
        }
        op.setProcedure(procedures);
        projects.add(op);
        orderBean.setProject(projects);
        orderBeans=new ArrayList<>();
        orderBeans.add(orderBean);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ATTR) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getHoldingActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                    orderBeans.get(0).getProject().get(0).getAttribute().get(currentPosition).setSn(result);
                    attrAdapter.notifyDataSetChanged();


                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getHoldingActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }else if (requestCode == REQUEST_PROCEDURE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getHoldingActivity(),"currentPosition"+currentPosition+ ";解析结果:" + result, Toast.LENGTH_LONG).show();
                    orderBeans.get(0).getProject().get(0).getProcedure().get(currentPosition).setSn(result);
                    procedureAdapter.notifyDataSetChanged();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getHoldingActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    //Spinner 动态增加数据
    //https://blog.csdn.net/qq_36339249/article/details/71699006
    private void initRecyclerView()
    {
        RecyclerView.LayoutManager staggerdGridLayoutManager;
        staggerdGridLayoutManager=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        procedureRecyclerView.setLayoutManager(staggerdGridLayoutManager);
        procedureAdapter=new ProcedureAdapter(getHoldingActivity());
        procedureAdapter.setmOnItemScanButtonClickListener(new ProcedureAdapter.OnItemScanButtonClickListener() {
            @Override
            public void onScanButtonClick(int position, BaseViewHolder holder) {

                Intent intent = new Intent(getHoldingActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_PROCEDURE);
                currentPosition = position;
            }
            @Override
            public void OnItemSelectedListener(int position,String vendorName) {
                ToastUtil.showShort(getActivity(),"position:"+position+":vendor:"+vendorName);
                //orderBeans.get(0).getProject().get(0).getProcedure().get(position).setVendor(vendorName);
                //procedureAdapter.notifyDataSetChanged();
                currentPosition = position;
                mHandler.obtainMessage(0,vendorName).sendToTarget();
            }
        });
        procedureRecyclerView.setAdapter(procedureAdapter);


        staggerdGridLayoutManager=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        attrRecyclerView.setLayoutManager(staggerdGridLayoutManager);
        attrAdapter=new AttrAdapter(getHoldingActivity());
        attrAdapter.setmOnItemScanButtonClickListener(new AttrAdapter.OnItemScanButtonClickListener() {
            @Override
            public void onScanButtonClick(int position, BaseViewHolder holder) {
                Intent intent = new Intent(getHoldingActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_ATTR);
                currentPosition = position;
            }
        });
        attrRecyclerView.setAdapter(attrAdapter);
    }

    public static ProcedureFragment getInstance()
    {
        if(procedureFragment==null)
            procedureFragment = new ProcedureFragment();
        return procedureFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_procedure;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
