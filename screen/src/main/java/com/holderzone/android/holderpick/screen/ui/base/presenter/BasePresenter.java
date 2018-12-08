package com.holderzone.android.holderpick.screen.ui.base.presenter;


import com.holderzone.android.holderpick.screen.ui.base.modle.Repository;
import com.holderzone.android.holderpick.screen.ui.base.modle.RepositoryImpl;
import com.holderzone.android.holderpick.screen.ui.base.view.IView;

/**
 * 基类presenter
 *
 * @author www
 * @date 2018/11/8 17:58.
 */

public class BasePresenter<V extends IView> implements IPresenter {
    protected V mView;

    protected Repository mRepository;


    /**
     * 使用单例实例化存储库
     *
     * @param view 视图
     * @date 2018/11/9 10:09
     */
    public BasePresenter(V view) {
        mView = view;
        mRepository = RepositoryImpl.getInstance();
    }

    @Override
    public void onDestroy() {
        mView = null;
        mRepository = null;
    }
}
