package com.tosmart.tmdb.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

/**
 * @author ggz
 * @date 2020/10/24
 */
@Entity(primaryKeys = {"pop_id", "type"})
public class Popularity {

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

    public Popularity(int popId, int type, String data, int orderDirection, int page) {
        this.popId = popId;
        this.type = type;
        this.data = data;
        this.orderDirection = orderDirection;
        this.page = page;
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
