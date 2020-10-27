package com.tosmart.tmdb.network;

import com.tosmart.tmdb.network.response.MovieRes;
import com.tosmart.tmdb.network.response.TvCredits;
import com.tosmart.tmdb.network.response.TvRes;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author ggz
 * @date 2020/10/19
 */
public interface ApiService {
    String BASE_URL = "https://api.themoviedb.org/3/";
    String PIC_URL = "https://image.tmdb.org/t/p/w500/";

    @GET("discover/movie")
    Observable<MovieRes> discoverMovie(
            @Query("api_key") String key,
            @Query("sort_by") String query,
            @Query("page") int page);

    @GET("search/movie")
    Observable<MovieRes> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page);

    @GET("movie/{id}/credits")
    Observable<TvCredits> queryMovieCredits(
            @Path("id") int id,
            @Query("api_key") String key);

    @GET("discover/tv")
    Observable<TvRes> discoverTv(
            @Query("api_key") String key,
            @Query("sort_by") String query,
            @Query("page") int page);

    @GET("tv/{id}/credits")
    Observable<TvCredits> queryTvCredits(
            @Path("id") int id,
            @Query("api_key") String key);

    @GET("search/tv")
    Observable<TvRes> searchTv(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page);

}
