package com.tosmart.tmdb.detail;

import android.annotation.SuppressLint;
import android.util.Log;

import com.tosmart.tmdb.db.RoomManager;
import com.tosmart.tmdb.db.database.TMDatabase;
import com.tosmart.tmdb.db.entity.Movie;
import com.tosmart.tmdb.db.entity.Tv;
import com.tosmart.tmdb.network.ApiObserver;
import com.tosmart.tmdb.network.ApiRequest;
import com.tosmart.tmdb.network.response.TvCredits;
import com.tosmart.tmdb.network.response.TvRes;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.tosmart.tmdb.network.ApiRequest.INDEX_MOVIE;
import static com.tosmart.tmdb.network.ApiRequest.INDEX_TV;

/**
 * @author ggz
 * @date 2020/10/27
 */
public class DetailViewModel extends ViewModel {
    private final String TAG = getClass().getSimpleName();

    private int mId = -1;
    private int mType = -1;
    private Tv mTvEntity;
    private Movie mMovieEntity;

    public MutableLiveData<String> showName = new MutableLiveData<>();
    public MutableLiveData<String> showData = new MutableLiveData<>();
    public MutableLiveData<String> showLanguage = new MutableLiveData<>();
    public MutableLiveData<String> showPoster = new MutableLiveData<>();
    public MutableLiveData<String> showAverage = new MutableLiveData<>();
    public MutableLiveData<String> showOverview = new MutableLiveData<>();
    public MutableLiveData<String> showCast = new MutableLiveData<>();
    public MutableLiveData<String> showWriter = new MutableLiveData<>();
    public MutableLiveData<String> showDirector = new MutableLiveData<>();

    private CompositeDisposable mCompositeDisposable;

    public DetailViewModel() {
        mCompositeDisposable = new CompositeDisposable();
    }

    public void requestDatabase(int id, int type) {
        mId = id;
        mType = type;
        TMDatabase db = RoomManager.getInstance().getTMDatabase();
        if (mType == INDEX_TV) {
            Consumer<Tv> consumer = new Consumer<Tv>() {
                @Override
                public void accept(Tv tv) throws Exception {
                    mTvEntity = tv;
                    showName.setValue(tv.getOriginalName());
                    showData.setValue(tv.getFirstAirDate());
                    showLanguage.setValue(tv.getOriginalLanguage());
                    showPoster.setValue(tv.getPosterPath());
                    showOverview.setValue(tv.getOverview());
                    String average = String.valueOf((int) (tv.getVoteAverage() * 10));
                    showAverage.setValue(average);
                }
            };
            Flowable<Tv> flowable = db.getTvDao().getTvById(mId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            mCompositeDisposable.add(flowable.subscribe(consumer));

            requestNetWorkData(mId, INDEX_TV);
        } else {
            Consumer<Movie> consumer = new Consumer<Movie>() {
                @Override
                public void accept(Movie movie) throws Exception {
                    mMovieEntity = movie;
                    showName.setValue(movie.getOriginalTitle());
                    showData.setValue(movie.getReleaseDate());
                    showLanguage.setValue(movie.getOriginalLanguage());
                    showPoster.setValue(movie.getPosterPath());
                    showOverview.setValue(movie.getOverview());
                    String average = String.valueOf((int) (movie.getVoteAverage() * 10));
                    showAverage.setValue(average);
                }
            };
            Flowable<Movie> flowable = db.getMovieDao().getMovieById(mId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            mCompositeDisposable.add(flowable.subscribe(consumer));

            requestNetWorkData(mId, INDEX_MOVIE);
        }
    }

    public void requestNetWorkData(int id, int type) {
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
        if (type == INDEX_TV) {
            mCompositeDisposable.add(observer);
            Observable<TvCredits> observable = new ApiRequest().queryTvCredits(id);
            observable.subscribe(observer);
        } else {
            mCompositeDisposable.add(observer);
            Observable<TvCredits> observable = new ApiRequest().queryMovieCredits(id);
            observable.subscribe(observer);
        }
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
