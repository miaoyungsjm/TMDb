package com.tosmart.tmdb.content;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.tosmart.tmdb.BR;
import com.tosmart.tmdb.R;
import com.tosmart.tmdb.adapter.GridFavAdapter;
import com.tosmart.tmdb.adapter.GridMoviePageListAdapter;
import com.tosmart.tmdb.adapter.GridTvPageListAdapter;
import com.tosmart.tmdb.base.BaseFragment;
import com.tosmart.tmdb.db.entity.Favorite;
import com.tosmart.tmdb.db.entity.MoviePageList;
import com.tosmart.tmdb.db.entity.TvPageList;
import com.tosmart.tmdb.detail.DetailActivity;
import com.tosmart.tmdb.main.MainViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.tosmart.tmdb.detail.DetailActivity.KEY_ID;
import static com.tosmart.tmdb.detail.DetailActivity.KEY_TYPE;

/**
 * @author ggz
 * @date 2020/10/22
 */
public class GridStyleFragment extends BaseFragment {
    private final String TAG = getClass().getSimpleName();

    private GridStyleViewModel mGridStyleViewModel;
    private MainViewModel mMainViewModel;

    @Override
    protected void initViewModel() {
        mGridStyleViewModel = getFragmentViewModel(GridStyleViewModel.class);
        mMainViewModel = getActivityViewModel(MainViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_grid_style, BR.vm, mGridStyleViewModel);
    }

    @Override
    protected void initData() {
        Log.e(TAG, "initData:" );

        // 初始化 paging data
        mGridStyleViewModel.initPagedList(
                mMainViewModel.getFilterType(),
                mMainViewModel.getFilterOrder());

        // 初始化 fav data
        mGridStyleViewModel.initFavList();

        // 排序回调，刷新数据源
        mMainViewModel.mFilterFlag.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer index) {
                Log.e(TAG, "onChanged: filterIndex = " + index);
                mMainViewModel.setFilterIndex(index);

                releaseOldPagingObservers();
                mGridStyleViewModel.initPagedList(
                        mMainViewModel.getFilterType(),
                        mMainViewModel.getFilterOrder());
                initTvPaging(rootView);
                initMoviePaging(rootView);
            }
        });
    }

    @Override
    protected void initView(View v) {
        initFav(v);
        initTvPaging(v);
        initMoviePaging(v);
    }

    @Override
    public void onResume() {
        Log.e(TAG, "onResume:" );
        // 详情回来刷新 fav data
        mGridStyleViewModel.initFavList();
        super.onResume();
    }

    public void initFav(View v) {
        RecyclerView favRv = v.findViewById(R.id.rv_grid_content_favorite);
        favRv.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        GridFavAdapter gridFavAdapter = new GridFavAdapter();
        favRv.setAdapter(gridFavAdapter);
        gridFavAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int id, int type) {
                Log.d(TAG, "onClick: fav id=" + id + ", type=" + type);
                jumpToDetail(id, type);
            }
        });
        mGridStyleViewModel.mFavoriteLiveData.observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(List<Favorite> favorites) {
                gridFavAdapter.setFavList(favorites);
            }
        });
    }

    public void initTvPaging(View v) {
        RecyclerView tvRv = v.findViewById(R.id.rv_grid_content_tv);
        tvRv.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        GridTvPageListAdapter gridTvPageListAdapter = new GridTvPageListAdapter();
        tvRv.setAdapter(gridTvPageListAdapter);
        gridTvPageListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int id, int type) {
                Log.d(TAG, "onClick: tv id=" + id + ", type=" + type);
                jumpToDetail(id, type);
            }
        });
        mGridStyleViewModel.mTvLiveData.observe(this, new Observer<PagedList<TvPageList>>() {
            @Override
            public void onChanged(PagedList<TvPageList> tvPageLists) {
                Log.e(TAG, "onChanged: tv");
                gridTvPageListAdapter.submitList(tvPageLists);
            }
        });
        mGridStyleViewModel.mTvFilterPage.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer page) {
                mMainViewModel.requestFilterTv(
                        mMainViewModel.getFilterIndex(),
                        page);
            }
        });
    }

    public void initMoviePaging(View v) {
        RecyclerView movieRv = v.findViewById(R.id.rv_grid_content_movie);
        movieRv.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        GridMoviePageListAdapter gridMoviePageListAdapter = new GridMoviePageListAdapter();
        movieRv.setAdapter(gridMoviePageListAdapter);
        gridMoviePageListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int id, int type) {
                Log.d(TAG, "onClick: movie id=" + id + ", type=" + type);
                jumpToDetail(id, type);
            }
        });
        mGridStyleViewModel.mMovieLiveData.observe(this, new Observer<PagedList<MoviePageList>>() {
            @Override
            public void onChanged(PagedList<MoviePageList> moviePageLists) {
                Log.e(TAG, "onChanged: movie");
                gridMoviePageListAdapter.submitList(moviePageLists);
            }
        });
        mGridStyleViewModel.mMovieFilterPage.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer page) {
                mMainViewModel.requestFilterMovie(
                        mMainViewModel.getFilterIndex(),
                        page);
            }
        });
    }

    private void releaseOldPagingObservers() {
        mGridStyleViewModel.mTvLiveData.removeObservers(this);
        mGridStyleViewModel.mMovieLiveData.removeObservers(this);
        mGridStyleViewModel.mTvFilterPage.removeObservers(this);
        mGridStyleViewModel.mMovieFilterPage.removeObservers(this);
    }

    private void releaseOldFavObservers() {
        mGridStyleViewModel.mFavoriteLiveData.removeObservers(this);
    }

    private void jumpToDetail(int id, int type) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(KEY_ID, id);
        intent.putExtra(KEY_TYPE, type);
        startActivity(intent);
    }

    public interface OnItemClickListener {
        void onItemClick(int id, int type);
    }
}
