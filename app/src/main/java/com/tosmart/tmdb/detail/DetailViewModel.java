package com.tosmart.tmdb.detail;

import android.util.Log;

import com.tosmart.tmdb.db.RoomManager;
import com.tosmart.tmdb.db.database.TMDatabase;
import com.tosmart.tmdb.db.entity.Favorite;
import com.tosmart.tmdb.db.entity.FilterMovie;
import com.tosmart.tmdb.db.entity.FilterTv;
import com.tosmart.tmdb.db.entity.Movie;
import com.tosmart.tmdb.db.entity.MoviePageList;
import com.tosmart.tmdb.db.entity.Tv;
import com.tosmart.tmdb.db.entity.TvPageList;
import com.tosmart.tmdb.network.ApiObserver;
import com.tosmart.tmdb.network.ApiRequest;
import com.tosmart.tmdb.network.response.MovieRes;
import com.tosmart.tmdb.network.response.TvCredits;
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
    private String mCurrentName = null;
    private String mCurrentDate = null;
    private String mCurrentPoster = null;
    private String mCurrentAverage = null;
    private boolean isFavorite = false;

    public MutableLiveData<String> showName = new MutableLiveData<>();
    public MutableLiveData<String> showData = new MutableLiveData<>();
    public MutableLiveData<String> showLanguage = new MutableLiveData<>();
    public MutableLiveData<String> showPoster = new MutableLiveData<>();
    public MutableLiveData<String> showAverage = new MutableLiveData<>();
    public MutableLiveData<String> showOverview = new MutableLiveData<>();
    public MutableLiveData<String> showCast = new MutableLiveData<>();
    public MutableLiveData<String> showWriter = new MutableLiveData<>();
    public MutableLiveData<String> showDirector = new MutableLiveData<>();
    public MutableLiveData<Boolean> showFavorite = new MutableLiveData<>();

    public LiveData<PagedList<TvPageList>> mTvLiveData;
    public LiveData<PagedList<MoviePageList>> mMovieLiveData;

    private CompositeDisposable mCompositeDisposable;

    public DetailViewModel() {
        Log.e(TAG, "DetailViewModel()");
        mCompositeDisposable = new CompositeDisposable();
    }

    public void requestDatabase(int id, int type) {
        mCurrentId = id;
        mCurrentType = type;
        TMDatabase db = RoomManager.getInstance().getTMDatabase();
        if (mCurrentType == INDEX_TV) {
            Consumer<Tv> consumer = new Consumer<Tv>() {
                @Override
                public void accept(Tv tv) throws Exception {
                    mCurrentName = tv.getOriginalName();
                    mCurrentDate = tv.getFirstAirDate();
                    mCurrentPoster = tv.getPosterPath();
                    String average = String.valueOf((int) (tv.getVoteAverage() * 10));
                    mCurrentAverage = average;

                    showName.setValue(mCurrentName);
                    showData.setValue(mCurrentDate);
                    showLanguage.setValue(tv.getOriginalLanguage());
                    showPoster.setValue(mCurrentPoster);
                    showOverview.setValue(tv.getOverview());
                    showAverage.setValue(mCurrentAverage);
                }
            };
            Single<Tv> single = db.getTvDao().getTvById(mCurrentId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            mCompositeDisposable.add(single.subscribe(consumer, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    throwable.printStackTrace();
                }
            }));
        } else {
            Consumer<Movie> consumer = new Consumer<Movie>() {
                @Override
                public void accept(Movie movie) throws Exception {
                    mCurrentName = movie.getOriginalTitle();
                    mCurrentDate = movie.getReleaseDate();
                    mCurrentPoster = movie.getPosterPath();
                    String average = String.valueOf((int) (movie.getVoteAverage() * 10));
                    mCurrentAverage = average;

                    showName.setValue(mCurrentName);
                    showData.setValue(mCurrentDate);
                    showLanguage.setValue(movie.getOriginalLanguage());
                    showPoster.setValue(mCurrentPoster);
                    showOverview.setValue(movie.getOverview());
                    showAverage.setValue(mCurrentAverage);
                }
            };
            Single<Movie> single = db.getMovieDao().getMovieById(mCurrentId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            mCompositeDisposable.add(single.subscribe(consumer, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    throwable.printStackTrace();
                }
            }));
        }

        checkFavorite();

        requestCreditsData();
    }

    public void checkFavorite() {
        Consumer<Throwable> throwable = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        };
        TMDatabase db = RoomManager.getInstance().getTMDatabase();
        Single<Favorite> single = db.getFavoriteDao().getFavorite(mCurrentId, mCurrentType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mCompositeDisposable.add(single.subscribe(new Consumer<Favorite>() {
            @Override
            public void accept(Favorite favorite) throws Exception {
                Log.e(TAG, "accept: checkFavorite " + favorite);
                if (favorite != null) {
                    isFavorite = true;
                    showFavorite.setValue(true);
                } else {
                    isFavorite = false;
                    showFavorite.setValue(false);
                }
            }
        }, throwable));
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
            Favorite favorite = new Favorite(0, mCurrentId, mCurrentType,
                    mCurrentName, mCurrentDate, mCurrentPoster, mCurrentAverage);
            Single<Long> single = db.getFavoriteDao().insertFavorite(favorite)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            mCompositeDisposable.add(single.subscribe(new Consumer<Long>() {
                @Override
                public void accept(Long result) throws Exception {
                    Log.e(TAG, "accept: insertFavorite " + result);
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
                    Log.e(TAG, "accept: deleteFavorite " + integer);
                    isFavorite = false;
                    showFavorite.setValue(false);
                }
            }, throwable));
        }
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
                showCast.postValue(castBuilder.toString());
                List<TvCredits.Crew> crewList = tvCredits.getCrew();
                for (int i = 0; i < crewList.size(); i++) {
                    String job = crewList.get(i).getJob();
                    if (job.contains("Writer") || job.contains("Producer")) {
                        writerBuilder.append(crewList.get(i).getName());
                        castBuilder.append("/");
                    }
                    if (job.contains("Director") || job.contains("Characters")) {
                        directorBuilder.append(crewList.get(i).getName());
                        castBuilder.append("/");
                    }
                }
                showWriter.postValue(writerBuilder.toString());
                showDirector.postValue(directorBuilder.toString());
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

    public void requestTvRecommendations(int page) {
        ApiObserver<TvRes> observer = new ApiObserver<TvRes>() {
            @Override
            public void onSuccess(TvRes tvRes) {
                TMDatabase db = RoomManager.getInstance().getTMDatabase();
                List<Tv> list = tvRes.getResults();
                List<FilterTv> filterList = new ArrayList<>();
                String dateStr = buildDateValue();
                for (int i = 0; i < list.size(); i++) {
                    Log.d(TAG, "onSuccess: tv id: " + list.get(i).getId());
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
                TMDatabase db = RoomManager.getInstance().getTMDatabase();
                List<Movie> list = movieRes.getResults();
                List<FilterMovie> filterList = new ArrayList<>();
                String dateStr = buildDateValue();
                for (int i = 0; i < list.size(); i++) {
                    Log.d(TAG, "onSuccess: movie id: " + list.get(i).getId());
                    FilterMovie filterMovie = new FilterMovie(list.get(i).getId(), mCurrentId, 0,
                            dateStr, movieRes.getPage(), i);
                    filterList.add(filterMovie);
                }
                db.getFilterMovieDao().insertList(filterList);
                db.getMovieDao().insertList(list);
            }

            @Override
            public void onError(int errorCode, String message) {

            }
        };
        mCompositeDisposable.add(observer);
        Observable<MovieRes> observable = new ApiRequest().queryMovieRecommendations(mCurrentId, page);
        observable.subscribe(observer);
    }

    private PagedList.BoundaryCallback<TvPageList> mTvPageListCallback =
            new PagedList.BoundaryCallback<TvPageList>() {
                @Override
                public void onZeroItemsLoaded() {
                    super.onZeroItemsLoaded();
                    Log.e(TAG, "TV onZeroItemsLoaded: ");
                    requestTvRecommendations(1);
                }

                @Override
                public void onItemAtEndLoaded(@NonNull TvPageList itemAtEnd) {
                    super.onItemAtEndLoaded(itemAtEnd);
                    Log.e(TAG, "Tv onItemAtEndLoaded: " + itemAtEnd.getPage());
                    requestMovieRecommendations(itemAtEnd.getPage() + 1);
                }
            };

    private PagedList.BoundaryCallback<MoviePageList> mMoviePageListCallback =
            new PagedList.BoundaryCallback<MoviePageList>() {
                @Override
                public void onZeroItemsLoaded() {
                    super.onZeroItemsLoaded();
                    Log.e(TAG, "Movie onZeroItemsLoaded: ");
                    requestMovieRecommendations(1);
                }

                @Override
                public void onItemAtEndLoaded(@NonNull MoviePageList itemAtEnd) {
                    super.onItemAtEndLoaded(itemAtEnd);
                    Log.e(TAG, "Movie onItemAtEndLoaded: " + itemAtEnd.getPage());
                    requestMovieRecommendations(itemAtEnd.getPage() + 1);
                }
            };

    public void initPagedList(int type) {
        TMDatabase db = RoomManager.getInstance().getTMDatabase();
        if (type == INDEX_TV) {
            DataSource.Factory<Integer, TvPageList> tvDataSource =
                    db.getFilterTvDao().getFilterTvPageList(mCurrentId, 0);
            mTvLiveData = new LivePagedListBuilder<>(
                    tvDataSource,
                    7)
                    .setBoundaryCallback(mTvPageListCallback)
                    .build();
        } else {
            DataSource.Factory<Integer, MoviePageList> movieDataSource =
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
