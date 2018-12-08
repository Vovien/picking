package com.holderzone.android.holderpick.screen.ui.home;

import com.holderzone.android.holderpick.screen.data.domain.dto.resp.home.InitializeInfo;
import com.holderzone.android.holderpick.screen.ui.base.presenter.IPresenter;
import com.holderzone.android.holderpick.screen.ui.base.view.IView;

/**
 * @author www
 * @date 2018/11/13 18:27.
 */

public interface HomeContract {
    interface View extends IView {

        /**
         * 初始化数据成功
         *
         * @param initializeInfo
         * @date 2018/11/14 14:23
         */
        void initSuccess(InitializeInfo initializeInfo);

        /**
         * 初始化数据失败
         *
         * @date 2018/11/14 14:23
         */
        void initFail();


    }

    interface Presenter extends IPresenter {

        /**
         * 主页初始化数据
         *
         * @date 2018/11/14 14:22
         */
        void initialize();
    }
}
