package com.tosmart.tmdb.main.list;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.tosmart.tmdb.BR;
import com.tosmart.tmdb.R;
import com.tosmart.tmdb.adapter.ListStyleFavAdapter;
import com.tosmart.tmdb.adapter.OnItemClickListener;
import com.tosmart.tmdb.adapter.SpacingItemDecoration;
import com.tosmart.tmdb.adapter.paged_list_adapter.CommonPagedListAdapter;
import com.tosmart.tmdb.base.BaseFragment;
import com.tosmart.tmdb.databinding.ItemListStyleBinding;
import com.tosmart.tmdb.db.entity.CommonBean;
import com.tosmart.tmdb.db.entity.Favorite;
import com.tosmart.tmdb.detail.DetailActivity;
import com.tosmart.tmdb.main.MainViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.tosmart.tmdb.detail.DetailActivity.KEY_ID;
import static com.tosmart.tmdb.detail.DetailActivity.KEY_TYPE;
import static com.tosmart.tmdb.main.list.ListStyleViewModel.TITLE_INDEX_FAV;
import static com.tosmart.tmdb.main.list.ListStyleViewModel.TITLE_INDEX_MOVIE;
import static com.tosmart.tmdb.main.list.ListStyleViewModel.TITLE_INDEX_TV;
import static com.tosmart.tmdb.network.ApiRequest.INDEX_MOVIE;
import static com.tosmart.tmdb.network.ApiRequest.INDEX_TV;

/**
 * @author ggz
 * @date 2020/10/22
 */
public class ListStyleFragment extends BaseFragment {
    private final String TAG = getClass().getSimpleName();

    private ListStyleViewModel mListStyleViewModel;
    private MainViewModel mMainViewModel;

    private RecyclerView mContentRv;
    private CommonPagedListAdapter<ItemListStyleBinding> mTvPagedListAdapter;
    private CommonPagedListAdapter<ItemListStyleBinding> mMoviePagedListAdapter;
    private ListStyleFavAdapter mFavAdapter;

    private LinearLayout mTitleTvLl;
    private LinearLayout mTitleMovieLl;
    private LinearLayout mTitleFavLl;

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
        Log.e(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        Log.e(TAG, "onResume: ");
        mListStyleViewModel.initFavList();
        super.onResume();
    }

    @Override
    protected void initData() {
        Log.e(TAG, "initData: ");
        mListStyleViewModel.initPagedList(
                mMainViewModel.getFilterType(),
                mMainViewModel.getFilterOrder());

        mListStyleViewModel.initFavList();

        mMainViewModel.mFilterFlag.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer index) {
                Log.d(TAG, "onChanged: filterIndex = " + index);
                mMainViewModel.setFilterIndex(index);

                releaseOldPagingObservers();
                mListStyleViewModel.initPagedList(
                        mMainViewModel.getFilterType(),
                        mMainViewModel.getFilterOrder());
                initTvPageListAdapter();
                initMoviePageListAdapter();
                mListStyleViewModel.mTitleIndex.setValue(mListStyleViewModel.getCurrentTitleIndex());
            }
        });
    }

    @Override
    protected void initView(View v) {
        mTitleTvLl = v.findViewById(R.id.ll_list_title_tv);
        mTitleMovieLl = v.findViewById(R.id.ll_list_title_movie);
        mTitleFavLl = v.findViewById(R.id.ll_list_title_favorite);
        setTitleState(TITLE_INDEX_TV);

        mContentRv = v.findViewById(R.id.rv_list_content);
        mContentRv.setLayoutManager(new GridLayoutManager(getContext(), 6));
        int spacingLeft = SizeUtils.dp2px(24);
        int spacingTop = SizeUtils.dp2px(43);
        SpacingItemDecoration decoration =
                new SpacingItemDecoration(6, spacingLeft, spacingTop);
        mContentRv.addItemDecoration(decoration);
        mContentRv.requestFocus();

        initTvPageListAdapter();
        initMoviePageListAdapter();
        initFavAdapter();

        mListStyleViewModel.mTitleIndex.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer) {
                    case TITLE_INDEX_TV:
                        mContentRv.setAdapter(mTvPagedListAdapter);
                        break;
                    case TITLE_INDEX_MOVIE:
                        mContentRv.setAdapter(mMoviePagedListAdapter);
                        break;
                    case TITLE_INDEX_FAV:
                        mContentRv.setAdapter(mFavAdapter);
                        break;
                    default:
                }
            }
        });

        mListStyleViewModel.updateTitleIndex(mListStyleViewModel.getCurrentTitleIndex());
    }

    private void initTvPageListAdapter() {
        mTvPagedListAdapter = new CommonPagedListAdapter<>(R.layout.item_list_style, INDEX_TV);
        mTvPagedListAdapter.setOnItemClickListener(mListener);

        mListStyleViewModel.mTvLiveData.observe(this, new Observer<PagedList<CommonBean>>() {
            @Override
            public void onChanged(PagedList<CommonBean> tvPageLists) {
                Log.d(TAG, "onChanged: tv size=" + tvPageLists.size());
                mTvPagedListAdapter.submitList(tvPageLists);
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
    }

    private void initMoviePageListAdapter() {
        mMoviePagedListAdapter = new CommonPagedListAdapter<>(R.layout.item_list_style, INDEX_MOVIE);
        mMoviePagedListAdapter.setOnItemClickListener(mListener);

        mListStyleViewModel.mMovieLiveData.observe(this, new Observer<PagedList<CommonBean>>() {
            @Override
            public void onChanged(PagedList<CommonBean> moviePageLists) {
                Log.d(TAG, "onChanged: movie size=" + moviePageLists.size());
                mMoviePagedListAdapter.submitList(moviePageLists);
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
    }

    private void releaseOldPagingObservers() {
        mListStyleViewModel.mTvLiveData.removeObservers(this);
        mListStyleViewModel.mMovieLiveData.removeObservers(this);
        mListStyleViewModel.mTvFilterPage.removeObservers(this);
        mListStyleViewModel.mMovieFilterPage.removeObservers(this);
    }

    private void initFavAdapter() {
        mFavAdapter = new ListStyleFavAdapter();
        mFavAdapter.setOnItemClickListener(mListener);
        mListStyleViewModel.mFavoriteLiveData.observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(List<Favorite> favorites) {
                Log.d(TAG, "onChanged: fav");
                mFavAdapter.setFavList(favorites);
            }
        });
    }

    private OnItemClickListener mListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int id, int type) {
            Log.d(TAG, "onClick: id=" + id + ", type=" + type);
            jumpToDetail(id, type);
        }
    };

    private void jumpToDetail(int id, int type) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(KEY_ID, id);
        intent.putExtra(KEY_TYPE, type);
        startActivity(intent);
    }

    public void setTitleState(int index) {
        mTitleTvLl.setSelected(false);
        mTitleMovieLl.setSelected(false);
        mTitleFavLl.setSelected(false);
        if (index == TITLE_INDEX_TV) {
            mTitleTvLl.setSelected(true);
        } else if (index == TITLE_INDEX_MOVIE) {
            mTitleMovieLl.setSelected(true);
        } else {
            mTitleFavLl.setSelected(true);
        }
    }

    public class ClickProxy {
        public void itemClick(View view) {
            switch (view.getId()) {
                case R.id.ll_list_title_tv:
                    setTitleState(TITLE_INDEX_TV);
                    mListStyleViewModel.updateTitleIndex(TITLE_INDEX_TV);
                    break;
                case R.id.ll_list_title_movie:
                    setTitleState(TITLE_INDEX_MOVIE);
                    mListStyleViewModel.updateTitleIndex(TITLE_INDEX_MOVIE);
                    break;
                case R.id.ll_list_title_favorite:
                    setTitleState(TITLE_INDEX_FAV);
                    mListStyleViewModel.updateTitleIndex(TITLE_INDEX_FAV);
                    break;
                default:
                    break;
            }
        }
    }
}
