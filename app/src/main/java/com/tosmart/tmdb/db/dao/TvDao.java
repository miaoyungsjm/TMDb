package com.tosmart.tmdb.db.dao;

import com.tosmart.tmdb.db.entity.Tv;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import io.reactivex.Flowable;

/**
 * @author ggz
 * @date 2020/10/21
 */
@Dao
public interface TvDao extends BaseDao<Tv> {

    @Query("SELECT * FROM Tv")
    List<Tv> getAllTv();

    @Query("SELECT * FROM Tv WHERE id == :id")
    Flowable<Tv> getTvById(int id);
}
