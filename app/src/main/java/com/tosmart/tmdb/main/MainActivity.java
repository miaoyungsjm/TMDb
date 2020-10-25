package com.tosmart.tmdb.main;

import android.os.Bundle;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.tosmart.tmdb.BR;
import com.tosmart.tmdb.R;
import com.tosmart.tmdb.base.BaseActivity;

/**
 * @author ggz
 * @date 2020/10/22
 */
public class MainActivity extends BaseActivity {

    private MainViewModel mMainViewModel;

    @Override
    protected void initViewModel() {
        mMainViewModel = getActivityViewModel(MainViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_main, BR.vm, mMainViewModel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mMainViewModel.requestPopularTv(1);
    }
}