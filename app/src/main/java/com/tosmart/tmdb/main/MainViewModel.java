package com.tosmart.tmdb.main;

import android.util.Log;

import com.tosmart.tmdb.data_source.MyDataSourceFactory;
import com.tosmart.tmdb.db.RoomManager;
import com.tosmart.tmdb.db.database.TMDatabase;
import com.tosmart.tmdb.db.entity.Tv;
import com.tosmart.tmdb.network.ApiObserver;
import com.tosmart.tmdb.network.ApiRequest;
import com.tosmart.tmdb.network.response.TvRes;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

import static com.tosmart.tmdb.network.ApiRequest.DESC;
import static com.tosmart.tmdb.network.ApiRequest.POPULARITY;

/**
 * @author ggz
 * @date 2020/10/22
 */
public class MainViewModel extends ViewModel {
    private final String TAG = getClass().getSimpleName();

    private CompositeDisposable mCompositeDisposable;

    public MainViewModel() {

    }

    private void requestPopularTv() {
        ApiObserver<TvRes> observer = new ApiObserver<TvRes>() {
            @Override
            public void onSuccess(TvRes popularTv) {
                Log.e(TAG, "onSuccess: page: " + popularTv.getPage());
                // 存数据库
                TMDatabase db = RoomManager.getInstance().getTMDatabase();
                db.getTvDao().insertList(popularTv.getResults());
                // 检查内容
                List<Tv> list = db.getTvDao().getAllTv();
                for (int i = 0; i < list.size(); i++) {
                    Log.d(TAG, "onSuccess: id: " + list.get(i).getId());
                }
                // 切换主线程刷新UI
//                popTvList.postValue(popularTv.getResults());
            }

            @Override
            public void onError(int errorCode, String message) {
                Log.e(TAG, "onError: ");
                // todo UI弹窗提示
            }
        };
        mCompositeDisposable.add(observer);
        String query = POPULARITY + DESC;
        Observable<TvRes> observable = new ApiRequest().discoverTv(query, 1);
        observable.subscribe(observer);
    }


    @Override
    protected void onCleared() {
        Log.d(TAG, "onCleared()");
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
        super.onCleared();
    }
}
