package com.tosmart.tmdb.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

/**
 * @author ggz
 * @date 2020/10/31
 */
@Entity(primaryKeys = {"id", "type"})
public class CommonBean {

    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "type")
    private int type;
    @ColumnInfo(name = "original_name")
    private String originalName;
    @ColumnInfo(name = "first_air_date")
    private String firstAirDate;
    @ColumnInfo(name = "poster_path")
    private String posterPath;
    @ColumnInfo(name = "vote_average")
    private double voteAverage;
    @ColumnInfo(name = "page")
    private int page;



    public CommonBean(int id, int type, String originalName, String firstAirDate, String posterPath, double voteAverage, int page) {
        this.id = id;
        this.type = type;
        this.originalName = originalName;
        this.firstAirDate = firstAirDate;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.page = page;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
