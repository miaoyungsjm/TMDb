package com.tosmart.tmdb.network;

import android.util.SparseArray;

/**
 * @author ggz
 * @date 2020/10/19
 */
public class HttpCode {
    public static final int ERROR_UNKNOWN = -1;
    public static final int SUCCESS = 0;
    public static final int ERROR_NETWORK = 1000;
    public static final int ERROR_TIMEOUT = 1001;
    public static final int ERROR_SERVER_EXCEPTION = 1002;
    public static final int ERROR_EMPTY_OBJ = 1011;
    public static final SparseArray<String> ERRORS = new SparseArray<>();

    static {
        ERRORS.append(ERROR_UNKNOWN, "未知错误");
        ERRORS.append(SUCCESS, "请求成功");
        ERRORS.append(ERROR_NETWORK, "网络错误");
        ERRORS.append(ERROR_TIMEOUT, "连接超时");
        ERRORS.append(ERROR_SERVER_EXCEPTION, "服务器异常");
        ERRORS.append(ERROR_EMPTY_OBJ, "返回对象为空!");
    }
}
