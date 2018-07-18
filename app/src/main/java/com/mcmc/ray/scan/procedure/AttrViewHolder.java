package com.mcmc.ray.scan.procedure;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.mcmc.ray.scan.R;
import com.mcmc.ray.scan.beans.OrderBean;

public class AttrViewHolder extends BaseViewHolder<OrderBean.Attribute> {
    public TextView tv;
    public EditText editText;
    public Button button;
    public AttrViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_attr);
        tv=$(R.id.tv);
        editText=$(R.id.sn);
        button=$(R.id.scan);
    }

    @Override
    public void setData(OrderBean.Attribute data) {
        super.setData(data);
        tv.setText(data.getName());
        editText.setText(data.getSn());
    }
}

