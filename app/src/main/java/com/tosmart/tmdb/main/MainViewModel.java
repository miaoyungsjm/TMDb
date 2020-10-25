package com.tosmart.tmdb.main;

import android.util.Log;

import com.tosmart.tmdb.db.RoomManager;
import com.tosmart.tmdb.db.database.TMDatabase;
import com.tosmart.tmdb.db.entity.FilterMovie;
import com.tosmart.tmdb.db.entity.FilterTv;
import com.tosmart.tmdb.db.entity.Movie;
import com.tosmart.tmdb.db.entity.Tv;
import com.tosmart.tmdb.network.ApiObserver;
import com.tosmart.tmdb.network.ApiRequest;
import com.tosmart.tmdb.network.response.MovieRes;
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
import static com.tosmart.tmdb.network.ApiRequest.INDEX_AVERAGE;
import static com.tosmart.tmdb.network.ApiRequest.INDEX_DATE;
import static com.tosmart.tmdb.network.ApiRequest.INDEX_DESC;
import static com.tosmart.tmdb.network.ApiRequest.INDEX_MOVIE;
import static com.tosmart.tmdb.network.ApiRequest.INDEX_POPULARITY;
import static com.tosmart.tmdb.network.ApiRequest.INDEX_TV;
import static com.tosmart.tmdb.network.ApiRequest.POPULARITY;
import static com.tosmart.tmdb.network.ApiRequest.RELEASE_DATE;
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
                Log.e(TAG, "onSuccess: tv page: " + tvRes.getPage());

                TMDatabase db = RoomManager.getInstance().getTMDatabase();
                List<Tv> list = tvRes.getResults();
                List<FilterTv> filterList = new ArrayList<>();
                String dateStr = buildDateValue();
                for (int i = 0; i < list.size(); i++) {
                    Log.d(TAG, "onSuccess: tv id: " + list.get(i).getId());
                    FilterTv filterTv = new FilterTv(list.get(i).getId(), ft, fo,
                            dateStr, tvRes.getPage(), i);
                    filterList.add(filterTv);
                }
                db.getFilterTvDao().insertList(filterList);
                db.getTvDao().insertList(list);
            }

            @Override
            public void onError(int errorCode, String message) {
                Log.e(TAG, "onError: tv message: " + message);
            }
        };
        mCompositeDisposable.add(observer);
        String query = buildQueryValue(ft, ft, INDEX_TV);
        Observable<TvRes> observable = new ApiRequest().discoverTv(query, page);
        observable.subscribe(observer);
    }

    public void requestFilterMovie(int ft, int fo, int page) {
        ApiObserver<MovieRes> observer = new ApiObserver<MovieRes>() {
            @Override
            public void onSuccess(MovieRes movieRes) {
                Log.e(TAG, "onSuccess: movie page: " + movieRes.getPage());

                TMDatabase db = RoomManager.getInstance().getTMDatabase();
                List<Movie> list = movieRes.getResults();
                List<FilterMovie> filterList = new ArrayList<>();
                String dateStr = buildDateValue();
                for (int i = 0; i < list.size(); i++) {
                    Log.d(TAG, "onSuccess: movie id: " + list.get(i).getId());
                    FilterMovie filterMovie = new FilterMovie(list.get(i).getId(), ft, fo,
                            dateStr, movieRes.getPage(), i);
                    filterList.add(filterMovie);
                }
                db.getFilterMovieDao().insertList(filterList);
                db.getMovieDao().insertList(list);
            }

            @Override
            public void onError(int errorCode, String message) {
                Log.e(TAG, "onError: movie message: " + message);
            }
        };
        mCompositeDisposable.add(observer);
        String query = buildQueryValue(ft, ft, INDEX_MOVIE);
        Observable<MovieRes> observable = new ApiRequest().discoverMovie(query, page);
        observable.subscribe(observer);
    }

    private String buildQueryValue(int ft, int fo, int type) {
        StringBuilder sb = new StringBuilder();
        switch (ft) {
            case INDEX_AVERAGE:
                sb.append(VOTE_AVERAGE);
                break;
            case INDEX_DATE:
                if (type == INDEX_TV) {
                    sb.append(FIRT_AIR_DATE);
                } else {
                    sb.append(RELEASE_DATE);
                }
                break;
            case INDEX_POPULARITY:
            default:
                sb.append(POPULARITY);
                break;
        }
        if (fo == INDEX_DESC) {
            sb.append(DESC);
        } else {
            sb.append(ASC);
        }
        return sb.toString();
    }

    private String buildDateValue() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
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
