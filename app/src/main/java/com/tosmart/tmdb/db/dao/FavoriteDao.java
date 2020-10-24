package com.tosmart.tmdb.db.dao;

import com.tosmart.tmdb.db.entity.Favorite;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

/**
 * @author ggz
 * @date 2020/10/21
 */
@Dao
public interface FavoriteDao extends BaseDao<Favorite> {

    @Query("SELECT * FROM Favorite")
    List<Favorite> getAllFavorite();

//    @Query("SELECT * FROM Favorite, Tv " +
//            "WHERE Favorite.type = 0 AND Favorite.id = Tv.id ")
//    List<Tv> getFavoriteTv();

//    @Query("SELECT * FROM Favorite, Movie " +
//            "WHERE Favorite.type = 1 AND Favorite.id = Movie.id ")
//    List<Movie> getFavoriteMovie();
}
