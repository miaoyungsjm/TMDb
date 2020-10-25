package com.tosmart.tmdb.db.dao;

import com.tosmart.tmdb.db.entity.FilterTv;
import com.tosmart.tmdb.db.entity.TvPageList;

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

    @Query("SELECT * FROM Tv, FilterTv " +
            "WHERE FilterTv.filter_id = Tv.id " +
            "AND FilterTv.filter_type == :ft " +
            "AND FilterTv.filter_order == :fo ")
    DataSource.Factory<Integer, TvPageList> getFilterTvPageList(int ft, int fo);
}
