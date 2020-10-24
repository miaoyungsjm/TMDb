package com.tosmart.tmdb.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author ggz
 * @date 2020/10/21
 */
@Entity
public class Favorite {
    @PrimaryKey(autoGenerate = true)
    private int serialNumber;
    @ColumnInfo(name = "user_account")
    private int userAccount;
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "type")
    private int type;

    public Favorite(int userAccount, int id, int type) {
        this.userAccount = userAccount;
        this.id = id;
        this.type = type;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(int userAccount) {
        this.userAccount = userAccount;
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
}
