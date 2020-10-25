package com.tosmart.tmdb.content;

import android.util.Log;

import com.tosmart.tmdb.db.RoomManager;
import com.tosmart.tmdb.db.entity.TvPageList;
import com.tosmart.tmdb.db.database.TMDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

/**
 * @author ggz
 * @date 2020/10/22
 */
public class GridStyleViewModel extends ViewModel {
    private final String TAG = getClass().getSimpleName();

    public LiveData<PagedList<TvPageList>> mTvPagedList;
    public MutableLiveData<Integer> mTvFilterPage = new MutableLiveData<>();

    public int filterType = 0;
    public int filterOrder = 0;

    private PagedList.BoundaryCallback<TvPageList> mTvPageListCallback = new PagedList.BoundaryCallback<TvPageList>() {
        @Override
        public void onZeroItemsLoaded() {
            super.onZeroItemsLoaded();
            Log.e(TAG, "onZeroItemsLoaded: ");
            // 数据库没数据，网络请求
            mTvFilterPage.setValue(1);
        }

        @Override
        public void onItemAtEndLoaded(@NonNull TvPageList itemAtEnd) {
            super.onItemAtEndLoaded(itemAtEnd);
            Log.e(TAG, "onItemAtEndLoaded: " + itemAtEnd);
            // 数据库消耗完毕，网络请求
            mTvFilterPage.setValue(itemAtEnd.getPage() + 1);
        }
    };

    public GridStyleViewModel() {
        TMDatabase db = RoomManager.getInstance().getTMDatabase();
        mTvPagedList = new LivePagedListBuilder<>(
                db.getFilterTvDao().getFilterTvPageList(filterType, filterOrder),
                10)
                .setBoundaryCallback(mTvPageListCallback)
                .build();
    }
}
