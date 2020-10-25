package com.tosmart.tmdb.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

/**
 * @author ggz
 * @date 2020/10/24
 * <p>
 * filter_type = 0:popularity, 1:vote_average, 2:release_date
 * filter_order = 0:desc, 1:asc
 */
@Entity(primaryKeys = {"filter_id", "filter_type", "filter_order"})
public class FilterMovie {

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

    public FilterMovie(int filterId, int filterType, int filterOrder, String date, int page, int index) {
        this.filterId = filterId;
        this.filterType = filterType;
        this.filterOrder = filterOrder;
        this.date = date;
        this.page = page;
        this.index = index;
    }

    public int getFilterId() {
        return filterId;
    }

    public void setFilterId(int filterId) {
        this.filterId = filterId;
    }

    public int getFilterType() {
        return filterType;
    }

    public void setFilterType(int filterType) {
        this.filterType = filterType;
    }

    public int getFilterOrder() {
        return filterOrder;
    }

    public void setFilterOrder(int filterOrder) {
        this.filterOrder = filterOrder;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
