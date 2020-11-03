package com.tosmart.tmdb.db.dao;

import com.tosmart.tmdb.db.entity.CommonBean;
import com.tosmart.tmdb.db.entity.Favorite;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Single;

/**
 * @author ggz
 * @date 2020/10/21
 */
@Dao
public interface FavoriteDao extends BaseDao<Favorite> {

    @Insert
    Single<Long> insertFavorite(Favorite favorite);

    @Query("DELETE FROM Favorite " +
            "WHERE Favorite.id == :id AND Favorite.type == :type ")
    Single<Integer> deleteFavorite(int id, int type);

    @Query("SELECT * FROM Favorite " +
            "WHERE Favorite.id == :id AND Favorite.type == :type ")
    Single<List<Favorite>> getFavorite(int id, int type);

    @Query("SELECT id, type, name AS original_name, date AS first_air_date, " +
            "poster AS poster_path, average AS vote_average, account AS page " +
            "FROM Favorite ")
    Single<List<CommonBean>> getAllFavoriteToCommon();
}
