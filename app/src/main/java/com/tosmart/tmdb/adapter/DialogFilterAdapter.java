package com.tosmart.tmdb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.tosmart.tmdb.R;
import com.tosmart.tmdb.main.dialog.FilterDialogFragment;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author ggz
 * @date 2020/10/26
 */
public class DialogFilterAdapter extends RecyclerView.Adapter<DialogFilterAdapter.ViewHolder> {

    private String[] mFilters;
    private int mFilterIndex = 0;

    private FilterDialogFragment.OnItemClickListener mListener = null;

    public DialogFilterAdapter(int filterIndex) {
        mFilters = StringUtils.getStringArray(R.array.filter_list);
        mFilterIndex = filterIndex;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dialog_filter, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                mFilterIndex = position;
                notifyDataSetChanged();

                // 刷新数据源
                if (mListener != null) {
                    mListener.onItemClick(position);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = mFilters[position];
        holder.nameTv.setText(name);
        if (position == mFilterIndex) {
            holder.itemCl.requestFocus();
            holder.checkIv.setSelected(true);
        } else {
            holder.checkIv.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return mFilters.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout itemCl;
        TextView nameTv;
        ImageView checkIv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCl = itemView.findViewById(R.id.cl_dialog_filter_item);
            nameTv = itemView.findViewById(R.id.tv_dialog_filter_item_name);
            checkIv = itemView.findViewById(R.id.iv_dialog_filter_item_check);
        }
    }

    public void setOnItemClickListener(FilterDialogFragment.OnItemClickListener listener) {
        mListener = listener;
    }
}