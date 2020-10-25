package com.tosmart.tmdb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tosmart.tmdb.R;
import com.tosmart.tmdb.db.entity.TvPageList;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import static com.tosmart.tmdb.network.ApiService.PIC_URL;

/**
 * @author ggz
 * @date 2020/10/23
 */
public class GridTvPageListAdapter extends PagedListAdapter<TvPageList, GridTvPageListAdapter.ViewHolder> {

    public GridTvPageListAdapter() {
        super(new DiffUtil.ItemCallback<TvPageList>() {
            @Override
            public boolean areItemsTheSame(@NonNull TvPageList oldItem, @NonNull TvPageList newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull TvPageList oldItem, @NonNull TvPageList newItem) {
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
        TvPageList tv = getItem(position);
        if (tv == null) {
            holder.nameTv.setText("loading...");
            holder.dateTv.setText("loading...");
            holder.averageTv.setText("0");
        } else {
            String average = String.valueOf((int) (tv.getVoteAverage() * 10));
            String url = PIC_URL + tv.getPosterPath();
            Glide.with(holder.posterIv.getContext())
                    .load(url).centerCrop().into(holder.posterIv);
            holder.nameTv.setText(tv.getOriginalName());
            holder.dateTv.setText(tv.getFirstAirDate());
            holder.averageTv.setText(average);
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
