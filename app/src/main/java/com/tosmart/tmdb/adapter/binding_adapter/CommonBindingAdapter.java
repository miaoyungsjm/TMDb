package com.tosmart.tmdb.adapter.binding_adapter;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tosmart.tmdb.R;

import androidx.databinding.BindingAdapter;

import static com.tosmart.tmdb.network.ApiService.PIC_URL;

/**
 * @author ggz
 * @date 2020/11/2
 */
public class CommonBindingAdapter {

    @BindingAdapter(value = {"imageUrl"}, requireAll = false)
    public static void loadUrl(ImageView view, String poster) {
        if (poster != null) {
            String url = PIC_URL + poster;
            Glide.with(view.getContext())
                    .load(url)
                    .error(R.drawable.ic_launcher_background)
                    .centerCrop()
                    .into(view);
        } else {
            view.setImageResource(R.mipmap.empty);
        }
    }

    @BindingAdapter(value = {"voteAverage"}, requireAll = false)
    public static void loadAverage(TextView view, double voteAverage) {
        String average = String.valueOf((int) (voteAverage * 10));
        view.setText(average);
    }
}
