package com.tosmart.tmdb.content;

import android.view.View;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.tosmart.tmdb.BR;
import com.tosmart.tmdb.R;
import com.tosmart.tmdb.adapter.GridTvPageAdapter;
import com.tosmart.tmdb.base.BaseFragment;
import com.tosmart.tmdb.db.entity.Tv;

import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author ggz
 * @date 2020/10/22
 */
public class GridStyleFragment extends BaseFragment {

    private GridStyleViewModel mGridStyleViewModel;

    @Override
    protected void initViewModel() {
        mGridStyleViewModel = getFragmentViewModel(GridStyleViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_grid_style, BR.vm, mGridStyleViewModel);
    }

    @Override
    protected void initView(View v) {

        GridTvPageAdapter gridTvPageAdapter = new GridTvPageAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        RecyclerView recyclerView = v.findViewById(R.id.rv_grid_content_tv);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(gridTvPageAdapter);

        mGridStyleViewModel.mPagedList.observe(this, new Observer<PagedList<Tv>>() {
            @Override
            public void onChanged(PagedList<Tv> tvs) {
                gridTvPageAdapter.submitList(tvs);
            }
        });
    }
}
