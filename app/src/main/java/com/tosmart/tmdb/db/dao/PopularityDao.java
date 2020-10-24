package com.tosmart.tmdb.db.dao;

import com.tosmart.tmdb.db.entity.Popularity;
import com.tosmart.tmdb.db.entity.Tv;

import java.util.List;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Query;

/**
 * @author ggz
 * @date 2020/10/21
 */
@Dao
public interface PopularityDao extends BaseDao<Popularity> {

    @Query("SELECT Popularity.date FROM Popularity")
    String getPopularityDate();

    @Query("SELECT * FROM Popularity")
    List<Popularity> getAllPopularity();

    @Query("SELECT * FROM Popularity, Tv " +
            "WHERE Popularity.type = 0 AND Popularity.id = Tv.id " +
            "AND Popularity.order_direction == :od ")
    DataSource.Factory<Integer, Tv> getPopularityTv(int od);
}
