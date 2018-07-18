package com.mcmc.ray.scan.order;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.mcmc.ray.scan.R;
import com.mcmc.ray.scan.beans.OrderBean;

public class OrderViewHolder extends BaseViewHolder<OrderBean> {
    TextView tv;
    public OrderViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_order);
        tv=$(R.id.tv);
    }

    @Override
    public void setData(OrderBean data) {
        super.setData(data);
        tv.setText(data.getName());
    }
}

