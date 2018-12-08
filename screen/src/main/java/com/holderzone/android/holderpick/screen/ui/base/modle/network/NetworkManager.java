package com.holderzone.android.holderpick.screen.ui.base.modle.network;


import com.holderzone.android.holderpick.screen.data.network.ApiHelper;

/**
 * 网络数据管理
 *
 * @author www
 * @date 2018/11/9 9:54.
 */

public interface NetworkManager {


    /**
     * 拿到api接口
     *
     * @return ApiHelper
     * @date 2018/11/12 17:41
     */
    ApiHelper getApiHelper();
}
