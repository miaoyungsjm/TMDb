package com.tosmart.tmdb.db.database;

import com.tosmart.tmdb.db.dao.FavoriteDao;
import com.tosmart.tmdb.db.dao.FilterMovieDao;
import com.tosmart.tmdb.db.dao.FilterTvDao;
import com.tosmart.tmdb.db.dao.MovieDao;
import com.tosmart.tmdb.db.dao.TvDao;
import com.tosmart.tmdb.db.entity.Favorite;
import com.tosmart.tmdb.db.entity.FilterMovie;
import com.tosmart.tmdb.db.entity.FilterTv;
import com.tosmart.tmdb.db.entity.Movie;
import com.tosmart.tmdb.db.entity.Tv;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * @author ggz
 * @date 2020/10/21
 */
@Database(entities = {Movie.class, Tv.class, Favorite.class, FilterTv.class, FilterMovie.class},
        version = 1, exportSchema = false)
public abstract class TMDatabase extends RoomDatabase {

    public abstract MovieDao getMovieDao();

    public abstract TvDao getTvDao();

    public abstract FavoriteDao getFavoriteDao();

    public abstract FilterTvDao getFilterTvDao();

    public abstract FilterMovieDao getFilterMovieDao();
}
