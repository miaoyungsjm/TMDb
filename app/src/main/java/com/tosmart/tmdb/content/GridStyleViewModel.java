package com.tosmart.tmdb.content;

import android.util.Log;

import com.tosmart.tmdb.db.RoomManager;
import com.tosmart.tmdb.db.database.TMDatabase;
import com.tosmart.tmdb.db.entity.MoviePageList;
import com.tosmart.tmdb.db.entity.TvPageList;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

/**
 * @author ggz
 * @date 2020/10/22
 */
public class GridStyleViewModel extends ViewModel {
    private final String TAG = getClass().getSimpleName();

    private DataSource.Factory<Integer, TvPageList> mTvDataSource;
    public LiveData<PagedList<TvPageList>> mTvLiveData;
    public MediatorLiveData<PagedList<TvPageList>> mTvMediatorLiveData = new MediatorLiveData<>();
    public MutableLiveData<Integer> mTvFilterPage = new MutableLiveData<>();

    private DataSource.Factory<Integer, MoviePageList> mMovieDataSource;
    public LiveData<PagedList<MoviePageList>> mMovieLiveData;
    public MediatorLiveData<PagedList<MoviePageList>> mMovieMediatorLiveData = new MediatorLiveData<>();
    public MutableLiveData<Integer> mMovieFilterPage = new MutableLiveData<>();

    public void initDataSource(int filterType, int filterOrder) {
        TMDatabase db = RoomManager.getInstance().getTMDatabase();
        mTvDataSource = db.getFilterTvDao().getFilterTvPageList(filterType, filterOrder);
        mMovieDataSource = db.getFilterMovieDao().getFilterMoviePageList(filterType, filterOrder);
    }

    public void initPagedList() {
        mTvLiveData = new LivePagedListBuilder<>(
                mTvDataSource,
                10)
                .setBoundaryCallback(mTvPageListCallback)
                .build();
        mTvMediatorLiveData.addSource(mTvLiveData, new Observer<PagedList<TvPageList>>() {
            @Override
            public void onChanged(PagedList<TvPageList> tvPageLists) {
                mTvMediatorLiveData.setValue(tvPageLists);
            }
        });

        mMovieLiveData = new LivePagedListBuilder<>(
                mMovieDataSource,
                10)
                .setBoundaryCallback(mMoviePageListCallback)
                .build();
        mMovieMediatorLiveData.addSource(mMovieLiveData, new Observer<PagedList<MoviePageList>>() {
            @Override
            public void onChanged(PagedList<MoviePageList> moviePageLists) {
                mMovieMediatorLiveData.setValue(moviePageLists);
            }
        });
    }

    private PagedList.BoundaryCallback<TvPageList> mTvPageListCallback =
            new PagedList.BoundaryCallback<TvPageList>() {
                @Override
                public void onZeroItemsLoaded() {
                    super.onZeroItemsLoaded();
                    Log.e(TAG, "TV onZeroItemsLoaded: ");
                    // 数据库没数据，网络请求
                    mTvFilterPage.setValue(1);
                }

                @Override
                public void onItemAtEndLoaded(@NonNull TvPageList itemAtEnd) {
                    super.onItemAtEndLoaded(itemAtEnd);
                    Log.e(TAG, "Tv onItemAtEndLoaded: " + itemAtEnd);
                    // 数据库消耗完毕，网络请求
                    mTvFilterPage.setValue(itemAtEnd.getPage() + 1);
                }
            };

    private PagedList.BoundaryCallback<MoviePageList> mMoviePageListCallback =
            new PagedList.BoundaryCallback<MoviePageList>() {
                @Override
                public void onZeroItemsLoaded() {
                    super.onZeroItemsLoaded();
                    Log.e(TAG, "Movie onZeroItemsLoaded: ");
                    mMovieFilterPage.setValue(1);
                }

                @Override
                public void onItemAtEndLoaded(@NonNull MoviePageList itemAtEnd) {
                    super.onItemAtEndLoaded(itemAtEnd);
                    Log.e(TAG, "Movie onItemAtEndLoaded: " + itemAtEnd);
                    mMovieFilterPage.setValue(itemAtEnd.getPage() + 1);
                }
            };
}
