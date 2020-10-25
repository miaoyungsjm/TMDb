package com.tosmart.tmdb.main;

import android.util.Log;

import com.tosmart.tmdb.db.RoomManager;
import com.tosmart.tmdb.db.database.TMDatabase;
import com.tosmart.tmdb.db.entity.Popularity;
import com.tosmart.tmdb.network.ApiObserver;
import com.tosmart.tmdb.network.ApiRequest;
import com.tosmart.tmdb.network.response.TvRes;

import androidx.lifecycle.ViewModel;
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
        mCompositeDisposable = new CompositeDisposable();
    }

    public void requestPopularTv(int page) {
        ApiObserver<TvRes> observer = new ApiObserver<TvRes>() {
            @Override
            public void onSuccess(TvRes tvRes) {
                Log.e(TAG, "onSuccess: page: " + tvRes.getPage());
                // 存数据库
                TMDatabase db = RoomManager.getInstance().getTMDatabase();
                db.getTvDao().insertList(tvRes.getResults());
                for (int i = 0; i < tvRes.getResults().size(); i++) {
                    Log.d(TAG, "onSuccess: id: " + tvRes.getResults().get(i).getId());
                    Popularity popularity = new Popularity(
                            tvRes.getResults().get(i).getId(),
                            0,
                            "2020-10-25",
                            0);
                    db.getPopularityDao().insert(popularity);
                }
                // 切换主线程刷新UI
//                popTvList.postValue(popularTv.getResults());
            }

            @Override
            public void onError(int errorCode, String message) {
                Log.e(TAG, "onError: message: " + message);
            }
        };
        mCompositeDisposable.add(observer);
        String query = POPULARITY + DESC;
        Observable<TvRes> observable = new ApiRequest().discoverTv(query, page);
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
