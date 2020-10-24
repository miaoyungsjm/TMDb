package com.tosmart.tmdb.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.tosmart.tmdb.network.ApiService.BASE_URL;

/**
 * @author ggz
 * @date 2020/10/19
 */
public class ApiManager {

    private static ApiManager sInstance;
    private ApiService mApiService;

    private ApiManager() {
    }

    public static ApiManager getInstance() {
        if (null == sInstance) {
            synchronized (ApiManager.class) {
                if (null == sInstance) {
                    sInstance = new ApiManager();
                }
            }
        }
        return sInstance;
    }

    public void init() {
        if (mApiService == null) {
            //初始化一个OkHttpClient
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(10000, TimeUnit.MILLISECONDS)
                    .readTimeout(10000, TimeUnit.MILLISECONDS)
                    .writeTimeout(10000, TimeUnit.MILLISECONDS);
            OkHttpClient okHttpClient = builder.build();

            //使用该OkHttpClient创建一个Retrofit的ApiService
            mApiService = new Retrofit.Builder()
                    //添加Gson数据格式转换器支持
                    .addConverterFactory(GsonConverterFactory.create())
                    //添加RxJava语言支持
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    //指定网络请求client
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .build()
                    .create(ApiService.class);
        }
    }

    public ApiService getApiService() {
        if (mApiService == null) {
            throw new IllegalStateException("Retrofit instance hasn't init!");
        }
        return mApiService;
    }
}
