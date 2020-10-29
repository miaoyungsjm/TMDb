package com.tosmart.tmdb.network;

import com.tosmart.tmdb.network.response.MovieRes;
import com.tosmart.tmdb.network.response.TvCredits;
import com.tosmart.tmdb.network.response.TvRes;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ggz
 * @date 2020/10/21
 */
public class ApiRequest {
    private final String KEY = "1dd1834a037b3355b06d80ea235bf021";
    public static final String POPULARITY = "popularity";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String FIRST_AIR_DATE = "first_air_date";
    public static final String RELEASE_DATE = "release_date";
    public static final String DESC = ".desc";
    public static final String ASC = ".asc";
    public static final int INDEX_POPULARITY = 0;
    public static final int INDEX_AVERAGE = 1;
    public static final int INDEX_DATE = 2;
    public static final int INDEX_DESC = 0;
    public static final int INDEX_ASC = 1;
    public static final int INDEX_TV = 0;
    public static final int INDEX_MOVIE = 1;

    public Observable<MovieRes> discoverMovie(String query, int page) {
        return ApiManager.getInstance().getApiService()
                .discoverMovie(KEY, query, page)
                .subscribeOn(Schedulers.io());
    }

    public Observable<TvCredits> queryMovieCredits(int id) {
        return ApiManager.getInstance().getApiService()
                .queryMovieCredits(id, KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MovieRes> searchMovie(String query, int page) {
        return ApiManager.getInstance().getApiService()
                .searchMovie(KEY, query, page)
                .subscribeOn(Schedulers.io());
    }

    public Observable<TvRes> discoverTv(String query, int page) {
        return ApiManager.getInstance().getApiService()
                .discoverTv(KEY, query, page)
                .subscribeOn(Schedulers.io());
    }

    public Observable<TvCredits> queryTvCredits(int id) {
        return ApiManager.getInstance().getApiService()
                .queryTvCredits(id, KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<TvRes> searchTv(String query, int page) {
        return ApiManager.getInstance().getApiService()
                .searchTv(KEY, query, page)
                .subscribeOn(Schedulers.io());
    }

    public Observable<TvRes> queryTvRecommendations(int id, int page) {
        return ApiManager.getInstance().getApiService()
                .queryTvRecommendations(id, KEY, page)
                .subscribeOn(Schedulers.io());
    }

    public Observable<MovieRes> queryMovieRecommendations(int id, int page) {
        return ApiManager.getInstance().getApiService()
                .queryMovieRecommendations(id, KEY, page)
                .subscribeOn(Schedulers.io());
    }
}
