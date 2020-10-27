package com.tosmart.tmdb.content;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.tosmart.tmdb.BR;
import com.tosmart.tmdb.R;
import com.tosmart.tmdb.adapter.GridMoviePageListAdapter;
import com.tosmart.tmdb.adapter.GridTvPageListAdapter;
import com.tosmart.tmdb.base.BaseFragment;
import com.tosmart.tmdb.db.entity.MoviePageList;
import com.tosmart.tmdb.db.entity.TvPageList;
import com.tosmart.tmdb.detail.DetailActivity;
import com.tosmart.tmdb.main.MainViewModel;

import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.tosmart.tmdb.detail.DetailActivity.KEY_ID;
import static com.tosmart.tmdb.detail.DetailActivity.KEY_TYPE;
import static com.tosmart.tmdb.network.ApiRequest.INDEX_MOVIE;
import static com.tosmart.tmdb.network.ApiRequest.INDEX_TV;

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
        // 初始化 paging
        mGridStyleViewModel.initPagedList(
                mMainViewModel.getFilterType(),
                mMainViewModel.getFilterOrder());

        // 排序回调，刷新数据源
        mMainViewModel.mFilterFlag.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer index) {
                Log.e(TAG, "onChanged: filterIndex = " + index);
                mMainViewModel.setFilterIndex(index);

                releaseOldObservers();
                mGridStyleViewModel.initPagedList(
                        mMainViewModel.getFilterType(),
                        mMainViewModel.getFilterOrder());
                initView(rootView);
            }
        });
    }

    @Override
    protected void initView(View v) {
        RecyclerView tvRv = v.findViewById(R.id.rv_grid_content_tv);
        tvRv.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        GridTvPageListAdapter gridTvPageListAdapter = new GridTvPageListAdapter();
        tvRv.setAdapter(gridTvPageListAdapter);
        gridTvPageListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int id) {
                Log.d(TAG, "onClick: tv id = " + id);
                jumpToDetail(id, INDEX_TV);
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

        RecyclerView movieRv = v.findViewById(R.id.rv_grid_content_movie);
        movieRv.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        GridMoviePageListAdapter gridMoviePageListAdapter = new GridMoviePageListAdapter();
        movieRv.setAdapter(gridMoviePageListAdapter);
        gridMoviePageListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int id) {
                Log.d(TAG, "onClick: movie id = " + id);
                jumpToDetail(id, INDEX_MOVIE);
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

    private void jumpToDetail(int id, int type) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(KEY_ID, id);
        intent.putExtra(KEY_TYPE, type);
        startActivity(intent);
    }

    private void releaseOldObservers() {
        mGridStyleViewModel.mTvLiveData.removeObservers(this);
        mGridStyleViewModel.mTvFilterPage.removeObservers(this);
        mGridStyleViewModel.mMovieLiveData.removeObservers(this);
        mGridStyleViewModel.mMovieFilterPage.removeObservers(this);
    }

    public interface OnItemClickListener {
        void onItemClick(int id);
    }
}
