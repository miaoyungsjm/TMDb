package com.tosmart.tmdb.db.dao;

import java.util.List;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

/**
 * @author ggz
 * @date 2020/10/21
 */
public interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T... obj);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<T> obj);

    @Delete
    void delete(T... obj);

    @Update
    void update(T... obj);

}
