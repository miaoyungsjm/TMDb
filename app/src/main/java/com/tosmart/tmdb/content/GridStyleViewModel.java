package com.tosmart.tmdb.content;

import com.tosmart.tmdb.data_source.MyDataSourceFactory;
import com.tosmart.tmdb.db.RoomManager;
import com.tosmart.tmdb.db.database.TMDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

/**
 * @author ggz
 * @date 2020/10/22
 */
public class GridStyleViewModel extends ViewModel {

    public LiveData<PagedList<String>> mPagedList;

    public GridStyleViewModel() {
//        PagedList.Config cfg = new PagedList.Config.Builder()
//                .setPageSize(10)
//                .setEnablePlaceholders(true)
//                .setInitialLoadSizeHint(10)
//                .build();
//        mPagedList = new LivePagedListBuilder<>(new MyDataSourceFactory(), cfg).build();

        TMDatabase db = RoomManager.getInstance().getTMDatabase();
        db.getTvDao().getAllTv();
        mPagedList = new LivePagedListBuilder<>(new MyDataSourceFactory(), 20).build();
    }
}
