package com.tosmart.tmdb.base;

import android.graphics.Color;
import android.os.Bundle;

import com.blankj.utilcode.util.BarUtils;
import com.kunminx.architecture.ui.page.DataBindingActivity;

import androidx.annotation.Nullable;

/**
 * @author ggz
 * @date 2020/10/10
 */
public abstract class BaseActivity extends DataBindingActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

//        BarUtils.setStatusBarColor(this, Color.TRANSPARENT);
//        BarUtils.setStatusBarLightMode(this, true);

        super.onCreate(savedInstanceState);
    }
}
