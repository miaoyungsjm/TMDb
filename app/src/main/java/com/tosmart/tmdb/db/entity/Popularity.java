package com.tosmart.tmdb.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author ggz
 * @date 2020/10/24
 */
@Entity
public class Popularity {
    @PrimaryKey(autoGenerate = true)
    private int serialNumber;
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "type")
    private int type;
    @ColumnInfo(name = "date")
    private String data;
    @ColumnInfo(name = "order_direction")
    private int orderDirection;

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
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
}
