package com.tosmart.tmdb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.tosmart.tmdb.R;
import com.tosmart.tmdb.db.entity.TvPageList;

import static com.tosmart.tmdb.network.ApiRequest.INDEX_TV;
import static com.tosmart.tmdb.network.ApiService.PIC_URL;

/**
 * @author ggz
 * @date 2020/10/23
 */
public class GridTvPageListAdapter extends PagedListAdapter<TvPageList, GridTvPageListAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName();

    private OnItemClickListener mListener = null;

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
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                TvPageList item = getItem(position);
                if (item != null) {
                    if (mListener != null) {
                        mListener.onItemClick(item.getId(), INDEX_TV);
                    }
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TvPageList tv = getItem(position);
        if (tv == null) {
            String showLoading = StringUtils.getString(R.string.str_content_item_statue_loading);
            holder.nameTv.setText(showLoading);
            holder.dateTv.setText(showLoading);
            holder.averageTv.setText("0");
        } else {
            String average = String.valueOf((int) (tv.getVoteAverage() * 10));
            String url = PIC_URL + tv.getPosterPath();
            if (tv.getPosterPath() != null) {
                Glide.with(holder.posterIv.getContext())
                        .load(url)
                        .error(R.drawable.ic_launcher_background)
                        .centerCrop()
                        .into(holder.posterIv);
            } else {
                holder.posterIv.setImageResource(R.mipmap.empty);
            }
            holder.nameTv.setText(tv.getOriginalName());
            holder.dateTv.setText(tv.getFirstAirDate());
            holder.averageTv.setText(average);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout constraintLayout;
        ImageView posterIv;
        ImageView averageIv;
        TextView averageTv;
        TextView nameTv;
        TextView dateTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.cl_grid_item_view);
            posterIv = itemView.findViewById(R.id.iv_grid_item_poster);
            averageIv = itemView.findViewById(R.id.iv_grid_item_average);
            averageTv = itemView.findViewById(R.id.tv_grid_item_average);
            nameTv = itemView.findViewById(R.id.tv_grid_item_name);
            dateTv = itemView.findViewById(R.id.tv_grid_item_date);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
