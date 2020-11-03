package com.tosmart.tmdb.main.gird;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.tosmart.tmdb.BR;
import com.tosmart.tmdb.R;
import com.tosmart.tmdb.adapter.CommonAdapter;
import com.tosmart.tmdb.adapter.OnItemClickListener;
import com.tosmart.tmdb.adapter.SpacingItemDecoration;
import com.tosmart.tmdb.adapter.paged_list_adapter.CommonPagedListAdapter;
import com.tosmart.tmdb.base.BaseFragment;
import com.tosmart.tmdb.databinding.ItemGridStyleBinding;
import com.tosmart.tmdb.db.entity.CommonBean;
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

    private RecyclerView mContentFavRv;
    private RecyclerView mContentTvRv;
    private RecyclerView mContentMovieRv;

    private CommonAdapter<ItemGridStyleBinding> mFavAdapter;
    private CommonPagedListAdapter<ItemGridStyleBinding> mTvPageListAdapter;
    private CommonPagedListAdapter<ItemGridStyleBinding> mMoviePageListAdapter;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onViewCreated:");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        Log.e(TAG, "onResume:");
        // 详情回来刷新 fav data
        mGridStyleViewModel.initFavList();
        super.onResume();
    }

    @Override
    protected void initData() {
        Log.e(TAG, "initData:");

        // 初始化 paging data
        mGridStyleViewModel.initPagedList(
                mMainViewModel.getFilterType(),
                mMainViewModel.getFilterOrder());

        // 初始化 fav data
        mGridStyleViewModel.initFavList();

        // 排序回调，刷新数据源
        mMainViewModel.mFilterFlag.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer index) {
                Log.d(TAG, "onChanged: filterIndex = " + index);
                mMainViewModel.setFilterIndex(index);

                releaseOldPagingObservers();
                mGridStyleViewModel.initPagedList(
                        mMainViewModel.getFilterType(),
                        mMainViewModel.getFilterOrder());
                initTvPageListAdapter();
                initMoviePageListAdapter();
            }
        });
    }

    @Override
    protected void initView(View v) {
        int spacingLeft = SizeUtils.dp2px(21);
        SpacingItemDecoration decoration =
                new SpacingItemDecoration(0, spacingLeft, 0);

        mContentFavRv = v.findViewById(R.id.rv_grid_content_favorite);
        mContentFavRv.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        mContentFavRv.addItemDecoration(decoration);

        mContentTvRv = v.findViewById(R.id.rv_grid_content_tv);
        mContentTvRv.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        mContentTvRv.addItemDecoration(decoration);

        mContentMovieRv = v.findViewById(R.id.rv_grid_content_movie);
        mContentMovieRv.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        mContentMovieRv.addItemDecoration(decoration);

        initFavAdapter();
        initTvPageListAdapter();
        initMoviePageListAdapter();
    }

    public void initFavAdapter() {
        mFavAdapter = new CommonAdapter<>(R.layout.item_grid_style);
        mFavAdapter.setOnItemClickListener(mListener);
        mContentFavRv.setAdapter(mFavAdapter);

        mGridStyleViewModel.mFavoriteLiveData.observe(this, new Observer<List<CommonBean>>() {
            @Override
            public void onChanged(List<CommonBean> favorites) {
                Log.d(TAG, "onChanged: fav");
                mFavAdapter.setCommonList(favorites);
                // todo: focus loss
//                mContentFavRv.requestFocus();
            }
        });
    }

    public void initTvPageListAdapter() {
        mTvPageListAdapter = new CommonPagedListAdapter<>(R.layout.item_grid_style);
        mTvPageListAdapter.setOnItemClickListener(mListener);
        mContentTvRv.setAdapter(mTvPageListAdapter);

        mGridStyleViewModel.mTvLiveData.observe(this, new Observer<PagedList<CommonBean>>() {
            @Override
            public void onChanged(PagedList<CommonBean> tvPageLists) {
                Log.d(TAG, "onChanged: tv");
                mTvPageListAdapter.submitList(tvPageLists);
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

    public void initMoviePageListAdapter() {
        mMoviePageListAdapter = new CommonPagedListAdapter<>(R.layout.item_grid_style);
        mMoviePageListAdapter.setOnItemClickListener(mListener);
        mContentMovieRv.setAdapter(mMoviePageListAdapter);

        mGridStyleViewModel.mMovieLiveData.observe(this, new Observer<PagedList<CommonBean>>() {
            @Override
            public void onChanged(PagedList<CommonBean> moviePageLists) {
                Log.d(TAG, "onChanged: movie");
                mMoviePageListAdapter.submitList(moviePageLists);
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

    private OnItemClickListener mListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int id, int type) {
            Log.d(TAG, "onClick: id=" + id + ", type=" + type);
            jumpToDetail(id, type);
        }
    };

    private void jumpToDetail(int id, int type) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(KEY_ID, id);
        intent.putExtra(KEY_TYPE, type);
        startActivity(intent);
    }
}
