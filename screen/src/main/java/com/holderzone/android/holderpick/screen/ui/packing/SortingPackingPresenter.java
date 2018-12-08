package com.holderzone.android.holderpick.screen.ui.packing;

import com.holderzone.android.holderpick.screen.data.domain.dto.req.UpdateSortingReq;
import com.holderzone.android.holderpick.screen.data.domain.dto.resp.base.Resp;
import com.holderzone.android.holderpick.screen.data.emq.Material;
import com.holderzone.android.holderpick.screen.data.emq.SortingDevice;
import com.holderzone.android.holderpick.screen.data.eneity.SortingDetail;
import com.holderzone.android.holderpick.screen.data.network.rephanding.BaseObserver;
import com.holderzone.android.holderpick.screen.rx.transformer.RxTransformer;
import com.holderzone.android.holderpick.screen.ui.base.presenter.BasePresenter;

import java.util.List;

import io.reactivex.Observable;

/**
 * 分拣装箱
 *
 * @author www
 * @date 2018/11/24 16:17.
 */

public class SortingPackingPresenter extends BasePresenter<SortingPackingContract.View> implements SortingPackingContract.Presenter {

    /**
     * 使用单例实例化存储库
     *
     * @param view 视图
     * @date 2018/11/9 10:09
     */
    public SortingPackingPresenter(SortingPackingContract.View view) {
        super(view);
    }

    @Override
    public void addSorting(SortingDevice sortingDevice, Material mMaterial, double count) {
        if (mMaterial != null) {
            SortingDetail sortingDetail = new SortingDetail();
            sortingDetail.setSortingID(sortingDevice.getSortingID());
            sortingDetail.setMaterialName(mMaterial.getMaterialName());
            sortingDetail.setCustomerID(sortingDevice.getCustomerID());
            sortingDetail.setCustomerName(sortingDevice.getCustomerName());
            sortingDetail.setSortingMaterialPackageID(mMaterial.getSortingMaterialPackageID());
            sortingDetail.setSortingMaterialPackageName(mMaterial.getSortingMaterialPackageName());
            sortingDetail.setSortingMaterialPackageUnitID(mMaterial.getSortingMaterialPackageUnitID());
            sortingDetail.setSortingMaterialPackageUnitName(mMaterial.getSortingMaterialPackageUnitName());
            sortingDetail.setPlannedQuantity(mMaterial.getPlannedQuantity());
            sortingDetail.setQuantity(count);
            Observable<Resp> observable = mRepository.getApiHelper().padSorting(sortingDetail);
            observable.compose(RxTransformer.applyTransformer(mView))
                    .subscribe(new BaseObserver<Resp>(mView) {
                        @Override
                        protected void next(Resp resp) {
                            if (resp.getData() != null && (Boolean) resp.getData()) {
                                mView.padSortingSuccess(sortingDetail);
                            }
                        }

                        @Override
                        protected void error(Throwable e) {
                            super.error(e);
                        }
                    });
        }

    }

    @Override
    public void updateSorting(UpdateSortingReq updateSortingReq) {
        Observable<Resp> observable = mRepository.getApiHelper().updateSorting(updateSortingReq);
        observable.compose(RxTransformer.applyTransformer(mView))
                .subscribe(new BaseObserver<Resp>(mView) {
                    @Override
                    protected void next(Resp resp) {
                        if (resp.getData() != null && (Boolean) resp.getData()) {
                            mView.updateSortingSuccess();
                        }
                    }

                    @Override
                    protected void error(Throwable e) {
                        super.error(e);
                        mView.updateSortingSuccess();
                    }
                });
    }

    @Override
    public void requestPackingList(int sortingId) {
        Observable<Resp<List<SortingDetail>>> observable = mRepository.getApiHelper().getSortingList(sortingId);
        observable.compose(RxTransformer.applyTransformer(mView))
                .subscribe(new BaseObserver<Resp<List<SortingDetail>>>(mView) {
                    @Override
                    protected void next(Resp<List<SortingDetail>> listResp) {
                        if (listResp != null) {
                            List<SortingDetail> detailList = listResp.getData();
                            mView.getSortingList(detailList);
                        }
                    }

                    @Override
                    protected void error(Throwable e) {
                        super.error(e);
                        mView.getSortingListFail();
                    }
                });
    }
}
