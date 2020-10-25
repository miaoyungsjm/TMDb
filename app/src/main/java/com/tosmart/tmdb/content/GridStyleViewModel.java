package com.tosmart.tmdb.content;

import com.tosmart.tmdb.data_source.TvDataSourceFactory;
import com.tosmart.tmdb.db.entity.Tv;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

/**
 * @author ggz
 * @date 2020/10/22
 */
public class GridStyleViewModel extends ViewModel {

    public LiveData<PagedList<Tv>> mPagedList;

    public GridStyleViewModel() {
//        PagedList.Config cfg = new PagedList.Config.Builder()
//                .setPageSize(20)
//                .setEnablePlaceholders(true)
//                .setInitialLoadSizeHint(10)
//                .build();
//        TMDatabase db = RoomManager.getInstance().getTMDatabase();
//        mPagedList = new LivePagedListBuilder<>(
//                db.getPopularityDao().getPopularityTv(0),
//                20).build();

        mPagedList = new LivePagedListBuilder<>(
                new TvDataSourceFactory(),
                20).build();

    }
}
