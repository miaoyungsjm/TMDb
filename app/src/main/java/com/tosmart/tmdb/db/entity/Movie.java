package com.tosmart.tmdb.db.entity;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author ggz
 * @date 2020/10/21
 *
 * popularity : 3130.643
 * vote_count : 143
 * video : false
 * poster_path : /7D430eqZj8y3oVkLFfsWXGRcpEG.jpg
 * id : 528085
 * adult : false
 * backdrop_path : /5UkzNSOK561c2QRy2Zr4AkADzLT.jpg
 * original_language : en
 * original_title : 2067
 * genre_ids : [18,878,53]
 * title : 2067
 * vote_average : 5.8
 * overview : A lowly utility worker is called to the future by a mysterious radio signal, he must leave his dying wife to embark on a journey that will force him to face his deepest fears in an attempt to change the fabric of reality and save humankind from its greatest environmental crisis yet.
 * release_date : 2020-10-01
 */
@Entity
public class Movie {
    @PrimaryKey(autoGenerate = false)
    private int id;
    @SerializedName("original_title")
    @ColumnInfo(name = "original_title")
    private String originalTitle;
    @SerializedName("title")
    @ColumnInfo(name = "title")
    private String title;
    @SerializedName("original_language")
    @ColumnInfo(name = "original_language")
    private String originalLanguage;
    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    private String releaseDate;
    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    private String posterPath;
    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    private String backdropPath;
    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    private String overview;
    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    private double voteAverage;
    @SerializedName("popularity")
    @ColumnInfo(name = "popularity")
    private double popularity;
    @SerializedName("vote_count")
    @ColumnInfo(name = "vote_count")
    private int voteCount;

    public Movie(int id, String originalTitle, String title, String originalLanguage, String releaseDate, String posterPath, String backdropPath, String overview, double voteAverage, double popularity, int voteCount) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.title = title;
        this.originalLanguage = originalLanguage;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
        this.voteCount = voteCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
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
}
