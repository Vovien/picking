package com.holderzone.android.holderpick.screen.ui.home;


import com.holderzone.android.holderpick.screen.App;
import com.holderzone.android.holderpick.screen.data.domain.dto.resp.base.Resp;
import com.holderzone.android.holderpick.screen.data.domain.dto.resp.home.InitializeInfo;
import com.holderzone.android.holderpick.screen.data.network.rephanding.BaseObserver;
import com.holderzone.android.holderpick.screen.rx.transformer.RxTransformer;
import com.holderzone.android.holderpick.screen.ui.base.presenter.BasePresenter;
import com.holderzone.android.holderpick.screen.util.PreferenceUtils;

import io.reactivex.Observable;

/**
 * 主页Presenter
 *
 * @author www
 * @date 2018/11/13 18:33.
 */

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {
    /**
     * 使用单例实例化存储库
     *
     * @param view 主页
     * @date 2018/11/9 10:09
     */
    public HomePresenter(HomeContract.View view) {
        super(view);
    }

    @Override
    public void initialize() {
        Observable<Resp<InitializeInfo>> observable = mRepository.getApiHelper().initialize();
        observable.compose(RxTransformer.applyTransformer(mView))
                .subscribe(new BaseObserver<Resp<InitializeInfo>>(mView) {
                    @Override
                    protected void next(Resp<InitializeInfo> initializeInfoResp) {
                        if (initializeInfoResp.getData() != null) {
                            InitializeInfo initializeInfo = initializeInfoResp.getData();
                            mView.initSuccess(initializeInfo);
                            PreferenceUtils.storeStringData(App.getContext(), "TOKEN", initializeInfo.getToken());
                        }
                    }

                    @Override
                    protected void error(Throwable e) {
                        super.error(e);
                        mView.initFail();
                    }
                });
    }
}
