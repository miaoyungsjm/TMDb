package com.tosmart.tmdb.main.dialog;

import android.util.Log;

import com.tosmart.tmdb.db.entity.CommonBean;
import com.tosmart.tmdb.db.entity.Movie;
import com.tosmart.tmdb.db.entity.Tv;
import com.tosmart.tmdb.network.ApiObserver;
import com.tosmart.tmdb.network.ApiRequest;
import com.tosmart.tmdb.network.response.MovieRes;
import com.tosmart.tmdb.network.response.TvRes;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

import static com.tosmart.tmdb.network.ApiRequest.INDEX_MOVIE;
import static com.tosmart.tmdb.network.ApiRequest.INDEX_TV;

/**
 * @author ggz
 * @date 2020/10/30
 */
public class SearchViewModel extends ViewModel {
    private final String TAG = getClass().getSimpleName();

    public MutableLiveData<List<CommonBean>> mSearchResult = new MutableLiveData<>();

    private List<CommonBean> mSearchList = new ArrayList<>();

    private CompositeDisposable mCompositeDisposable;

    public SearchViewModel() {
        Log.e(TAG, "SearchViewModel()");
        mCompositeDisposable = new CompositeDisposable();
    }

    public void requestSearch(String keyword) {
        mSearchList.clear();

        searchTv(keyword);
        searchMovie(keyword);
    }

    public void searchTv(String keyword) {
        ApiObserver<TvRes> observer = new ApiObserver<TvRes>() {
            @Override
            public void onSuccess(TvRes tvs) {
                List<Tv> list = tvs.getResults();
                Log.e(TAG, "onSuccess: tv search: " + list.size());
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        Tv tv = list.get(i);
                        CommonBean common = new CommonBean(tv.getId(), INDEX_TV,
                                tv.getOriginalName(),
                                tv.getFirstAirDate(),
                                tv.getPosterPath(),
                                tv.getVoteAverage(),
                                0);
                        mSearchList.add(common);
                    }
                    mSearchResult.postValue(mSearchList);
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                Log.e(TAG, "onError: tv search: " + message);
            }
        };
        mCompositeDisposable.add(observer);
        Observable<TvRes> observable = new ApiRequest().searchTv(keyword, 1);
        observable.subscribe(observer);
    }

    private void searchMovie(String keyword) {
        ApiObserver<MovieRes> observer = new ApiObserver<MovieRes>() {
            @Override
            public void onSuccess(MovieRes movies) {
                List<Movie> list = movies.getResults();
                Log.e(TAG, "onSuccess: movie search: " + list.size());
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        Movie mv = list.get(i);
                        CommonBean common = new CommonBean(mv.getId(), INDEX_MOVIE,
                                mv.getOriginalTitle(),
                                mv.getReleaseDate(),
                                mv.getPosterPath(),
                                mv.getVoteAverage(),
                                0);
                        mSearchList.add(common);
                    }
                    mSearchResult.postValue(mSearchList);
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                Log.e(TAG, "onError: movie search: " + message);
            }
        };
        mCompositeDisposable.add(observer);
        Observable<MovieRes> observable = new ApiRequest().searchMovie(keyword, 1);
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
