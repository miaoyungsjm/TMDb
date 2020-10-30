package com.tosmart.tmdb.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

/**
 * @author ggz
 * @date 2020/10/31
 */
@Entity
public class CommonPageList {
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "type")
    private int type;
    @ColumnInfo(name = "original_name")
    private String originalName;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "original_language")
    private String originalLanguage;
    @ColumnInfo(name = "first_air_date")
    private String firstAirDate;
    @ColumnInfo(name = "poster_path")
    private String posterPath;
    @ColumnInfo(name = "backdrop_path")
    private String backdropPath;
    @ColumnInfo(name = "overview")
    private String overview;
    @ColumnInfo(name = "vote_average")
    private double voteAverage;
    @ColumnInfo(name = "popularity")
    private double popularity;
    @ColumnInfo(name = "vote_count")
    private int voteCount;

    @ColumnInfo(name = "filter_id")
    private int filterId;
    @ColumnInfo(name = "filter_type")
    private int filterType;
    @ColumnInfo(name = "filter_order")
    private int filterOrder;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "page")
    private int page;
    @ColumnInfo(name = "index")
    private int index;
}
