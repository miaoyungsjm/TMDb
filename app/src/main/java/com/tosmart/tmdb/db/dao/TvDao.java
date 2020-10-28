package com.tosmart.tmdb.db.dao;

import com.tosmart.tmdb.db.entity.Tv;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import io.reactivex.Single;

/**
 * @author ggz
 * @date 2020/10/21
 */
@Dao
public interface TvDao extends BaseDao<Tv> {

    @Query("SELECT * FROM Tv WHERE id == :id")
    Single<Tv> getTvById(int id);
}
