package com.tosmart.tmdb.main;

import android.util.Log;

import com.blankj.utilcode.util.StringUtils;
import com.tosmart.tmdb.R;
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

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

import static com.tosmart.tmdb.network.ApiRequest.ASC;
import static com.tosmart.tmdb.network.ApiRequest.DESC;
import static com.tosmart.tmdb.network.ApiRequest.FIRT_AIR_DATE;
import static com.tosmart.tmdb.network.ApiRequest.INDEX_ASC;
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
    public static final int STYLE_GRID = 0;
    public static final int STYLE_LIST = 1;

    public int style = STYLE_GRID;

    public MutableLiveData<Integer> mFilterFlag = new MutableLiveData<>();
    private int mFilterIndex = 0;
    private int mFilterType = INDEX_POPULARITY;
    private int mFilterOrder = INDEX_DESC;
    private String mFilterTvQuery = "popularity.desc";
    private String mFilterMovieQuery = "popularity.desc";
    private CompositeDisposable mCompositeDisposable;

    public MainViewModel() {
        mCompositeDisposable = new CompositeDisposable();
    }

    /**
     * 进行网络请求，并存数据
     */
    public void requestFilterTv(int filterIndex, int page) {

        buildFilterType(filterIndex, INDEX_TV);

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
                    FilterTv filterTv = new FilterTv(list.get(i).getId(), mFilterType, mFilterOrder,
                            dateStr, tvRes.getPage(), i);
                    filterList.add(filterTv);
                }
                db.getFilterTvDao().insertList(filterList);
                db.getTvDao().insertList(list);
            }

            @Override
            public void onError(int errorCode, String message) {

            }
        };
        mCompositeDisposable.add(observer);
        Observable<TvRes> observable = new ApiRequest().discoverTv(mFilterTvQuery, page);
        observable.subscribe(observer);
    }

    public void requestFilterMovie(int filterIndex, int page) {

        buildFilterType(filterIndex, INDEX_MOVIE);

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
                    FilterMovie filterMovie = new FilterMovie(list.get(i).getId(), mFilterType, mFilterOrder,
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
        Observable<MovieRes> observable = new ApiRequest().discoverMovie(mFilterMovieQuery, page);
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

    private void buildFilterType(int index, int type) {
        StringBuilder sb = new StringBuilder();
        String[] filterList = StringUtils.getStringArray(R.array.filter_list);
        if (filterList[index].equals(StringUtils.getString(R.string.str_dialog_filter_item_pop_desc))) {
            mFilterType = INDEX_POPULARITY;
            mFilterOrder = INDEX_DESC;
            sb.append(POPULARITY);
            sb.append(DESC);
            mFilterTvQuery = sb.toString();
            mFilterMovieQuery = sb.toString();
        }
        if (filterList[index].equals(StringUtils.getString(R.string.str_dialog_filter_item_pop_asc))) {
            mFilterType = INDEX_POPULARITY;
            mFilterOrder = INDEX_ASC;
            sb.append(POPULARITY);
            sb.append(ASC);
            mFilterTvQuery = sb.toString();
            mFilterMovieQuery = sb.toString();
        }
        if (filterList[index].equals(StringUtils.getString(R.string.str_dialog_filter_item_average_desc))) {
            mFilterType = INDEX_AVERAGE;
            mFilterOrder = INDEX_DESC;
            sb.append(VOTE_AVERAGE);
            sb.append(DESC);
            mFilterTvQuery = sb.toString();
            mFilterMovieQuery = sb.toString();
        }
        if (filterList[index].equals(StringUtils.getString(R.string.str_dialog_filter_item_average_asc))) {
            mFilterType = INDEX_AVERAGE;
            mFilterOrder = INDEX_ASC;
            sb.append(VOTE_AVERAGE);
            sb.append(ASC);
            mFilterTvQuery = sb.toString();
            mFilterMovieQuery = sb.toString();
        }
        if (filterList[index].equals(StringUtils.getString(R.string.str_dialog_filter_item_date_desc))) {
            mFilterType = INDEX_DATE;
            mFilterOrder = INDEX_DESC;
            if (type == INDEX_TV) {
                sb.append(FIRT_AIR_DATE);
                sb.append(DESC);
                mFilterTvQuery = sb.toString();
            } else {
                sb.append(RELEASE_DATE);
                sb.append(DESC);
                mFilterMovieQuery = sb.toString();
            }
        }
        if (filterList[index].equals(StringUtils.getString(R.string.str_dialog_filter_item_date_asc))) {
            mFilterType = INDEX_DATE;
            mFilterOrder = INDEX_ASC;
            if (type == INDEX_TV) {
                sb.append(FIRT_AIR_DATE);
                sb.append(ASC);
                mFilterTvQuery = sb.toString();
            } else {
                sb.append(RELEASE_DATE);
                sb.append(ASC);
                mFilterMovieQuery = sb.toString();
            }
        }
    }

    private String buildDateValue() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public int getFilterIndex() {
        return mFilterIndex;
    }

    public void setFilterIndex(int index) {
        this.mFilterIndex = index;
        buildFilterType(index, INDEX_TV);
        buildFilterType(index, INDEX_MOVIE);
    }

    public int getFilterType() {
        return mFilterType;
    }

    public int getFilterOrder() {
        return mFilterOrder;
    }
}
