package com.holderzone.android.holderpick.screen.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 万能Adapter
 *
 * @author www
 * @date 2018/11/15 10:52.
 */
public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<RecycleHolder> {

    private Context mContext;
    private List<T> mData;
    private int mLayoutId;
    private LayoutInflater mInflater;

    private OnItemClickListener onItemClickListener;

    public RecyclerAdapter(Context mContext, List<T> mDatas, int mLayoutId) {
        this.mContext = mContext;
        this.mData = mDatas;
        this.mLayoutId = mLayoutId;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecycleHolder(mInflater.inflate(mLayoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleHolder holder, int position) {
        convert(holder, mData.get(position), position);
        if (onItemClickListener != null) {
            //设置背景
            holder.itemView.setOnClickListener(v -> {
                //注意，这里的position不要用上面参数中的position，会出现位置错乱\
                onItemClickListener.OnItemClickListener(holder.itemView, holder.getLayoutPosition());
            });
        }

    }

    /**
     * 转换器
     *
     * @param holder
     * @param data
     * @param position
     * @date 2018/11/15 10:52
     */
    public abstract void convert(RecycleHolder holder, T data, int position);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(View view, int position);
    }
}
