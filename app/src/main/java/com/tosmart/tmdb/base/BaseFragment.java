package com.tosmart.tmdb.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kunminx.architecture.ui.page.DataBindingFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author ggz
 * @date 2020/10/15
 */
public abstract class BaseFragment extends DataBindingFragment {

    protected View rootView = null;
    protected boolean isNavigationViewInit = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = super.onCreateView(inflater, container, savedInstanceState);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (!isNavigationViewInit) {
            super.onViewCreated(view, savedInstanceState);
            initData();
            initView(view);
            isNavigationViewInit = true;
        }
    }

    protected abstract void initData();

    protected abstract void initView(View v);

    @Override
    public void onDestroyView() {
        ViewGroup viewGroup = (ViewGroup) rootView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(rootView);
        }
        super.onDestroyView();
    }
}
