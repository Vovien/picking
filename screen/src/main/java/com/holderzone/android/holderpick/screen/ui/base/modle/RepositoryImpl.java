package com.holderzone.android.holderpick.screen.ui.base.modle;


import com.holderzone.android.holderpick.screen.data.db.EnterpriseInfo;
import com.holderzone.android.holderpick.screen.data.network.ApiHelper;
import com.holderzone.android.holderpick.screen.ui.base.modle.cache.CacheManager;
import com.holderzone.android.holderpick.screen.ui.base.modle.cache.CacheManagerImpl;
import com.holderzone.android.holderpick.screen.ui.base.modle.db.DbManager;
import com.holderzone.android.holderpick.screen.ui.base.modle.db.DbManagerImpl;
import com.holderzone.android.holderpick.screen.ui.base.modle.file.FileManager;
import com.holderzone.android.holderpick.screen.ui.base.modle.file.FileManagerImpl;
import com.holderzone.android.holderpick.screen.ui.base.modle.network.NetworkManager;
import com.holderzone.android.holderpick.screen.ui.base.modle.network.NetworkManagerImpl;
import com.holderzone.android.holderpick.screen.ui.base.modle.preference.PreferenceImpl;
import com.holderzone.android.holderpick.screen.ui.base.modle.preference.PreferenceManager;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 存储库实现类
 *
 * @author www
 * @date 2018/11/9 10:00.
 */

public class RepositoryImpl implements Repository {

    private volatile static RepositoryImpl sInstance;

    private CacheManager mCacheManager;

    private FileManager mFileManager;

    private PreferenceManager mPreferenceManager;

    private DbManager mDbManager;

    private NetworkManager mNetworkManager;

    /**
     * 单例模式
     */
    public static RepositoryImpl getInstance() {
        if (sInstance == null) {
            synchronized (RepositoryImpl.class) {
                if (sInstance == null) {
                    sInstance = new RepositoryImpl();
                }
            }
        }
        return sInstance;
    }


    /**
     * 私有静态构造方法、保证数据的安全性
     *
     * @date 2018/11/9 10:11
     */
    private RepositoryImpl() {
        mCacheManager = CacheManagerImpl.getInstance();
        mFileManager = FileManagerImpl.getInstance();
        mPreferenceManager = PreferenceImpl.getInstance();
        mDbManager = DbManagerImpl.getInstance();
        mNetworkManager = NetworkManagerImpl.getInstance();
    }


    @Override
    public Observable<Boolean> releaseForAppExit() {
        return mCacheManager.releaseForAppExit()
                .flatMap(aBoolean -> mPreferenceManager.releaseForAppExit())
                .flatMap(aBoolean -> mDbManager.releaseForAppExit())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public ApiHelper getApiHelper() {
        return mNetworkManager.getApiHelper();
    }

    @Override
    public Observable<EnterpriseInfo> saveEnterpriseInfo(EnterpriseInfo enterpriseInfo) {
        return mCacheManager.saveEnterpriseInfo(enterpriseInfo)
                .flatMap(mDbManager::saveEnterpriseInfo);
    }

    @Override
    public Observable<EnterpriseInfo> getEnterpriseInfo() {
        Observable<EnterpriseInfo> memory = mCacheManager.getEnterpriseInfo();
        Observable<EnterpriseInfo> disk = mDbManager.getEnterpriseInfo()
                .flatMap(mCacheManager::saveEnterpriseInfo);
        return Observable.concat(memory, disk)
                .firstOrError().toObservable();
    }
}
