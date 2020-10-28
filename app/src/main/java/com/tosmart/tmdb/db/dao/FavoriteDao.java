package com.tosmart.tmdb.db.dao;

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
    Single<Favorite> getFavorite(int id, int type);

    @Query("SELECT * FROM Favorite ")
    Single<List<Favorite>> getAllFavorite();
}
