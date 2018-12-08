package com.holderzone.android.holderpick.screen.ui.base.modle.network;

import android.annotation.SuppressLint;

import com.holderzone.android.holderpick.screen.App;
import com.holderzone.android.holderpick.screen.BuildConfig;
import com.holderzone.android.holderpick.screen.data.network.ApiHelper;
import com.holderzone.android.holderpick.screen.data.network.interceptor.CacheInterceptor;
import com.holderzone.android.holderpick.screen.data.network.interceptor.HeaderInterceptor;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络实现类
 *
 * @author www
 * @date 2018/11/9 10:36.
 */

public class NetworkManagerImpl implements NetworkManager {

    private volatile static NetworkManagerImpl sInstance;

    private ApiHelper apiHelper;
    /**
     * 定义超时时间（单位：秒）
     */
    private static final int TIME_OUT = 20;


    /**
     * 单例模式、实例化网络框架
     */
    public static NetworkManagerImpl getInstance() {
        if (sInstance == null) {
            synchronized (NetworkManagerImpl.class) {
                if (sInstance == null) {
                    sInstance = new NetworkManagerImpl();
                }
            }
        }
        return sInstance;
    }

    /**
     * 私有静态构造方法
     * OkHttpClient相关配置
     *
     * @date 2018/11/9 10:21
     */
    @SuppressLint("CheckResult")
    private NetworkManagerImpl() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        /**
         * 设置缓存
         */
        File cacheFile = new File(App.getApplication().getExternalCacheDir(), App.TAG);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        builder.cache(cache).addInterceptor(new CacheInterceptor());
        /**
         * 添加头信息
         */
        builder.addInterceptor(new HeaderInterceptor());
        /**
         * 登录拦截调试
         */
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Logger.d(message));
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        applyUnsafeTransform(builder);
        /**
         * 设置超时和重新连接
         */
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.HOST)
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiHelper = retrofit.create(ApiHelper.class);

    }

    /**
     * 信任所有证书
     *
     * @param builder
     */
    private OkHttpClient.Builder applyUnsafeTransform(OkHttpClient.Builder builder) {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier((hostname, session) -> true);
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiHelper getApiHelper() {
        return apiHelper;
    }

}
