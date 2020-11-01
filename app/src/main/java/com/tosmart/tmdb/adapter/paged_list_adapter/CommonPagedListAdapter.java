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
public class CommonPagedListAdapter extends PagedListAdapter<CommonPageList, RecyclerView.ViewHolder> {
    private final String TAG = getClass().getSimpleName();
    public final int ITEM_VIEW_TYPE_EMPTY = 0;
    public final int ITEM_VIEW_TYPE_GRID = 1;
    public final int ITEM_VIEW_TYPE_LIST = 2;

    private OnItemClickListener mListener = null;
    private int mShowType = -1;
    private int mItemViewType = ITEM_VIEW_TYPE_EMPTY;

    public CommonPagedListAdapter(int showType, int itemViewType) {
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
        mItemViewType = itemViewType;
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        if (mItemViewType == ITEM_VIEW_TYPE_GRID) {
            return ITEM_VIEW_TYPE_GRID;
        } else {
            return ITEM_VIEW_TYPE_LIST;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == ITEM_VIEW_TYPE_GRID) {
            View view = inflater.inflate(R.layout.item_grid_style, parent, false);
            return new GridViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_list_style, parent, false);
            return new ListViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
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

        CommonPageList item = getItem(position);
        if (holder instanceof GridViewHolder) {
            GridViewHolder gridViewHolder = (GridViewHolder) holder;
            if (item == null) {
                String showLoading = StringUtils.getString(R.string.str_content_item_statue_loading);
                gridViewHolder.posterIv.setImageResource(R.mipmap.empty);
                gridViewHolder.nameTv.setText(showLoading);
                gridViewHolder.dateTv.setText(showLoading);
                gridViewHolder.averageTv.setText("0");
            } else {
                String average = String.valueOf((int) (item.getVoteAverage() * 10));
                String url = PIC_URL + item.getPosterPath();
                if (item.getPosterPath() != null) {
                    Glide.with(gridViewHolder.posterIv.getContext())
                            .load(url)
                            .error(R.drawable.ic_launcher_background)
                            .centerCrop()
                            .into(gridViewHolder.posterIv);
                } else {
                    gridViewHolder.posterIv.setImageResource(R.mipmap.empty);
                }
                gridViewHolder.nameTv.setText(item.getOriginalName());
                gridViewHolder.dateTv.setText(item.getFirstAirDate());
                gridViewHolder.averageTv.setText(average);
            }
        } else {
            ListViewHolder listViewHolder = (ListViewHolder) holder;
            if (item == null) {
                String showLoading = StringUtils.getString(R.string.str_content_item_statue_loading);
                listViewHolder.posterIv.setImageResource(R.mipmap.empty);
                listViewHolder.nameTv.setText(showLoading);
                listViewHolder.dateTv.setText(showLoading);
                listViewHolder.averageTv.setText("0");
            } else {
                String average = String.valueOf((int) (item.getVoteAverage() * 10));
                String url = PIC_URL + item.getPosterPath();
                if (item.getPosterPath() != null) {
                    Glide.with(listViewHolder.posterIv.getContext())
                            .load(url)
                            .error(R.drawable.ic_launcher_background)
                            .centerCrop()
                            .into(listViewHolder.posterIv);
                } else {
                    listViewHolder.posterIv.setImageResource(R.mipmap.empty);
                }
                listViewHolder.nameTv.setText(item.getOriginalName());
                listViewHolder.dateTv.setText(item.getFirstAirDate());
                listViewHolder.averageTv.setText(average);
            }
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

    static class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView posterIv;
        ImageView averageIv;
        TextView averageTv;
        TextView nameTv;
        TextView dateTv;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            posterIv = itemView.findViewById(R.id.iv_list_item_poster);
            averageIv = itemView.findViewById(R.id.iv_list_item_average);
            averageTv = itemView.findViewById(R.id.tv_list_item_average);
            nameTv = itemView.findViewById(R.id.tv_list_item_name);
            dateTv = itemView.findViewById(R.id.tv_list_item_date);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
