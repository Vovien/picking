package com.holderzone.android.holderpick.screen.ui.setting.print;

import com.holderzone.android.holderpick.screen.ui.base.presenter.BasePresenter;

/**
 * 打印设置P层
 *
 * @author www
 * @date 2018/12/5 10:47.
 */

public class PrintSettingPresenter extends BasePresenter<PrintSettingContract.View> implements PrintSettingContract.Presenter {
    /**
     * 使用单例实例化存储库
     *
     * @param view 视图
     * @date 2018/11/9 10:09
     */
    public PrintSettingPresenter(PrintSettingContract.View view) {
        super(view);
    }
}
