package com.tosmart.tmdb.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.tosmart.tmdb.BR;
import com.tosmart.tmdb.R;
import com.tosmart.tmdb.base.BaseActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import static com.tosmart.tmdb.network.ApiService.PIC_URL;

/**
 * @author ggz
 * @date 2020/10/27
 */
public class DetailActivity extends BaseActivity {
    private final String TAG = getClass().getSimpleName();
    public static final String KEY_ID = "key_id";
    public static final String KEY_TYPE = "key_type";

    private DetailViewModel mDetailViewModel;

    @Override
    protected void initViewModel() {
        mDetailViewModel = getActivityViewModel(DetailViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_detail, BR.vm, mDetailViewModel);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int id = intent.getIntExtra(KEY_ID, -1);
        int type = intent.getIntExtra(KEY_TYPE, -1);
        if (id != -1 && type != -1) {
            mDetailViewModel.requestDatabase(id, type);
        }

        mDetailViewModel.showPoster.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ImageView posterIv = findViewById(R.id.iv_detail_poster);
                ImageView bgIv = findViewById(R.id.iv_detail_bg);
                if (s != null) {
                    String url = PIC_URL + s;
                    Glide.with(DetailActivity.this)
                            .asBitmap().load(url)
                            .error(R.drawable.ic_launcher_background)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    posterIv.setImageBitmap(resource);
                                    bgIv.setImageBitmap(resource);
                                }
                            });
                } else {
                    posterIv.setImageResource(R.mipmap.empty);
                    bgIv.setBackground(getResources().getDrawable(R.mipmap.empty));
                }
            }
        });
    }
}
