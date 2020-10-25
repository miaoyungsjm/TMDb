package com.tosmart.tmdb.main;

import android.util.Log;

import com.tosmart.tmdb.db.RoomManager;
import com.tosmart.tmdb.db.database.TMDatabase;
import com.tosmart.tmdb.db.entity.FilterTv;
import com.tosmart.tmdb.db.entity.Tv;
import com.tosmart.tmdb.network.ApiObserver;
import com.tosmart.tmdb.network.ApiRequest;
import com.tosmart.tmdb.network.response.TvRes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

import static com.tosmart.tmdb.network.ApiRequest.ASC;
import static com.tosmart.tmdb.network.ApiRequest.DESC;
import static com.tosmart.tmdb.network.ApiRequest.FIRT_AIR_DATE;
import static com.tosmart.tmdb.network.ApiRequest.POPULARITY;
import static com.tosmart.tmdb.network.ApiRequest.VOTE_AVERAGE;

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

    public void requestFilterTv(int ft, int fo, int page) {
        ApiObserver<TvRes> observer = new ApiObserver<TvRes>() {
            @Override
            public void onSuccess(TvRes tvRes) {
                Log.e(TAG, "onSuccess: page: " + tvRes.getPage());

                TMDatabase db = RoomManager.getInstance().getTMDatabase();
                List<Tv> tvList = tvRes.getResults();
                List<FilterTv> filterTvList = new ArrayList<>();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());
                String dateStr = simpleDateFormat.format(date);
                for (int i = 0; i < tvList.size(); i++) {
                    Log.d(TAG, "onSuccess: id: " + tvList.get(i).getId());
                    FilterTv filterTv = new FilterTv(tvList.get(i).getId(), ft, fo,
                            dateStr, tvRes.getPage(), i);
                    filterTvList.add(filterTv);
                }
                db.getFilterTvDao().insertList(filterTvList);
                db.getTvDao().insertList(tvList);
            }

            @Override
            public void onError(int errorCode, String message) {
                Log.e(TAG, "onError: message: " + message);
            }
        };
        mCompositeDisposable.add(observer);
        StringBuilder sb = new StringBuilder();
        switch (ft) {
            case 1:
                sb.append(VOTE_AVERAGE);
                break;
            case 2:
                sb.append(FIRT_AIR_DATE);
                break;
            case 0:
            default:
                sb.append(POPULARITY);
                break;
        }
        if (fo == 0) {
            sb.append(DESC);
        } else {
            sb.append(ASC);
        }
        String query = sb.toString();
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
