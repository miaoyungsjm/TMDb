package com.tosmart.tmdb.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

/**
 * @author ggz
 * @date 2020/10/25
 */
@Entity
public class PopTv {

    private int id;
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
    @ColumnInfo(name = "pop_id")
    private int popId;
    @ColumnInfo(name = "type")
    private int type;
    @ColumnInfo(name = "date")
    private String data;
    @ColumnInfo(name = "order_direction")
    private int orderDirection;
    @ColumnInfo(name = "page")
    private int page;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
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

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getPopId() {
        return popId;
    }

    public void setPopId(int popId) {
        this.popId = popId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(int orderDirection) {
        this.orderDirection = orderDirection;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
