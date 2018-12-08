package com.holderzone.android.holderpick.screen.ui.base.modle.preference;

import io.reactivex.Observable;

/**
 * Preference实现类
 *
 * @author www
 * @date 2018/11/9 10:19.
 */

public class PreferenceImpl implements PreferenceManager {

    private volatile static PreferenceImpl sInstance;

    /**
     * 单例模式
     */
    public static PreferenceImpl getInstance() {
        if (sInstance == null) {
            synchronized (PreferenceImpl.class) {
                if (sInstance == null) {
                    sInstance = new PreferenceImpl();
                }
            }
        }
        return sInstance;
    }

    private PreferenceImpl() {
    }


    @Override
    public Observable<Boolean> releaseForAppExit() {
        return null;
    }
}
