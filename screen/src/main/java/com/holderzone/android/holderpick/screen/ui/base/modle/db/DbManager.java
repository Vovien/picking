package com.holderzone.android.holderpick.screen.ui.base.modle.db;


import com.holderzone.android.holderpick.screen.data.db.EnterpriseInfo;
import com.holderzone.android.holderpick.screen.ui.base.modle.ReleaseResourceManager;

import io.reactivex.Observable;

/**
 * 数据库仓库管理
 *
 * @author www
 * @date 2018/11/9 9:37.
 */

public interface DbManager extends ReleaseResourceManager {


    /**
     * 保存企业信息和token
     *
     * @param enterpriseInfo
     * @return Observable<Boolean>
     * @date 2018/11/27 13:14
     */
    Observable<EnterpriseInfo> saveEnterpriseInfo(EnterpriseInfo enterpriseInfo);

    /**
     * 取企业信息和token
     *
     * @return Observable<EnterpriseInfo>
     * @date 2018/11/27 13:14
     */
    Observable<EnterpriseInfo> getEnterpriseInfo();
}
