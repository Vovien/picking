package com.holderzone.android.holderpick.screen.ui.base.modle.cache;

import com.holderzone.android.holderpick.screen.data.db.EnterpriseInfo;

import io.reactivex.Observable;

/**
 * 缓存实现类
 *
 * @author www
 * @date 2018/11/9 10:12.
 */

public class CacheManagerImpl implements CacheManager {
    private EnterpriseInfo mEnterpriseInfo;
    private volatile static CacheManagerImpl sInstance;

    /**
     * 单例模式
     */
    public static CacheManagerImpl getInstance() {
        if (sInstance == null) {
            synchronized (CacheManagerImpl.class) {
                if (sInstance == null) {
                    sInstance = new CacheManagerImpl();
                }
            }
        }
        return sInstance;
    }

    private CacheManagerImpl() {
    }


    @Override
    public Observable<Boolean> releaseForAppExit() {
        return Observable.create(e -> {
            mEnterpriseInfo = null;
            e.onNext(true);
            e.onComplete();
        });

    }

    @Override
    public Observable<EnterpriseInfo> saveEnterpriseInfo(EnterpriseInfo enterpriseInfo) {
        return Observable.create(e -> {
            mEnterpriseInfo = enterpriseInfo;
            e.onNext(enterpriseInfo);
            e.onComplete();
        });

    }

    @Override
    public Observable<EnterpriseInfo> getEnterpriseInfo() {
        return Observable.create(e -> {
            if (mEnterpriseInfo != null) {
                e.onNext(mEnterpriseInfo);
            }
            e.onComplete();
        });

    }
}
