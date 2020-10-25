package com.tosmart.tmdb.main;

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

                TMDatabase db = RoomManager.getInstance().getTMDatabase();
                List<Tv> tvList = tvRes.getResults();
                List<Popularity> popList = new ArrayList<>();
                for (int i = 0; i < tvList.size(); i++) {
                    Log.d(TAG, "onSuccess: id: " + tvList.get(i).getId());
                    Popularity pop = new Popularity(
                            tvList.get(i).getId(),
                            0,
                            "2020-10-25",
                            0,
                            tvRes.getPage());
                    popList.add(pop);
                }
                db.getPopularityDao().insertList(popList);
                db.getTvDao().insertList(tvList);

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
