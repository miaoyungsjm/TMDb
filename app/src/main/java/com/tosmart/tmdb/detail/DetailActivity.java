package com.tosmart.tmdb.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.tosmart.tmdb.BR;
import com.tosmart.tmdb.R;
import com.tosmart.tmdb.adapter.paged_list_adapter.ListStylePagedListAdapter;
import com.tosmart.tmdb.adapter.OnItemClickListener;
import com.tosmart.tmdb.base.BaseActivity;
import com.tosmart.tmdb.db.entity.CommonPageList;
import com.tosmart.tmdb.adapter.SpacingItemDecoration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.tosmart.tmdb.network.ApiRequest.INDEX_MOVIE;
import static com.tosmart.tmdb.network.ApiRequest.INDEX_TV;
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

    private ListStylePagedListAdapter mTvPageListAdapter;
    private ListStylePagedListAdapter mMoviePageListAdapter;

    @Override
    protected void initViewModel() {
        mDetailViewModel = getActivityViewModel(DetailViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_detail, BR.vm, mDetailViewModel)
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
        initData();
        initView();
    }

    @Override
    protected void onResume() {
        Log.e(TAG, "onResume: ");
        mDetailViewModel.checkFavorite();
        super.onResume();
    }

    private void initData() {
        Log.e(TAG, "initData: ");
        Intent intent = getIntent();
        int id = intent.getIntExtra(KEY_ID, -1);
        int type = intent.getIntExtra(KEY_TYPE, -1);

        if (id != -1 && type != -1) {
            // 根据 ID 查数据库，并进行网络请求
            mDetailViewModel.requestData(id, type);
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

        mDetailViewModel.showFavorite.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                ImageView favIv = findViewById(R.id.iv_detail_favorite);
                favIv.setSelected(aBoolean);
            }
        });
    }

    private void initView() {
        int spacingLeft = SizeUtils.dp2px(24);
        SpacingItemDecoration decoration =
                new SpacingItemDecoration(0, spacingLeft, 0);

        RecyclerView recyclerView = findViewById(R.id.rv_detail_recommend);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView.addItemDecoration(decoration);

        if (mDetailViewModel.getCurrentType() == INDEX_TV) {
            initTvPageAdapter();
            recyclerView.setAdapter(mTvPageListAdapter);
        } else {
            initMoviePageAdapter();
            recyclerView.setAdapter(mMoviePageListAdapter);
        }
    }

    private void initTvPageAdapter() {
        mTvPageListAdapter = new ListStylePagedListAdapter(INDEX_TV);
        mTvPageListAdapter.setOnItemClickListener(mListener);

        mDetailViewModel.initPagedList(INDEX_TV);
        mDetailViewModel.mTvLiveData.observe(this, new Observer<PagedList<CommonPageList>>() {
            @Override
            public void onChanged(PagedList<CommonPageList> tvPageLists) {
                Log.d(TAG, "onChanged: tv");
                mTvPageListAdapter.submitList(tvPageLists);
            }
        });
    }

    private void initMoviePageAdapter() {
        mMoviePageListAdapter = new ListStylePagedListAdapter(INDEX_MOVIE);
        mMoviePageListAdapter.setOnItemClickListener(mListener);

        mDetailViewModel.initPagedList(INDEX_MOVIE);
        mDetailViewModel.mMovieLiveData.observe(this, new Observer<PagedList<CommonPageList>>() {
            @Override
            public void onChanged(PagedList<CommonPageList> moviePageLists) {
                Log.d(TAG, "onChanged: movie");
                mMoviePageListAdapter.submitList(moviePageLists);
            }
        });
    }

    private OnItemClickListener mListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int id, int type) {
            Log.d(TAG, "onClick: id=" + id + ", type=" + type);
            jumpToDetail(id, type);
        }
    };

    private void jumpToDetail(int id, int type) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(KEY_ID, id);
        intent.putExtra(KEY_TYPE, type);
        startActivity(intent);
    }

    public class ClickProxy {
        public void updateFavorite() {
            boolean flag = mDetailViewModel.isFavorite();
            mDetailViewModel.updateFavorite(!flag);
        }
    }
}
