package com.tosmart.tmdb.network;

import android.accounts.NetworkErrorException;
import android.text.TextUtils;

import java.net.SocketTimeoutException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * @author ggz
 * @date 2020/10/19
 */
public abstract class ApiObserver<T> extends DisposableObserver<T> {

    /**
     * 外部重写，接受数据
     */
    public abstract void onSuccess(T t);

    /**
     * 外部想要处理异常
     */
    public abstract void onError(int errorCode, String message);

    @Override
    public void onNext(T t) {
        if (t == null) {
            onError(HttpCode.ERROR_EMPTY_OBJ, getErrorMessage(HttpCode.ERROR_EMPTY_OBJ));
        } else {
            onSuccess(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        int errorCode;
        if (e instanceof NullPointerException) {
            errorCode = HttpCode.ERROR_EMPTY_OBJ;
        } else if (e instanceof SocketTimeoutException) {
            errorCode = HttpCode.ERROR_TIMEOUT;
        } else if (e instanceof NetworkErrorException) {
            errorCode = HttpCode.ERROR_NETWORK;
        } else if (e instanceof HttpException) {
            errorCode = HttpCode.ERROR_SERVER_EXCEPTION;
        } else {
            errorCode = HttpCode.ERROR_UNKNOWN;
        }
        String errMsg = getErrorMessage(errorCode);
        onError(errorCode, errMsg);
    }

    @Override
    public void onComplete() {
    }

    private String getErrorMessage(int errorCode) {
        String message = HttpCode.ERRORS.get(errorCode);
        if (TextUtils.isEmpty(message)) {
            message = HttpCode.ERRORS.get(HttpCode.ERROR_UNKNOWN);
        }
        return message;
    }
}
