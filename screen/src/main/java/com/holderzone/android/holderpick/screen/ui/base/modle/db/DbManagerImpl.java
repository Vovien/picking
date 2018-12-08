package com.holderzone.android.holderpick.screen.ui.base.modle.db;

import android.database.sqlite.SQLiteDatabase;

import com.holderzone.android.holderpick.screen.App;
import com.holderzone.android.holderpick.screen.data.db.DaoMaster;
import com.holderzone.android.holderpick.screen.data.db.DaoSession;
import com.holderzone.android.holderpick.screen.data.db.EnterpriseInfo;
import com.holderzone.android.holderpick.screen.data.db.EnterpriseInfoDao;

import java.util.List;

import io.reactivex.Observable;

/**
 * 数据库实现类
 *
 * @author www
 * @date 2018/11/9 10:20.
 */

public class DbManagerImpl implements DbManager {

    private volatile static DbManagerImpl sInstance;
    private final EnterpriseInfoDao mEenterpriseInfoDao;

    /**
     * 单例模式
     */
    public static DbManagerImpl getInstance() {
        if (sInstance == null) {
            synchronized (DbManagerImpl.class) {
                if (sInstance == null) {
                    sInstance = new DbManagerImpl();
                }
            }
        }
        return sInstance;
    }

    /**
     * 私有静态构造方法、保证数据的安全性
     *
     * @date 2018/11/9 10:21
     */
    private DbManagerImpl() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(App.getContext(), "picking_pad_db", null);
        SQLiteDatabase sqLiteDatabase = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(sqLiteDatabase);
        DaoSession daoSession = daoMaster.newSession();
        mEenterpriseInfoDao = daoSession.getEnterpriseInfoDao();
    }

    @Override
    public Observable<Boolean> releaseForAppExit() {
        return Observable.create(e -> {
            mEenterpriseInfoDao.deleteAll();
            e.onNext(true);
            e.onComplete();
        });
    }

    @Override
    public Observable<EnterpriseInfo> saveEnterpriseInfo(EnterpriseInfo enterpriseInfo) {
        return Observable.create(e -> {
            mEenterpriseInfoDao.deleteAll();
            mEenterpriseInfoDao.insert(enterpriseInfo);
            e.onNext(enterpriseInfo);
            e.onComplete();
        });
    }

    @Override
    public Observable<EnterpriseInfo> getEnterpriseInfo() {
        return Observable.create(e -> {
            List<EnterpriseInfo> enterpriseInfos = mEenterpriseInfoDao.loadAll();
            if (enterpriseInfos.size() > 0) {
                e.onNext(enterpriseInfos.get(0));
            }
            e.onComplete();
        });

    }
}
