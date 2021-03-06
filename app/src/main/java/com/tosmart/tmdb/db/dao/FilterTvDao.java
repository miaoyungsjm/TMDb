package com.tosmart.tmdb.db.dao;

import com.tosmart.tmdb.db.entity.CommonBean;
import com.tosmart.tmdb.db.entity.FilterTv;

import java.util.List;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Query;

/**
 * @author ggz
 * @date 2020/10/21
 */
@Dao
public interface FilterTvDao extends BaseDao<FilterTv> {

    @Query("SELECT * FROM FilterTv")
    List<FilterTv> getAllFilterTv();

    //SELECT * FROM Tv, FilterTv WHERE FilterTv.filter_id = Tv.id AND FilterTv.filter_type == 0 AND FilterTv.filter_order == 0
    //SELECT * FROM Tv INNER JOIN FilterTv ON FilterTv.filter_id = Tv.id AND FilterTv.filter_type == 0 AND FilterTv.filter_order == 0
    @Query("SELECT id, type, original_name, first_air_date, poster_path, vote_average, page " +
            "FROM Tv, FilterTv " +
            "WHERE FilterTv.filter_id = Tv.id " +
            "AND FilterTv.filter_type == :ft " +
            "AND FilterTv.filter_order == :fo ")
    DataSource.Factory<Integer, CommonBean> getFilterTvPageList(int ft, int fo);
}
