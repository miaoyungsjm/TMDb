package com.tosmart.tmdb.data_source;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

/**
 * @author ggz
 * @date 2020/10/23
 */
public class MyDataSourceFactory extends DataSource.Factory<Integer, String> {

    @NonNull
    @Override
    public DataSource<Integer, String> create() {
        return new PagingDataSource();
    }
}
