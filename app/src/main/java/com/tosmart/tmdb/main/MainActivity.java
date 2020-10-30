package com.tosmart.tmdb.main;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.tosmart.tmdb.BR;
import com.tosmart.tmdb.R;
import com.tosmart.tmdb.base.BaseActivity;

import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import static com.tosmart.tmdb.main.MainViewModel.STYLE_GRID;
import static com.tosmart.tmdb.main.MainViewModel.STYLE_LIST;


/**
 * @author ggz
 * @date 2020/10/22
 */
public class MainActivity extends BaseActivity {

    private MainViewModel mMainViewModel;

    private LinearLayout mTitleGirdStyleLl;
    private LinearLayout mTitleListStyleLl;

    @Override
    protected void initViewModel() {
        mMainViewModel = getActivityViewModel(MainViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_main, BR.vm, mMainViewModel)
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitleGirdStyleLl = findViewById(R.id.ll_main_title_grid);
        mTitleListStyleLl = findViewById(R.id.ll_main_title_list);
        setTitleState(STYLE_GRID);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                FilterDialogFragment filterDialogFragment =
                        FilterDialogFragment.getInstance();
                filterDialogFragment.show(getSupportFragmentManager());
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setTitleState(int index) {
        if (index == STYLE_GRID) {
            mTitleGirdStyleLl.setSelected(true);
            mTitleListStyleLl.setSelected(false);
        } else {
            mTitleGirdStyleLl.setSelected(false);
            mTitleListStyleLl.setSelected(true);
        }
    }

    public class ClickProxy {
        public void itemClick(View view) {
            switch (view.getId()) {
                case R.id.ll_main_title_grid:
                    if (mMainViewModel.style != STYLE_GRID) {
                        mMainViewModel.style = STYLE_GRID;
                        setTitleState(STYLE_GRID);
                        NavController nc = Navigation.findNavController(
                                MainActivity.this, R.id.content_fragment_host);
                        nc.navigate(R.id.action_listStyleFragment_to_gridStyleFragment);
                    }
                    break;
                case R.id.ll_main_title_list:
                    if (mMainViewModel.style != STYLE_LIST) {
                        mMainViewModel.style = STYLE_LIST;
                        setTitleState(STYLE_LIST);
                        NavController nc = Navigation.findNavController(
                                MainActivity.this, R.id.content_fragment_host);
                        nc.navigate(R.id.action_gridStyleFragment_to_listStyleFragment);
                    }
                    break;
                case R.id.ll_main_search:
                    SearchDialogFragment searchDialogFragment =
                            SearchDialogFragment.getInstance();
                    searchDialogFragment.setStyle(DialogFragment.STYLE_NORMAL,
                            R.style.style_search_dialog_fragment_full_screen);
                    searchDialogFragment.show(getSupportFragmentManager());
                    break;
                default:
                    break;
            }
        }
    }
}