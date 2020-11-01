package com.tosmart.tmdb.main.gird;

import android.util.Log;

import com.tosmart.tmdb.db.RoomManager;
import com.tosmart.tmdb.db.database.TMDatabase;
import com.tosmart.tmdb.db.entity.CommonPageList;
import com.tosmart.tmdb.db.entity.Favorite;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ggz
 * @date 2020/10/22
 */
public class GridStyleViewModel extends ViewModel {
    private final String TAG = getClass().getSimpleName();

    public LiveData<PagedList<CommonPageList>> mTvLiveData;
    public LiveData<PagedList<CommonPageList>> mMovieLiveData;
    public MutableLiveData<Integer> mTvFilterPage;
    public MutableLiveData<Integer> mMovieFilterPage;

    public MutableLiveData<List<Favorite>> mFavoriteLiveData = new MutableLiveData<>();

    private CompositeDisposable mCompositeDisposable;

    public GridStyleViewModel() {
        Log.e(TAG, "GridStyleViewModel()");
        mCompositeDisposable = new CompositeDisposable();
    }

    public void initPagedList(int filterType, int filterOrder) {
        TMDatabase db = RoomManager.getInstance().getTMDatabase();
        DataSource.Factory<Integer, CommonPageList> tvDataSource =
                db.getFilterTvDao().getFilterTvPageList(filterType, filterOrder);

        DataSource.Factory<Integer, CommonPageList> movieDataSource =
                db.getFilterMovieDao().getFilterMoviePageList(filterType, filterOrder);

        mTvLiveData = new LivePagedListBuilder<>(
                tvDataSource,
                10)
                .setBoundaryCallback(mTvPageListCallback)
                .build();

        mMovieLiveData = new LivePagedListBuilder<>(
                movieDataSource,
                10)
                .setBoundaryCallback(mMoviePageListCallback)
                .build();

        mTvFilterPage = new MutableLiveData<>();
        mMovieFilterPage = new MutableLiveData<>();
    }

    public void initFavList() {
        TMDatabase db = RoomManager.getInstance().getTMDatabase();
        Single<List<Favorite>> single = db.getFavoriteDao().getAllFavorite()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mCompositeDisposable.add(single.subscribe(new Consumer<List<Favorite>>() {
            @Override
            public void accept(List<Favorite> favorites) throws Exception {
                mFavoriteLiveData.setValue(favorites);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        }));
    }

    private PagedList.BoundaryCallback<CommonPageList> mTvPageListCallback =
            new PagedList.BoundaryCallback<CommonPageList>() {
                @Override
                public void onZeroItemsLoaded() {
                    super.onZeroItemsLoaded();
                    Log.d(TAG, "TV onZeroItemsLoaded: ");
                    // 数据库没数据，网络请求
                    mTvFilterPage.setValue(1);
                }

                @Override
                public void onItemAtEndLoaded(@NonNull CommonPageList itemAtEnd) {
                    super.onItemAtEndLoaded(itemAtEnd);
                    Log.d(TAG, "Tv onItemAtEndLoaded: " + itemAtEnd.getPage());
                    // 数据库消耗完毕，网络请求
                    mTvFilterPage.setValue(itemAtEnd.getPage() + 1);
                }
            };

    private PagedList.BoundaryCallback<CommonPageList> mMoviePageListCallback =
            new PagedList.BoundaryCallback<CommonPageList>() {
                @Override
                public void onZeroItemsLoaded() {
                    super.onZeroItemsLoaded();
                    Log.d(TAG, "Movie onZeroItemsLoaded: ");
                    mMovieFilterPage.setValue(1);
                }

                @Override
                public void onItemAtEndLoaded(@NonNull CommonPageList itemAtEnd) {
                    super.onItemAtEndLoaded(itemAtEnd);
                    Log.d(TAG, "Movie onItemAtEndLoaded: " + itemAtEnd.getPage());
                    mMovieFilterPage.setValue(itemAtEnd.getPage() + 1);
                }
            };

    @Override
    protected void onCleared() {
        Log.e(TAG, "onCleared()");
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
        super.onCleared();
    }
}