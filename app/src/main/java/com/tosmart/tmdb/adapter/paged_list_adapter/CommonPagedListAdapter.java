package com.tosmart.tmdb.adapter.paged_list_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.StringUtils;
import com.tosmart.tmdb.BR;
import com.tosmart.tmdb.R;
import com.tosmart.tmdb.adapter.OnItemClickListener;
import com.tosmart.tmdb.db.entity.CommonPageList;

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
public class CommonPagedListAdapter<T extends ViewDataBinding> extends PagedListAdapter<CommonPageList, CommonPagedListAdapter.CommonViewHolder<T>> {
    private final String TAG = getClass().getSimpleName();

    private OnItemClickListener mListener = null;
    private int mLayoutId = -1;
    private int mShowType = -1;

    public CommonPagedListAdapter(int layoutId, int showType) {
        super(new DiffUtil.ItemCallback<CommonPageList>() {
            @Override
            public boolean areItemsTheSame(@NonNull CommonPageList oldItem, @NonNull CommonPageList newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull CommonPageList oldItem, @NonNull CommonPageList newItem) {
                return oldItem.getId() == newItem.getId();
            }
        });

        mLayoutId = layoutId;
        mShowType = showType;
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
                CommonPageList item = getItem(position);
                if (item != null) {
                    if (mListener != null) {
                        mListener.onItemClick(item.getId(), mShowType);
                    }
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder<T> holder, int position) {
        CommonPageList commonPageList = getItem(position);
        if (commonPageList == null) {
            commonPageList = new CommonPageList();
            commonPageList.setOriginalName(StringUtils.getString(R.string.str_content_item_statue_loading));
            commonPageList.setDate(StringUtils.getString(R.string.str_content_item_statue_loading));
        }
        holder.binding.setVariable(BR.common, commonPageList);
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
