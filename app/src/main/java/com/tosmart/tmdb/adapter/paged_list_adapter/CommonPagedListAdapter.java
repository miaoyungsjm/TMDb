package com.tosmart.tmdb.adapter.paged_list_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.StringUtils;
import com.tosmart.tmdb.BR;
import com.tosmart.tmdb.R;
import com.tosmart.tmdb.adapter.OnItemClickListener;
import com.tosmart.tmdb.db.entity.CommonBean;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author ggz
 * @date 2020/10/23
 */
public class CommonPagedListAdapter<T extends ViewDataBinding> extends PagedListAdapter<CommonBean, CommonPagedListAdapter.CommonViewHolder<T>> {
    private final String TAG = getClass().getSimpleName();

    private OnItemClickListener mListener = null;
    private int mLayoutId = -1;

    public CommonPagedListAdapter(int layoutId) {
        super(new DiffUtil.ItemCallback<CommonBean>() {
            @Override
            public boolean areItemsTheSame(@NonNull CommonBean oldItem, @NonNull CommonBean newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull CommonBean oldItem, @NonNull CommonBean newItem) {
                return oldItem.getId() == newItem.getId();
            }
        });

        mLayoutId = layoutId;
    }

    @NonNull
    @Override
    public CommonViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        T binding = DataBindingUtil.inflate(inflater, mLayoutId, parent, false);
        CommonViewHolder<T> holder = new CommonViewHolder<T>(binding);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                CommonBean item = getItem(position);
                if (item != null) {
                    if (mListener != null) {
                        mListener.onItemClick(item.getId(), item.getType());
                    }
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder<T> holder, int position) {
        CommonBean commonBean = getItem(position);
        if (commonBean == null) {
            String loading = StringUtils.getString(R.string.str_content_item_statue_loading);
            commonBean = new CommonBean(-1, -1,
                    loading, loading, null, 0.0, 0);
        }
        holder.binding.setVariable(BR.common, commonBean);
    }

    static class CommonViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
        T binding;

        public CommonViewHolder(T binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
