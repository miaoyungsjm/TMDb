package com.tosmart.tmdb.db.dao;

import com.tosmart.tmdb.db.entity.CommonBean;
import com.tosmart.tmdb.db.entity.FilterMovie;

import java.util.List;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Query;

/**
 * @author ggz
 * @date 2020/10/21
 */
@Dao
public interface FilterMovieDao extends BaseDao<FilterMovie> {

    @Query("SELECT * FROM FilterMovie")
    List<FilterMovie> getAllFilterMoive();

    @Query("SELECT id, type, original_title AS original_name, release_date AS first_air_date, " +
            "poster_path, vote_average, page " +
            "FROM Movie, FilterMovie " +
            "WHERE FilterMovie.filter_id = Movie.id " +
            "AND FilterMovie.filter_type == :ft " +
            "AND FilterMovie.filter_order == :fo ")
    DataSource.Factory<Integer, CommonBean> getFilterMoviePageList(int ft, int fo);
}
