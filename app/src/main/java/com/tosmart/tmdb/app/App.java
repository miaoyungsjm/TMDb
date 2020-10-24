package com.tosmart.tmdb.app;

import android.app.Application;

import com.tosmart.tmdb.db.RoomManager;
import com.tosmart.tmdb.network.ApiManager;

/**
 * @author ggz
 * @date 2020/10/22
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ApiManager.getInstance().init();
        RoomManager.getInstance().init(this);

    }
}
