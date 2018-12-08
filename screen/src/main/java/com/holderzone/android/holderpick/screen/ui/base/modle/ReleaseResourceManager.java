package com.holderzone.android.holderpick.screen.ui.base.modle;

import io.reactivex.Observable;

/**
 * 释放资源
 *
 * @author www
 * @date 2018/11/9 9:39.
 */

public interface ReleaseResourceManager {


    /**
     * 释放资源并退出App程序
     *
     * @return Observable<Boolean>
     * @date 2018/11/9 9:41
     */
    Observable<Boolean> releaseForAppExit();
}
