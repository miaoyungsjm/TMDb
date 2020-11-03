package com.tosmart.tmdb.detail;

import android.util.Log;

import com.blankj.utilcode.util.StringUtils;
import com.tosmart.tmdb.R;
import com.tosmart.tmdb.db.RoomManager;
import com.tosmart.tmdb.db.database.TMDatabase;
import com.tosmart.tmdb.db.entity.CommonBean;
import com.tosmart.tmdb.db.entity.Favorite;
import com.tosmart.tmdb.db.entity.FilterMovie;
import com.tosmart.tmdb.db.entity.FilterTv;
import com.tosmart.tmdb.db.entity.Movie;
import com.tosmart.tmdb.db.entity.Tv;
import com.tosmart.tmdb.network.ApiObserver;
import com.tosmart.tmdb.network.ApiRequest;
import com.tosmart.tmdb.network.response.MovieDetail;
import com.tosmart.tmdb.network.response.MovieRes;
import com.tosmart.tmdb.network.response.TvCredits;
import com.tosmart.tmdb.network.response.TvDetail;
import com.tosmart.tmdb.network.response.TvRes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.tosmart.tmdb.network.ApiRequest.INDEX_TV;

/**
 * @author ggz
 * @date 2020/10/27
 */
public class DetailViewModel extends ViewModel {
    private final String TAG = getClass().getSimpleName();

    private int mCurrentId = -1;
    private int mCurrentType = -1;
    private boolean isFavorite = false;
    private Favorite mFavorite = null;

    public MutableLiveData<String> showName = new MutableLiveData<>();
    public MutableLiveData<String> showData = new MutableLiveData<>();
    public MutableLiveData<String> showLanguage = new MutableLiveData<>();
    public MutableLiveData<String> showPoster = new MutableLiveData<>();
    public MutableLiveData<String> showAverage = new MutableLiveData<>();
    public MutableLiveData<String> showOverview = new MutableLiveData<>();
    public MutableLiveData<String> showRuntime = new MutableLiveData<>();
    public MutableLiveData<String> showCast = new MutableLiveData<>();
    public MutableLiveData<String> showWriter = new MutableLiveData<>();
    public MutableLiveData<String> showDirector = new MutableLiveData<>();
    public MutableLiveData<Boolean> showFavorite = new MutableLiveData<>();

    public LiveData<PagedList<CommonBean>> mTvLiveData;
    public LiveData<PagedList<CommonBean>> mMovieLiveData;

    private CompositeDisposable mCompositeDisposable;

    public DetailViewModel() {
        Log.e(TAG, "DetailViewModel()");
        mCompositeDisposable = new CompositeDisposable();
    }

    public void requestData(int id, int type) {
        mCurrentId = id;
        mCurrentType = type;
        mFavorite = new Favorite(0, mCurrentId, mCurrentType);

        if (mCurrentType == INDEX_TV) {
            checkTvTable();
        } else {
            checkMovieTable();
        }
        checkFavorite();
        requestDataById();
        requestCreditsData();
    }

    private void checkTvTable() {
        Consumer<Throwable> throwable = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        };
        Consumer<List<Tv>> consumer = new Consumer<List<Tv>>() {
            @Override
            public void accept(List<Tv> list) throws Exception {
                if (list.size() != 0) {
                    Tv tv = list.get(0);
                    String average = String.valueOf((int) (tv.getVoteAverage() * 10));
                    mFavorite.setName(tv.getOriginalName());
                    mFavorite.setDate(tv.getFirstAirDate());
                    mFavorite.setPoster(tv.getPosterPath());
                    mFavorite.setAverage(average);
                    mFavorite.setLanguage(tv.getOriginalLanguage());
                    mFavorite.setOverview(tv.getOverview());
                    updateUi(mFavorite);
                }
            }
        };
        TMDatabase db = RoomManager.getInstance().getTMDatabase();
        Single<List<Tv>> single = db.getTvDao().getTvById(mCurrentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mCompositeDisposable.add(single.subscribe(consumer, throwable));
    }

    private void checkMovieTable() {
        Consumer<Throwable> throwable = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        };
        Consumer<List<Movie>> consumer = new Consumer<List<Movie>>() {
            @Override
            public void accept(List<Movie> list) throws Exception {
                if (list.size() != 0) {
                    Movie movie = list.get(0);
                    String average = String.valueOf((int) (movie.getVoteAverage() * 10));
                    mFavorite.setName(movie.getOriginalTitle());
                    mFavorite.setDate(movie.getReleaseDate());
                    mFavorite.setPoster(movie.getPosterPath());
                    mFavorite.setAverage(average);
                    mFavorite.setLanguage(movie.getOriginalLanguage());
                    mFavorite.setOverview(movie.getOverview());
                    updateUi(mFavorite);
                }
            }
        };
        TMDatabase db = RoomManager.getInstance().getTMDatabase();
        Single<List<Movie>> single = db.getMovieDao().getMovieById(mCurrentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mCompositeDisposable.add(single.subscribe(consumer, throwable));
    }

    public void checkFavorite() {
        Consumer<Throwable> throwable = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        };
        Consumer<List<Favorite>> consumer = new Consumer<List<Favorite>>() {
            @Override
            public void accept(List<Favorite> list) throws Exception {
                Log.d(TAG, "accept: checkFavorite " + list.size());
                if (list.size() != 0) {
                    Favorite favorite = list.get(0);
                    isFavorite = true;
                    showFavorite.setValue(true);
                    mFavorite = favorite;
                    updateUi(favorite);
                } else {
                    isFavorite = false;
                    showFavorite.setValue(false);
                }
            }
        };
        TMDatabase db = RoomManager.getInstance().getTMDatabase();
        Single<List<Favorite>> single = db.getFavoriteDao().getFavorite(mCurrentId, mCurrentType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mCompositeDisposable.add(single.subscribe(consumer, throwable));
    }

    public void requestDataById() {
        if (mCurrentType == INDEX_TV) {
            requestTvById();
        } else {
            requestMovieById();
        }
    }

    public void requestTvById() {
        ApiObserver<TvDetail> observer = new ApiObserver<TvDetail>() {
            @Override
            public void onSuccess(TvDetail tv) {
                Log.d(TAG, "onSuccess: TvDetail: " + tv.getStatus());
                if (tv.getStatus_code() == 0) {
                    String average = String.valueOf((int) (tv.getVote_average() * 10));
                    String runtime = String.format(
                            StringUtils.getString(R.string.str_detail_runtime),
                            tv.getEpisode_run_time().get(0));
                    mFavorite.setName(tv.getOriginal_name());
                    mFavorite.setDate(tv.getFirst_air_date());
                    mFavorite.setPoster(tv.getPoster_path());
                    mFavorite.setAverage(average);
                    mFavorite.setLanguage(tv.getOriginal_language());
                    mFavorite.setOverview(tv.getOverview());
                    mFavorite.setRuntime(runtime);
                    updateUi(mFavorite);
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                Log.e(TAG, "onError: TvDetail message: " + message);
            }
        };
        mCompositeDisposable.add(observer);
        Observable<TvDetail> observable = new ApiRequest().queryTvDetail(mCurrentId);
        observable.subscribe(observer);
    }

    public void requestMovieById() {
        ApiObserver<MovieDetail> observer = new ApiObserver<MovieDetail>() {
            @Override
            public void onSuccess(MovieDetail mv) {
                Log.d(TAG, "onSuccess: MovieDetail: " + mv.getStatus_code());
                if (mv.getStatus_code() == 0) {
                    String average = String.valueOf((int) (mv.getVote_average() * 10));
                    String runtime = String.format(
                            StringUtils.getString(R.string.str_detail_runtime),
                            mv.getRuntime());
                    mFavorite.setName(mv.getOriginal_title());
                    mFavorite.setDate(mv.getRelease_date());
                    mFavorite.setPoster(mv.getPoster_path());
                    mFavorite.setAverage(average);
                    mFavorite.setLanguage(mv.getOriginal_language());
                    mFavorite.setOverview(mv.getOverview());
                    mFavorite.setRuntime(runtime);
                    updateUi(mFavorite);
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                Log.e(TAG, "onError: MovieDetail message: " + message);
            }
        };
        mCompositeDisposable.add(observer);
        Observable<MovieDetail> observable = new ApiRequest().queryMovieDetail(mCurrentId);
        observable.subscribe(observer);
    }

    public void requestCreditsData() {
        ApiObserver<TvCredits> observer = new ApiObserver<TvCredits>() {
            @Override
            public void onSuccess(TvCredits tvCredits) {
                StringBuilder directorBuilder = new StringBuilder();
                StringBuilder writerBuilder = new StringBuilder();
                StringBuilder castBuilder = new StringBuilder();
                List<TvCredits.Cast> castList = tvCredits.getCast();
                for (int i = 0; i < castList.size(); i++) {
                    castBuilder.append(castList.get(i).getName());
                    castBuilder.append("/");
                }
                List<TvCredits.Crew> crewList = tvCredits.getCrew();
                for (int i = 0; i < crewList.size(); i++) {
                    String job = crewList.get(i).getJob();
                    if (job.contains("Writer") || job.contains("Producer")) {
                        writerBuilder.append(crewList.get(i).getName());
                        writerBuilder.append("/");
                    }
                    if (job.contains("Director") || job.contains("Novel")) {
                        directorBuilder.append(crewList.get(i).getName());
                        directorBuilder.append("/");
                    }
                }
                mFavorite.setCast(castBuilder.toString());
                mFavorite.setWriter(writerBuilder.toString());
                mFavorite.setDirector(directorBuilder.toString());
                updateUi(mFavorite);
            }

            @Override
            public void onError(int errorCode, String message) {
                Log.e(TAG, "onError: TvCredits message: " + message);
            }
        };
        if (mCurrentType == INDEX_TV) {
            mCompositeDisposable.add(observer);
            Observable<TvCredits> observable = new ApiRequest().queryTvCredits(mCurrentId);
            observable.subscribe(observer);

        } else {
            mCompositeDisposable.add(observer);
            Observable<TvCredits> observable = new ApiRequest().queryMovieCredits(mCurrentId);
            observable.subscribe(observer);
        }
    }

    private void updateUi(Favorite favorite) {
        mFavorite = favorite;

        showName.setValue(favorite.getName());
        showData.setValue(favorite.getDate());
        showPoster.setValue(favorite.getPoster());
        showAverage.setValue(favorite.getAverage());
        showLanguage.setValue(favorite.getLanguage());
        showOverview.setValue(favorite.getOverview());
        showRuntime.setValue(favorite.getRuntime());
        showCast.setValue(favorite.getCast());
        showWriter.setValue(favorite.getWriter());
        showDirector.setValue(favorite.getDirector());
    }

    public void updateFavorite(boolean flag) {
        Consumer<Throwable> throwable = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        };
        TMDatabase db = RoomManager.getInstance().getTMDatabase();
        if (flag) {
            Single<Long> single = db.getFavoriteDao().insertFavorite(mFavorite)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            mCompositeDisposable.add(single.subscribe(new Consumer<Long>() {
                @Override
                public void accept(Long result) throws Exception {
                    Log.d(TAG, "accept: insertFavorite " + result);
                    isFavorite = true;
                    showFavorite.setValue(true);
                }
            }, throwable));
        } else {
            Single<Integer> single = db.getFavoriteDao().deleteFavorite(mCurrentId, mCurrentType)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            mCompositeDisposable.add(single.subscribe(new Consumer<Integer>() {
                @Override
                public void accept(Integer integer) throws Exception {
                    Log.d(TAG, "accept: deleteFavorite " + integer);
                    isFavorite = false;
                    showFavorite.setValue(false);
                }
            }, throwable));
        }
    }

    public void requestTvRecommendations(int page) {
        ApiObserver<TvRes> observer = new ApiObserver<TvRes>() {
            @Override
            public void onSuccess(TvRes tvRes) {
                Log.d(TAG, "onSuccess: tv page: " + tvRes.getPage() +
                        ", size: " + tvRes.getResults().size());

                TMDatabase db = RoomManager.getInstance().getTMDatabase();
                List<Tv> list = tvRes.getResults();
                List<FilterTv> filterList = new ArrayList<>();
                String dateStr = buildDateValue();
                for (int i = 0; i < list.size(); i++) {
                    FilterTv filterTv = new FilterTv(list.get(i).getId(), mCurrentId, 0,
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
        Observable<TvRes> observable = new ApiRequest().queryTvRecommendations(mCurrentId, page);
        observable.subscribe(observer);
    }

    public void requestMovieRecommendations(int page) {
        ApiObserver<MovieRes> observer = new ApiObserver<MovieRes>() {
            @Override
            public void onSuccess(MovieRes movieRes) {
                Log.d(TAG, "onSuccess: movie page: " + movieRes.getPage() +
                        ", size: " + movieRes.getResults().size());

                TMDatabase db = RoomManager.getInstance().getTMDatabase();
                List<Movie> list = movieRes.getResults();
                List<FilterMovie> filterList = new ArrayList<>();
                String dateStr = buildDateValue();
                for (int i = 0; i < list.size(); i++) {
                    FilterMovie filterMovie = new FilterMovie(list.get(i).getId(), mCurrentId, 0,
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
        Observable<MovieRes> observable = new ApiRequest().queryMovieRecommendations(mCurrentId, page);
        observable.subscribe(observer);
    }

    private PagedList.BoundaryCallback<CommonBean> mTvPageListCallback =
            new PagedList.BoundaryCallback<CommonBean>() {
                @Override
                public void onZeroItemsLoaded() {
                    super.onZeroItemsLoaded();
                    Log.d(TAG, "TV onZeroItemsLoaded: ");
                    requestTvRecommendations(1);
                }

                @Override
                public void onItemAtEndLoaded(@NonNull CommonBean itemAtEnd) {
                    super.onItemAtEndLoaded(itemAtEnd);
                    Log.d(TAG, "Tv onItemAtEndLoaded: " + itemAtEnd.getPage());
                    requestMovieRecommendations(itemAtEnd.getPage() + 1);
                }
            };

    private PagedList.BoundaryCallback<CommonBean> mMoviePageListCallback =
            new PagedList.BoundaryCallback<CommonBean>() {
                @Override
                public void onZeroItemsLoaded() {
                    super.onZeroItemsLoaded();
                    Log.d(TAG, "Movie onZeroItemsLoaded: ");
                    requestMovieRecommendations(1);
                }

                @Override
                public void onItemAtEndLoaded(@NonNull CommonBean itemAtEnd) {
                    super.onItemAtEndLoaded(itemAtEnd);
                    Log.d(TAG, "Movie onItemAtEndLoaded: " + itemAtEnd.getPage());
                    requestMovieRecommendations(itemAtEnd.getPage() + 1);
                }
            };

    public void initPagedList(int type) {
        TMDatabase db = RoomManager.getInstance().getTMDatabase();
        if (type == INDEX_TV) {
            DataSource.Factory<Integer, CommonBean> tvDataSource =
                    db.getFilterTvDao().getFilterTvPageList(mCurrentId, 0);
            mTvLiveData = new LivePagedListBuilder<>(
                    tvDataSource,
                    7)
                    .setBoundaryCallback(mTvPageListCallback)
                    .build();
        } else {
            DataSource.Factory<Integer, CommonBean> movieDataSource =
                    db.getFilterMovieDao().getFilterMoviePageList(mCurrentId, 0);
            mMovieLiveData = new LivePagedListBuilder<>(
                    movieDataSource,
                    7)
                    .setBoundaryCallback(mMoviePageListCallback)
                    .build();
        }
    }

    private String buildDateValue() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public int getCurrentType() {
        return mCurrentType;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    @Override
    protected void onCleared() {
        Log.e(TAG, "onCleared()");
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
        super.onCleared();
    }
}
