package com.tosmart.tmdb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tosmart.tmdb.BR;
import com.tosmart.tmdb.db.entity.CommonBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author ggz
 * @date 2020/11/3
 */
public class CommonAdapter<T extends ViewDataBinding> extends RecyclerView.Adapter<CommonAdapter.ViewHolder<T>> {

    private int mLayoutId;
    private OnItemClickListener mListener;
    private List<CommonBean> mList = new ArrayList<>();

    public CommonAdapter(int layoutId) {
        this.mLayoutId = layoutId;
    }

    @NonNull
    @Override
    public ViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        T binding = DataBindingUtil.inflate(inflater, mLayoutId, parent, false);
        ViewHolder<T> holder = new ViewHolder<>(binding);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                CommonBean common = mList.get(position);
                if (common != null) {
                    if (mListener != null) {
                        mListener.onItemClick(common.getId(), common.getType());
                    }
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder<T> holder, int position) {
        CommonBean common = mList.get(position);
        if (common != null) {
            holder.binding.setVariable(BR.common, common);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
        T binding;

        public ViewHolder(T binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setCommonList(List<CommonBean> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
