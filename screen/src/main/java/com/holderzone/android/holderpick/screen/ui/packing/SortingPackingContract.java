package com.holderzone.android.holderpick.screen.ui.packing;

import com.holderzone.android.holderpick.screen.data.domain.dto.req.UpdateSortingReq;
import com.holderzone.android.holderpick.screen.data.emq.Material;
import com.holderzone.android.holderpick.screen.data.emq.SortingDevice;
import com.holderzone.android.holderpick.screen.data.eneity.SortingDetail;
import com.holderzone.android.holderpick.screen.ui.base.presenter.IPresenter;
import com.holderzone.android.holderpick.screen.ui.base.view.IView;

import java.util.List;

/**
 * 分拣装箱契约
 *
 * @author www
 * @date 2018/11/24 16:17.
 */

public interface SortingPackingContract {
    interface View extends IView {

        /**
         * 屏显分拣数量成功
         *
         * @param sortingDetail
         * @date 2018/11/24 18:02
         */
        void padSortingSuccess(SortingDetail sortingDetail);

        /**
         * 修改分拣数量成功
         *
         * @date 2018/11/26 12:01
         */
        void updateSortingSuccess();


        /**
         * 修改分拣数量失败
         * @date 2018/12/5 17:24
         */
        void updateSortingFail();

        /**
         * 获取分拣列表
         *
         * @param detailList
         * @date 2018/11/30 12:11
         */
        void getSortingList(List<SortingDetail> detailList);

        /**
         * 分拣列表获取失败
         * @date 2018/12/5 13:54
         */
        void getSortingListFail();
    }

    interface Presenter extends IPresenter {

        /**
         * 新增装箱数量
         *
         * @param sortingDevice 分拣设备
         * @param mMaterial     物品
         * @param count         新增之后的数量
         * @date 2018/11/24 17:55
         */
        void addSorting(SortingDevice sortingDevice, Material mMaterial, double count);


        /**
         * 屏显设备修改分拣数量
         *
         * @param updateSortingReq
         * @date 2018/11/24 20:23
         */
        void updateSorting(UpdateSortingReq updateSortingReq);


        /**
         * 获取分拣数据列表
         *
         * @param sortingId
         * @date 2018/11/30 12:10
         */
        void requestPackingList(int sortingId);
    }
}
