package com.tosmart.tmdb.db.dao;

import com.tosmart.tmdb.db.entity.Movie;
import com.tosmart.tmdb.db.entity.Tv;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

/**
 * @author ggz
 * @date 2020/10/21
 */
@Dao
public interface MovieDao extends BaseDao<Movie> {

    @Query("SELECT * FROM Movie")
    List<Movie> getAllMovie();

    @Query("SELECT * FROM Movie WHERE id == :id")
    Movie getMovie(int id);

//    @Query("SELECT * FROM Movie, Favorite " +
//            "WHERE Favorite.type = 0 AND Favorite.id = Movie.id ")
//    List<Tv> getFavoriteMovie();
}
