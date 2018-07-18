package com.mcmc.ray.scan.procedure;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.mcmc.ray.scan.R;
import com.mcmc.ray.scan.beans.OrderBean;

public class ProcedureViewHolder extends BaseViewHolder<OrderBean.Procedure> {
    public TextView tv;
    public EditText editText;
    public Button button;
    public Spinner spinner;
    public ProcedureViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_procedure);
        tv=$(R.id.tv);
        editText=$(R.id.sn);
        button=$(R.id.scan);
        spinner=$(R.id.vendor_spinner);

    }

    @Override
    public void setData(OrderBean.Procedure data) {
        super.setData(data);
        tv.setText(data.getName());
        String s[]={"C语言","java","php","Python","C++","C#"};
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_dropdown_item_1line,android.R.id.text1,s);
        spinner.setAdapter(adapter);
    }
}

