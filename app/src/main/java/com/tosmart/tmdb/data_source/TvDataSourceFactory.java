package com.tosmart.tmdb.data_source;

import com.tosmart.tmdb.db.entity.Tv;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

/**
 * @author ggz
 * @date 2020/10/23
 */
public class TvDataSourceFactory extends DataSource.Factory<Integer, Tv> {

    @NonNull
    @Override
    public DataSource<Integer, Tv> create() {
        return new TvDataSource();
    }
}
