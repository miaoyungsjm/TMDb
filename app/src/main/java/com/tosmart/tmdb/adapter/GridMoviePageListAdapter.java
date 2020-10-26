package com.tosmart.tmdb.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.tosmart.tmdb.R;
import com.tosmart.tmdb.db.entity.MoviePageList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import static com.tosmart.tmdb.network.ApiService.PIC_URL;

/**
 * @author ggz
 * @date 2020/10/23
 */
public class GridMoviePageListAdapter extends PagedListAdapter<MoviePageList, GridMoviePageListAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName();

    public GridMoviePageListAdapter() {
        super(new DiffUtil.ItemCallback<MoviePageList>() {
            @Override
            public boolean areItemsTheSame(@NonNull MoviePageList oldItem, @NonNull MoviePageList newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull MoviePageList oldItem, @NonNull MoviePageList newItem) {
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
                MoviePageList item = getItem(position);
                if (item != null) {
                    Log.d(TAG, "onClick: id=" + item.getId());
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MoviePageList movie = getItem(position);
        if (movie == null) {
            String showLoading = StringUtils.getString(R.string.str_content_item_statue_loading);
            holder.nameTv.setText(showLoading);
            holder.dateTv.setText(showLoading);
            holder.averageTv.setText("0");
        } else {
            String average = String.valueOf((int) (movie.getVoteAverage() * 10));
            String url = PIC_URL + movie.getPosterPath();
            Glide.with(holder.posterIv.getContext())
                    .load(url).into(holder.posterIv);
            holder.nameTv.setText(movie.getOriginalTitle());
            holder.dateTv.setText(movie.getReleaseDate());
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
            constraintLayout = itemView.findViewById(R.id.cl_grid_item);
            posterIv = itemView.findViewById(R.id.iv_grid_item_poster);
            averageIv = itemView.findViewById(R.id.iv_grid_item_average);
            averageTv = itemView.findViewById(R.id.tv_grid_item_average);
            nameTv = itemView.findViewById(R.id.tv_grid_item_name);
            dateTv = itemView.findViewById(R.id.tv_grid_item_date);
        }
    }
}
