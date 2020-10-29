package com.tosmart.tmdb.db.dao;

import com.tosmart.tmdb.db.entity.Movie;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import io.reactivex.Single;

/**
 * @author ggz
 * @date 2020/10/21
 */
@Dao
public interface MovieDao extends BaseDao<Movie> {

    @Query("SELECT * FROM Movie WHERE id == :id")
    Single<List<Movie>> getMovieById(int id);
}
