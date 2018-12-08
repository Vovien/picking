package com.holderzone.android.holderpick.screen;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;
import android.widget.Toast;

import com.blankj.utilcode.util.Utils;
import com.holderzone.android.holderpick.screen.data.real.PushRealmMigration;
import com.holderzone.android.holderpick.screen.ui.base.Constants;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * 程序入口App
 *
 * @author www
 * @date 2018/11/8 11:18.
 */

public class App extends Application {
    /**
     * App的Tag
     */
    public static final String TAG = App.class.getSimpleName();

    /**
     * EMQ的Tag
     */
    private final static String EMQ_INFO = "EMQ_INFO";

    /**
     * App静态实例
     */
    private static App mApp;

    /**
     * App的静态context
     */
    private static Context mAppContext;

    /**
     * 吐司
     */
    private static Toast mToast;

    /**
     * 订阅管理者
     */
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    /**
     * 返回App实例
     */
    public static App getApplication() {
        return mApp;
    }

    /**
     * 返回App级别的context
     */
    public static Context getContext() {
        return mAppContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        mAppContext = this.getApplicationContext();
        Utils.init(this);
        initRealm();
        initLogger();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
    }


    /**
     * 初始化Logger
     *
     * @date 2018/11/26 19:46
     */
    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag("WWW_LOG")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

    }

    /**
     * 初始化realm
     *
     * @date 2018/11/8 11:37
     */
    private void initRealm() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(Constants.REALM_NAME)
                .schemaVersion(Constants.REALM_VERSION)
                // 开始迁移
                .migration(new PushRealmMigration())
                .build();
        Realm.setDefaultConfiguration(config);
    }

    /**
     * Toast显示
     *
     * @param message
     * @date 2018/11/8 11:52
     */
    public static void showMessage(String message) {
        new Handler(Looper.getMainLooper()).post((() -> {
            if (mToast == null) {
                mToast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(message);
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.show();
        }));
    }
}
