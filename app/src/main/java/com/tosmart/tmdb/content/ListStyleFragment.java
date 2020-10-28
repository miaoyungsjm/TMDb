package com.tosmart.tmdb.content;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.tosmart.tmdb.BR;
import com.tosmart.tmdb.R;
import com.tosmart.tmdb.adapter.ListFavAdapter;
import com.tosmart.tmdb.adapter.ListMoviePageListAdapter;
import com.tosmart.tmdb.adapter.ListTvPageListAdapter;
import com.tosmart.tmdb.adapter.OnItemClickListener;
import com.tosmart.tmdb.base.BaseFragment;
import com.tosmart.tmdb.detail.DetailActivity;
import com.tosmart.tmdb.main.MainViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.tosmart.tmdb.content.ListStyleViewModel.INDEX_FAV;
import static com.tosmart.tmdb.content.ListStyleViewModel.INDEX_MOVIE;
import static com.tosmart.tmdb.content.ListStyleViewModel.INDEX_TV;
import static com.tosmart.tmdb.detail.DetailActivity.KEY_ID;
import static com.tosmart.tmdb.detail.DetailActivity.KEY_TYPE;

/**
 * @author ggz
 * @date 2020/10/22
 */
public class ListStyleFragment extends BaseFragment {
    private final String TAG = getClass().getSimpleName();

    private ListStyleViewModel mListStyleViewModel;
    private MainViewModel mMainViewModel;

    @Override
    protected void initViewModel() {
        mListStyleViewModel = getFragmentViewModel(ListStyleViewModel.class);
        mMainViewModel = getActivityViewModel(MainViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_list_style, BR.vm, mListStyleViewModel)
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setTitleState(INDEX_TV);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initData() {
        mListStyleViewModel.initPagedList(
                mMainViewModel.getFilterType(),
                mMainViewModel.getFilterOrder());

        mListStyleViewModel.initFavList();

//        mMainViewModel.mFilterFlag.observe(getViewLifecycleOwner(), new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer index) {
//                Log.e(TAG, "onChanged: filterIndex = " + index);
//                mMainViewModel.setFilterIndex(index);
//
//                mListStyleViewModel.initPagedList(
//                        mMainViewModel.getFilterType(),
//                        mMainViewModel.getFilterOrder());
//                if (mListStyleViewModel.currentIndex == INDEX_TV){
//                    initTvPaging(rootView);
//                } else if (mListStyleViewModel.currentIndex == INDEX_MOVIE) {
//                    initMoviePaging(rootView);
//                } else {
//                    initFav(rootView);
//                }
//            }
//        });
    }

    @Override
    protected void initView(View v) {
        RecyclerView recyclerView = v.findViewById(R.id.rv_list_content);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 6));
        if (mListStyleViewModel.currentIndex == INDEX_TV) {
            ListTvPageListAdapter listTvPageListAdapter = new ListTvPageListAdapter();
            recyclerView.setAdapter(listTvPageListAdapter);
            listTvPageListAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int id, int type) {
                    Log.d(TAG, "onClick: tv id=" + id + ", type=" + type);
                    jumpToDetail(id, type);
                }
            });




        } else if (mListStyleViewModel.currentIndex == INDEX_MOVIE) {
            ListMoviePageListAdapter listMoviePageListAdapter = new ListMoviePageListAdapter();
            recyclerView.setAdapter(listMoviePageListAdapter);



        } else {
            ListFavAdapter listFavAdapter = new ListFavAdapter();
            recyclerView.setAdapter(listFavAdapter);

        }
    }

    private void jumpToDetail(int id, int type) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(KEY_ID, id);
        intent.putExtra(KEY_TYPE, type);
        startActivity(intent);
    }

    public void setTitleState(int index) {
        LinearLayout tvLl = rootView.findViewById(R.id.ll_list_title_tv);
        LinearLayout mvLl = rootView.findViewById(R.id.ll_list_title_movie);
        LinearLayout favLl = rootView.findViewById(R.id.ll_list_title_favorite);
        tvLl.setHovered(false);
        mvLl.setHovered(false);
        favLl.setHovered(false);
        if (index == INDEX_TV) {
            tvLl.setHovered(true);
        } else if (index == INDEX_MOVIE) {
            mvLl.setHovered(true);
        } else {
            favLl.setHovered(true);
        }
    }

    public class ClickProxy {
        public void itemClick(View view) {
            switch (view.getId()) {
                case R.id.ll_list_title_tv:
                    setTitleState(INDEX_TV);
                    mListStyleViewModel.currentIndex = INDEX_TV;
                    break;
                case R.id.ll_list_title_movie:
                    setTitleState(INDEX_MOVIE);
                    mListStyleViewModel.currentIndex = INDEX_MOVIE;
                    break;
                case R.id.ll_list_title_favorite:
                    setTitleState(INDEX_FAV);
                    mListStyleViewModel.currentIndex = INDEX_FAV;
                    break;
                default:
                    break;
            }
        }
    }
}
