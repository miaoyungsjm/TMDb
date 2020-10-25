package com.tosmart.tmdb.db.dao;

import com.tosmart.tmdb.db.entity.PopTv;
import com.tosmart.tmdb.db.entity.Popularity;

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

    @Query("SELECT * FROM Tv, Popularity " +
            "WHERE Popularity.type = 0 AND Popularity.pop_id = Tv.id " +
            "AND Popularity.order_direction == :od ")
    DataSource.Factory<Integer, PopTv> getPopularityTv(int od);
}
