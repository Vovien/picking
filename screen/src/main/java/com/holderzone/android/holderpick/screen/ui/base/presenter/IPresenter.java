package com.holderzone.android.holderpick.screen.ui.base.presenter;

/**
 * 接口presenter
 *
 * @author www
 * @date 2018/11/8 10:10.
 */

public interface IPresenter {

    /**
     * view层调用以实现presenter的资源回收
     */
    void onDestroy();
}
