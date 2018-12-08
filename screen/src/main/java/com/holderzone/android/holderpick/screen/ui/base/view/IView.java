package com.holderzone.android.holderpick.screen.ui.base.view;

import android.content.Intent;

/**
 * 基类view
 *
 * @author www
 * @date 2018/11/8 10:19.
 */

public interface IView {
    /**
     * 显示进度dialog
     */
    void showLoading();

    /**
     * 隐藏dialog
     */
    void hideLoading();

    /**
     * 取消订阅
     */
    void onDispose();

    /**
     * 显示信息
     */
    void showMessage(String message);

    /**
     * 跳转activity
     */
    void launchActivity(Intent intent);

    /**
     * 结束activity
     */
    void finishActivity();
}
