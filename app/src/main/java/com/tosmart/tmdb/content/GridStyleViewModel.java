package com.tosmart.tmdb.content;

import android.util.Log;

import com.tosmart.tmdb.db.RoomManager;
import com.tosmart.tmdb.db.entity.PopTv;
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

    public LiveData<PagedList<PopTv>> PopTvPagedList;
    public MutableLiveData<Integer> requestPage = new MutableLiveData<>();

    public GridStyleViewModel() {
//        PagedList.Config cfg = new PagedList.Config.Builder()
//                .setPageSize(20)
//                .setEnablePlaceholders(true)
//                .setInitialLoadSizeHint(10)
//                .build();

        PagedList.BoundaryCallback<PopTv> callback = new PagedList.BoundaryCallback<PopTv>() {
            @Override
            public void onZeroItemsLoaded() {
                super.onZeroItemsLoaded();
                Log.e(TAG, "onZeroItemsLoaded: ");
                requestPage.setValue(1);
            }

            @Override
            public void onItemAtEndLoaded(@NonNull PopTv itemAtEnd) {
                super.onItemAtEndLoaded(itemAtEnd);
                Log.e(TAG, "onItemAtEndLoaded: " + itemAtEnd);
                requestPage.setValue(itemAtEnd.getPage() + 1);
            }
        };

        TMDatabase db = RoomManager.getInstance().getTMDatabase();
        PopTvPagedList = new LivePagedListBuilder<>(
                db.getPopularityDao().getPopularityTv(0),
                10)
                .setBoundaryCallback(callback)
                .build();
    }
}
