package com.tosmart.tmdb.content;

import android.view.View;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.tosmart.tmdb.BR;
import com.tosmart.tmdb.R;
import com.tosmart.tmdb.adapter.GridTvPageListAdapter;
import com.tosmart.tmdb.base.BaseFragment;
import com.tosmart.tmdb.db.entity.PopTv;
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

        GridTvPageListAdapter gridTvPageListAdapter = new GridTvPageListAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        RecyclerView recyclerView = v.findViewById(R.id.rv_grid_content_tv);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(gridTvPageListAdapter);

        mGridStyleViewModel.PopTvPagedList.observe(this, new Observer<PagedList<PopTv>>() {
            @Override
            public void onChanged(PagedList<PopTv> popTvs) {
                gridTvPageListAdapter.submitList(popTvs);
            }
        });

        mGridStyleViewModel.requestPage.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mMainViewModel.requestPopularTv(integer);
            }
        });
    }
}
