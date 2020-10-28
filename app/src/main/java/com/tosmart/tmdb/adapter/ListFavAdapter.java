package com.tosmart.tmdb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.tosmart.tmdb.R;
import com.tosmart.tmdb.content.GridStyleFragment;
import com.tosmart.tmdb.db.entity.Favorite;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import static com.tosmart.tmdb.network.ApiService.PIC_URL;

/**
 * @author ggz
 * @date 2020/10/28
 */
public class ListFavAdapter extends RecyclerView.Adapter<ListFavAdapter.ViewHolder> {

    private List<Favorite> mFavList = new ArrayList<>();
    private GridStyleFragment.OnItemClickListener mListener = null;

    public void setFavList(List<Favorite> mFavList) {
        this.mFavList = mFavList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_style, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                Favorite favorite = mFavList.get(position);
                if (favorite != null) {
                    if (mListener != null) {
                        mListener.onItemClick(favorite.getId(), favorite.getType());
                    }
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Favorite fav = mFavList.get(position);
        if (fav == null) {
            String showLoading = StringUtils.getString(R.string.str_content_item_statue_loading);
            holder.nameTv.setText(showLoading);
            holder.dateTv.setText(showLoading);
            holder.averageTv.setText("0");
        } else {
            if (fav.getPoster() != null) {
                String url = PIC_URL + fav.getPoster();
                Glide.with(holder.posterIv.getContext())
                        .load(url)
                        .error(R.drawable.ic_launcher_background)
                        .centerCrop()
                        .into(holder.posterIv);
            } else {
                holder.posterIv.setImageResource(R.mipmap.empty);
            }
            holder.nameTv.setText(fav.getName());
            holder.dateTv.setText(fav.getDate());
            holder.averageTv.setText(fav.getAverage());
        }
    }

    @Override
    public int getItemCount() {
        return mFavList.size();
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
            constraintLayout = itemView.findViewById(R.id.cl_list_item_view);
            posterIv = itemView.findViewById(R.id.iv_list_item_poster);
            averageIv = itemView.findViewById(R.id.iv_list_item_average);
            averageTv = itemView.findViewById(R.id.tv_list_item_average);
            nameTv = itemView.findViewById(R.id.tv_list_item_name);
            dateTv = itemView.findViewById(R.id.tv_list_item_date);
        }
    }

    public void setOnItemClickListener(GridStyleFragment.OnItemClickListener listener) {
        mListener = listener;
    }
}
