package com.tosmart.tmdb.data_source;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

/**
 * @author ggz
 * @date 2020/10/23
 */
public class PagingDataSource extends PageKeyedDataSource<Integer, String> {
    private final String TAG = getClass().getSimpleName();

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, String> callback) {
        int size = params.requestedLoadSize;
        boolean pal = params.placeholdersEnabled;
        Log.d(TAG, "loadInitial: size = " + size);
        Log.d(TAG, "loadInitial: pal = " + pal);
        List<String> list = new ArrayList<>();
        list.add("Initial 1");
        list.add("Initial 2");
        list.add("Initial 3");
        callback.onResult(list, null, 2);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, String> callback) {
        int size = params.requestedLoadSize;
        int key = params.key;
        Log.d(TAG, "loadInitial: size = " + size);
        Log.d(TAG, "loadInitial: key = " + key);
        List<String> list = new ArrayList<>();
        list.add("Before 1");
        list.add("Before 2");
        list.add("Before 3");
        callback.onResult(list, params.key - 1);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, String> callback) {
        int size = params.requestedLoadSize;
        int key = params.key;
        Log.d(TAG, "loadInitial: size = " + size);
        Log.d(TAG, "loadInitial: key = " + key);
        List<String> list = new ArrayList<>();
        list.add("After 1");
        list.add("After 2");
        list.add("After 3");
        callback.onResult(list, params.key + 1);
    }
}
