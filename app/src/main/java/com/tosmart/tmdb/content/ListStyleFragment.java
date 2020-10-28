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
import com.tosmart.tmdb.db.entity.Favorite;
import com.tosmart.tmdb.db.entity.MoviePageList;
import com.tosmart.tmdb.db.entity.TvPageList;
import com.tosmart.tmdb.detail.DetailActivity;
import com.tosmart.tmdb.main.MainViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.tosmart.tmdb.content.ListStyleViewModel.TITLE_INDEX_FAV;
import static com.tosmart.tmdb.content.ListStyleViewModel.TITLE_INDEX_MOVIE;
import static com.tosmart.tmdb.content.ListStyleViewModel.TITLE_INDEX_TV;
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
        setTitleState(TITLE_INDEX_TV, view);
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

        OnItemClickListener listener = new OnItemClickListener() {
            @Override
            public void onItemClick(int id, int type) {
                Log.d(TAG, "onClick: id=" + id + ", type=" + type);
                jumpToDetail(id, type);
            }
        };

        RecyclerView recyclerView = v.findViewById(R.id.rv_list_content);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 6));

        ListTvPageListAdapter listTvPageListAdapter = new ListTvPageListAdapter();
        listTvPageListAdapter.setOnItemClickListener(listener);
        mListStyleViewModel.mTvLiveData.observe(this, new Observer<PagedList<TvPageList>>() {
            @Override
            public void onChanged(PagedList<TvPageList> tvPageLists) {
                Log.e(TAG, "onChanged: tv");
                listTvPageListAdapter.submitList(tvPageLists);
            }
        });
        mListStyleViewModel.mTvFilterPage.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mMainViewModel.requestFilterTv(
                        mMainViewModel.getFilterIndex(),
                        integer);
            }
        });

        ListMoviePageListAdapter listMoviePageListAdapter = new ListMoviePageListAdapter();
        listMoviePageListAdapter.setOnItemClickListener(listener);
        mListStyleViewModel.mMovieLiveData.observe(this, new Observer<PagedList<MoviePageList>>() {
            @Override
            public void onChanged(PagedList<MoviePageList> moviePageLists) {
                Log.e(TAG, "onChanged: movie");
                listMoviePageListAdapter.submitList(moviePageLists);
            }
        });
        mListStyleViewModel.mMovieFilterPage.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mMainViewModel.requestFilterTv(
                        mMainViewModel.getFilterIndex(),
                        integer);
            }
        });

        ListFavAdapter listFavAdapter = new ListFavAdapter();
        listFavAdapter.setOnItemClickListener(listener);
        mListStyleViewModel.mFavoriteLiveData.observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(List<Favorite> favorites) {
                listFavAdapter.setFavList(favorites);
            }
        });

        mListStyleViewModel.mTitleIndex.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer) {
                    case TITLE_INDEX_TV:
                        recyclerView.setAdapter(listTvPageListAdapter);
                        break;
                    case TITLE_INDEX_MOVIE:
                        recyclerView.setAdapter(listMoviePageListAdapter);
                        break;
                    case TITLE_INDEX_FAV:
                        recyclerView.setAdapter(listFavAdapter);
                        break;
                    default:
                }
            }
        });

        mListStyleViewModel.updateTitleIndex(mListStyleViewModel.getCurrentTitleIndex());
    }

    private void jumpToDetail(int id, int type) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(KEY_ID, id);
        intent.putExtra(KEY_TYPE, type);
        startActivity(intent);
    }

    public void setTitleState(int index, View view) {
        View root = view.getRootView();
        LinearLayout tvLl = root.findViewById(R.id.ll_list_title_tv);
        LinearLayout mvLl = root.findViewById(R.id.ll_list_title_movie);
        LinearLayout favLl = root.findViewById(R.id.ll_list_title_favorite);
        tvLl.setHovered(false);
        mvLl.setHovered(false);
        favLl.setHovered(false);
        if (index == TITLE_INDEX_TV) {
            tvLl.setHovered(true);
        } else if (index == TITLE_INDEX_MOVIE) {
            mvLl.setHovered(true);
        } else {
            favLl.setHovered(true);
        }
    }

    public class ClickProxy {
        public void itemClick(View view) {
            switch (view.getId()) {
                case R.id.ll_list_title_tv:
                    setTitleState(TITLE_INDEX_TV, view);
                    mListStyleViewModel.updateTitleIndex(TITLE_INDEX_TV);
                    break;
                case R.id.ll_list_title_movie:
                    setTitleState(TITLE_INDEX_MOVIE, view);
                    mListStyleViewModel.updateTitleIndex(TITLE_INDEX_MOVIE);
                    break;
                case R.id.ll_list_title_favorite:
                    setTitleState(TITLE_INDEX_FAV, view);
                    mListStyleViewModel.updateTitleIndex(TITLE_INDEX_FAV);
                    break;
                default:
                    break;
            }
        }
    }
}
