package com.tosmart.tmdb.content;

import android.view.View;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.tosmart.tmdb.BR;
import com.tosmart.tmdb.R;
import com.tosmart.tmdb.adapter.GridMoviePageListAdapter;
import com.tosmart.tmdb.adapter.GridTvPageListAdapter;
import com.tosmart.tmdb.base.BaseFragment;
import com.tosmart.tmdb.db.entity.MoviePageList;
import com.tosmart.tmdb.db.entity.TvPageList;
import com.tosmart.tmdb.main.MainViewModel;

import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    protected void initView(View v) {
        RecyclerView tvRv = v.findViewById(R.id.rv_grid_content_tv);
        tvRv.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        GridTvPageListAdapter gridTvPageListAdapter = new GridTvPageListAdapter();
        tvRv.setAdapter(gridTvPageListAdapter);
        mGridStyleViewModel.mTvPagedList.observe(this, new Observer<PagedList<TvPageList>>() {
            @Override
            public void onChanged(PagedList<TvPageList> tvPageLists) {
                gridTvPageListAdapter.submitList(tvPageLists);
            }
        });
        mGridStyleViewModel.mTvFilterPage.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer page) {
                mMainViewModel.requestFilterTv(
                        mGridStyleViewModel.filterType,
                        mGridStyleViewModel.filterOrder,
                        page);
            }
        });


        RecyclerView movieRv = v.findViewById(R.id.rv_grid_content_movie);
        movieRv.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        GridMoviePageListAdapter gridMoviePageListAdapter = new GridMoviePageListAdapter();
        movieRv.setAdapter(gridMoviePageListAdapter);
        mGridStyleViewModel.mMoviePagedList.observe(this, new Observer<PagedList<MoviePageList>>() {
            @Override
            public void onChanged(PagedList<MoviePageList> moviePageLists) {
                gridMoviePageListAdapter.submitList(moviePageLists);
            }
        });
        mGridStyleViewModel.mMovieFilterPage.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer page) {
                mMainViewModel.requestFilterMovie(
                        mGridStyleViewModel.filterType,
                        mGridStyleViewModel.filterOrder,
                        page);
            }
        });
    }
}
