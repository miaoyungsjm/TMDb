package com.tosmart.tmdb.db;

import android.content.Context;

import com.tosmart.tmdb.db.database.TMDatabase;
import com.tosmart.tmdb.network.ApiManager;

import androidx.room.Room;

/**
 * @author ggz
 * @date 2020/10/21
 */
public class RoomManager {
    private static RoomManager sInstance;
    private TMDatabase mTMDatabase;

    private RoomManager() {
    }

    public static RoomManager getInstance() {
        if (null == sInstance) {
            synchronized (ApiManager.class) {
                if (null == sInstance) {
                    sInstance = new RoomManager();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        mTMDatabase = Room.databaseBuilder(context,
                TMDatabase.class, "tmdb.db")
//                .allowMainThreadQueries()
                .build();
    }

    public TMDatabase getTMDatabase() {
        if (mTMDatabase == null) {
            throw new IllegalStateException("TMDatabase instance hasn't init!");
        }
        return mTMDatabase;
    }
}
