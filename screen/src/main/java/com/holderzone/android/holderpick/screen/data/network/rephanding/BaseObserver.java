package com.holderzone.android.holderpick.screen.data.network.rephanding;

import android.net.ParseException;
import android.os.NetworkOnMainThreadException;
import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.holderzone.android.holderpick.screen.data.domain.dto.resp.base.Resp;
import com.holderzone.android.holderpick.screen.exception.network.ApiException;
import com.holderzone.android.holderpick.screen.ui.base.view.IView;
import com.holderzone.android.holderpick.screen.util.helper.ApiNoteHelper;

import org.json.JSONException;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;


/**
 * 网络数据返回处理
 *
 * @author www
 * @date 2018/11/12 18:41.
 */

public abstract class BaseObserver<T> implements Observer<T> {

    /**
     * view层的引用
     */
    private IView mIView;


    public BaseObserver(IView iView) {
        mIView = iView;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        if (t instanceof Resp) {
            Resp resp = (Resp) t;
            if (ApiNoteHelper.checkBusiness(resp)) {
                next(t);
            } else {
                onError(new ApiException(resp));
            }
        } else {
//            next(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof ApiException) {
            mIView.showMessage(e.getMessage());
        } else if (e instanceof HttpException) {
            switch (((HttpException) e).code()) {
                case 400:
                    mIView.showMessage("参数错误");
                    break;
                case 500:
                    mIView.showMessage("服务器内部错误");
                    break;
                case 401:
                    mIView.showMessage("请求验证错误");
                    break;
                case 404:
                    mIView.showMessage("请求资源不存在");
                    break;
                default:
                    mIView.showMessage("网络错误");
                    break;
            }
        } else if (e instanceof SocketException) {
            mIView.showMessage("连接失败");
        } else if (e instanceof SocketTimeoutException) {
            mIView.showMessage("连接超时");
        } else if (e instanceof IOException) {
            mIView.showMessage("网络错误");
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            mIView.showMessage("解析失败");
        } else if (e instanceof NullPointerException) {
            mIView.showMessage("空指针异常");
        } else if (e instanceof NetworkOnMainThreadException) {
            mIView.showMessage("主线程耗时操作");
        } else {
            mIView.showMessage(!TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : "未知错误");
        }
        error(e);
    }

    @Override
    public void onComplete() {

    }

    /**
     * 正常事件处理
     *
     * @param t
     */
    protected abstract void next(T t);

    /**
     * 发生错误但不至于一定退出软件时会调用该方法；否则，不调用。
     *
     * @param e
     */
    protected void error(Throwable e) {

    }
}
