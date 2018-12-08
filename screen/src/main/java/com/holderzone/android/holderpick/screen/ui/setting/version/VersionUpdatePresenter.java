package com.holderzone.android.holderpick.screen.ui.setting.version;

import com.holderzone.android.holderpick.screen.ui.base.presenter.BasePresenter;

/**
 * 版本更新P层
 *
 * @author www
 * @date 2018/12/4 18:19.
 */

public class VersionUpdatePresenter extends BasePresenter<VersionUpdateContract.View> implements VersionUpdateContract.Presenter {
    /**
     * 使用单例实例化存储库
     *
     * @param view 视图
     * @date 2018/11/9 10:09
     */
    public VersionUpdatePresenter(VersionUpdateContract.View view) {
        super(view);
    }

}
