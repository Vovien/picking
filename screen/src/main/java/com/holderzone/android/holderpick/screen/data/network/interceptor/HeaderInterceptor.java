package com.holderzone.android.holderpick.screen.data.network.interceptor;

import android.annotation.SuppressLint;

import com.holderzone.android.holderpick.screen.App;
import com.holderzone.android.holderpick.screen.data.network.constant.HeaderConstant;
import com.holderzone.android.holderpick.screen.util.PreferenceUtils;
import com.holderzone.android.holderpick.screen.util.helper.DeviceHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 设置头信息
 * 添加请求头信息，服务器进行token有效性验证
 *
 * @author www
 * @date 2018/11/12 18:05.
 */

public class HeaderInterceptor implements Interceptor {

    public HeaderInterceptor() {
    }

    @SuppressLint("CheckResult")
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder requestBuilder = originalRequest.newBuilder()
                .addHeader("Accept-Encoding", "gzip")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("AppID", DeviceHelper.getInstance().getDeviceID())
                .method(originalRequest.method(), originalRequest.body());
        if (PreferenceUtils.getStringData(App.getContext(), "TOKEN") != null) {
            requestBuilder.addHeader(HeaderConstant.HEADER_TOKEN, "Bearer " + PreferenceUtils.getStringData(App.getContext(), "TOKEN"));
        }

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
