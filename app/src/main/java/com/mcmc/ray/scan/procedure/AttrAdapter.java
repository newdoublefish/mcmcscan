package com.mcmc.ray.scan.procedure;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.mcmc.ray.scan.beans.OrderBean;
import com.mcmc.ray.scan.util.LogUtil;
import com.mcmc.ray.scan.util.ToastUtil;

public class AttrAdapter extends RecyclerArrayAdapter<OrderBean.Attribute> {
    private OnItemScanButtonClickListener mOnItemScanButtonClickListener;
    public AttrAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new AttrViewHolder(parent);
    }

    @Override
    public void OnBindViewHolder(final BaseViewHolder holder, final int position) {
        super.OnBindViewHolder(holder, position);
        AttrViewHolder attrViewHolder = (AttrViewHolder)holder;
        attrViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d("button----------------------------------------------"+position);
                if(mOnItemScanButtonClickListener!=null)
                    mOnItemScanButtonClickListener.onScanButtonClick(position,holder);
            }
        });

    }

    public void setmOnItemScanButtonClickListener(OnItemScanButtonClickListener listener)
    {
        this.mOnItemScanButtonClickListener = listener;
    }

    public interface OnItemScanButtonClickListener{
        void onScanButtonClick(int position,BaseViewHolder holder);
    }
}
