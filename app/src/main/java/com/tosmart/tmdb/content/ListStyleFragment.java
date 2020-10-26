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
public class ListStyleFragment extends BaseFragment {

    private ListStyleViewModel mListStyleViewModel;

    @Override
    protected void initViewModel() {
        mListStyleViewModel = getFragmentViewModel(ListStyleViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_list_style, BR.vm, mListStyleViewModel);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View v) {

    }
}
