package com.mcmc.ray.scan.project;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.mcmc.ray.scan.R;
import com.mcmc.ray.scan.beans.OrderBean;

public class ProjectViewHolder extends BaseViewHolder<OrderBean.Project> {
    TextView tv;
    public ProjectViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_order);

        tv=$(R.id.tv);
    }

    @Override
    public void setData(OrderBean.Project data) {
        super.setData(data);
        tv.setText(data.getName());
    }
}

