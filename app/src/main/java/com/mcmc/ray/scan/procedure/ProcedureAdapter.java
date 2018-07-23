package com.mcmc.ray.scan.procedure;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.mcmc.ray.scan.beans.OrderBean;
import com.mcmc.ray.scan.util.LogUtil;

public class ProcedureAdapter extends RecyclerArrayAdapter<OrderBean.Procedure> {
    private OnItemScanButtonClickListener mOnItemScanButtonClickListener;
    public ProcedureAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProcedureViewHolder(parent);
    }

    @Override
    public void OnBindViewHolder(final BaseViewHolder holder, final int position) {
        super.OnBindViewHolder(holder, position);
        final ProcedureViewHolder procedureViewHolder = (ProcedureViewHolder)holder;

        procedureViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d("button----------------------------------------------"+position);
                if(mOnItemScanButtonClickListener!=null)
                    mOnItemScanButtonClickListener.onScanButtonClick(position,holder);
            }
        });
        //procedureViewHolder.spinner.setSelection(0, true);
        procedureViewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if(procedureViewHolder.isInit) {
                    procedureViewHolder.isInit = false;
                    return;
                }
                if(mOnItemScanButtonClickListener!=null)
                    mOnItemScanButtonClickListener.OnItemSelectedListener(position,parent.getItemAtPosition(pos).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setmOnItemScanButtonClickListener(OnItemScanButtonClickListener listener)
    {
        this.mOnItemScanButtonClickListener = listener;
    }

    public interface OnItemScanButtonClickListener{
        void onScanButtonClick(int position,BaseViewHolder holder);
        void OnItemSelectedListener(int position,String vendorName);
    }
}
