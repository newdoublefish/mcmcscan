package com.mcmc.ray.scan.procedure;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.mcmc.ray.scan.beans.OrderBean;
import com.mcmc.ray.scan.util.LogUtil;

public class ProcedureAdapter extends RecyclerArrayAdapter<OrderBean.Procedure> {
    private AttrAdapter.OnItemScanButtonClickListener mOnItemScanButtonClickListener;
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
        ProcedureViewHolder procedureViewHolder = (ProcedureViewHolder)holder;
        procedureViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d("button----------------------------------------------"+position);
                if(mOnItemScanButtonClickListener!=null)
                    mOnItemScanButtonClickListener.onScanButtonClick(position,holder);
            }
        });
    }

    public void setmOnItemScanButtonClickListener(AttrAdapter.OnItemScanButtonClickListener listener)
    {
        this.mOnItemScanButtonClickListener = listener;
    }

    public interface OnItemScanButtonClickListener{
        void onScanButtonClick(int position,BaseViewHolder holder);
    }
}
