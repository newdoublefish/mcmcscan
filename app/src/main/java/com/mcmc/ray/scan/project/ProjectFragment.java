package com.mcmc.ray.scan.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewStub;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.mcmc.ray.scan.R;
import com.mcmc.ray.scan.beans.OrderBean;
import com.mcmc.ray.scan.order.OrderAdapter;
import com.mcmc.ray.scan.procedure.ProcedureActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import coder.mylibrary.base.BaseFragment;

public class ProjectFragment extends BaseFragment {
    private Unbinder unbinder;
    private static ProjectFragment orderFragment=null;
    @BindView(R.id.orders_recycler_view)
    EasyRecyclerView orderRecyclerView;
    @BindView(R.id.network_error_layout)
    ViewStub networkErrorLayout;

    private ProjectAdapter projectAdapter;
    private List<OrderBean> orderBeans;
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        initRecyclerView();
        initData();
        projectAdapter.addAll(orderBeans.get(0).getProject());
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
        orderBeans=new ArrayList<>();
        orderBeans.add(orderBean);

    }

    private void initRecyclerView()
    {
        RecyclerView.LayoutManager staggerdGridLayoutManager;
        staggerdGridLayoutManager=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        orderRecyclerView.setLayoutManager(staggerdGridLayoutManager);
        projectAdapter=new ProjectAdapter(getHoldingActivity());
        orderRecyclerView.setAdapter(projectAdapter);
        //recordAdapter.setMore(R.layout.load_more_layout,this);
        //recordAdapter.setNoMore(R.layout.no_more_layout);
        projectAdapter.setError(R.layout.network_error);
        projectAdapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getHoldingActivity(), ProcedureActivity.class);
                startActivity(intent);
            }
        });
    }

    public static ProjectFragment getInstance()
    {
        if(orderFragment==null)
            orderFragment = new ProjectFragment();
        return orderFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
