package com.tosmart.tmdb.adapter.paged_list_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.tosmart.tmdb.R;
import com.tosmart.tmdb.adapter.OnItemClickListener;
import com.tosmart.tmdb.db.entity.CommonPageList;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import static com.tosmart.tmdb.network.ApiService.PIC_URL;

/**
 * @author ggz
 * @date 2020/10/23
 */
public class GridStylePagedListAdapter extends PagedListAdapter<CommonPageList, GridStylePagedListAdapter.GridViewHolder> {
    private final String TAG = getClass().getSimpleName();

    private OnItemClickListener mListener = null;
    private int mShowType = -1;

    public GridStylePagedListAdapter(int showType) {
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
        mShowType = showType;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_grid_style, parent, false);
        GridViewHolder viewHolder = new GridViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                CommonPageList item = getItem(position);
                if (item != null) {
                    if (mListener != null) {
                        mListener.onItemClick(item.getId(), mShowType);
                    }
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        CommonPageList pageListItem = getItem(position);
        if (pageListItem == null) {
            String showLoading = StringUtils.getString(R.string.str_content_item_statue_loading);
            holder.posterIv.setImageResource(R.mipmap.empty);
            holder.nameTv.setText(showLoading);
            holder.dateTv.setText(showLoading);
            holder.averageTv.setText("0");
        } else {
            String average = String.valueOf((int) (pageListItem.getVoteAverage() * 10));
            String url = PIC_URL + pageListItem.getPosterPath();
            if (pageListItem.getPosterPath() != null) {
                Glide.with(holder.posterIv.getContext())
                        .load(url)
                        .error(R.drawable.ic_launcher_background)
                        .centerCrop()
                        .into(holder.posterIv);
            } else {
                holder.posterIv.setImageResource(R.mipmap.empty);
            }
            holder.nameTv.setText(pageListItem.getOriginalName());
            holder.dateTv.setText(pageListItem.getFirstAirDate());
            holder.averageTv.setText(average);
        }
    }

    static class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView posterIv;
        ImageView averageIv;
        TextView averageTv;
        TextView nameTv;
        TextView dateTv;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
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
