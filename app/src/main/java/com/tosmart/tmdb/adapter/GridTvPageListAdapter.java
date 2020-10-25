package com.tosmart.tmdb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tosmart.tmdb.R;
import com.tosmart.tmdb.db.entity.PopTv;
import com.tosmart.tmdb.db.entity.Tv;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author ggz
 * @date 2020/10/23
 */
public class GridTvPageListAdapter extends PagedListAdapter<PopTv, GridTvPageListAdapter.ViewHolder> {

    public GridTvPageListAdapter() {
        super(new DiffUtil.ItemCallback<PopTv>() {
            @Override
            public boolean areItemsTheSame(@NonNull PopTv oldItem, @NonNull PopTv newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull PopTv oldItem, @NonNull PopTv newItem) {
                return false;
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_grid_style, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PopTv tv = getItem(position);
        if (tv == null) {
            holder.nameTv.setText("loading...");
            holder.dateTv.setText("loading...");
            holder.averageTv.setText("0");
        } else {
//            holder.posterIv.setImageBitmap(tv.getPosterPath());
            holder.nameTv.setText(tv.getOriginalName());
            holder.dateTv.setText(tv.getFirstAirDate());
            holder.averageTv.setText("0");
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView posterIv;
        ImageView averageIv;
        TextView averageTv;
        TextView nameTv;
        TextView dateTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            posterIv = itemView.findViewById(R.id.iv_grid_item_poster);
            averageIv = itemView.findViewById(R.id.iv_grid_item_average);
            averageTv = itemView.findViewById(R.id.tv_grid_item_average);
            nameTv = itemView.findViewById(R.id.tv_grid_item_name);
            dateTv = itemView.findViewById(R.id.tv_grid_item_date);
        }
    }
}
