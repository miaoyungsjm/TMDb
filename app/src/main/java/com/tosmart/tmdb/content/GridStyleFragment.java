package com.tosmart.tmdb.content;

import android.view.View;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.tosmart.tmdb.BR;
import com.tosmart.tmdb.R;
import com.tosmart.tmdb.base.BaseFragment;

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

    }
}
