package com.tosmart.tmdb.data_source;

import android.util.Log;

import com.tosmart.tmdb.db.RoomManager;
import com.tosmart.tmdb.db.database.TMDatabase;
import com.tosmart.tmdb.db.entity.Popularity;
import com.tosmart.tmdb.db.entity.Tv;
import com.tosmart.tmdb.network.ApiObserver;
import com.tosmart.tmdb.network.ApiRequest;
import com.tosmart.tmdb.network.response.TvRes;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import io.reactivex.Observable;

import static com.tosmart.tmdb.network.ApiRequest.DESC;
import static com.tosmart.tmdb.network.ApiRequest.POPULARITY;

/**
 * @author ggz
 * @date 2020/10/23
 */
public class TvDataSource extends PageKeyedDataSource<Integer, Tv> {
    private final String TAG = getClass().getSimpleName();

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Tv> callback) {
        int size = params.requestedLoadSize;
        boolean pal = params.placeholdersEnabled;
        Log.d(TAG, "loadInitial: size = " + size);
        Log.d(TAG, "loadInitial: pal = " + pal);

        List<Tv> list = new ArrayList<>();
        // todo 数据获取加载逻辑
        callback.onResult(list, null, 2);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Tv> callback) {
        int size = params.requestedLoadSize;
        int key = params.key;
        Log.d(TAG, "loadInitial: size = " + size);
        Log.d(TAG, "loadInitial: key = " + key);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Tv> callback) {
        int size = params.requestedLoadSize;
        int key = params.key;
        Log.d(TAG, "loadInitial: size = " + size);
        Log.d(TAG, "loadInitial: key = " + key);
    }
}
