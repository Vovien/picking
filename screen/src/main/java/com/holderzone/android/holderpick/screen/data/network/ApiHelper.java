package com.holderzone.android.holderpick.screen.data.network;

import com.holderzone.android.holderpick.screen.data.domain.dto.req.UpdateSortingReq;
import com.holderzone.android.holderpick.screen.data.domain.dto.resp.base.Resp;
import com.holderzone.android.holderpick.screen.data.domain.dto.resp.home.InitializeInfo;
import com.holderzone.android.holderpick.screen.data.eneity.SortingDetail;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * API接口
 *
 * @author www
 * @date 2018/11/9 17:59.
 */

public interface ApiHelper {

    /**
     * 初始化屏显设备获取相关数据
     *
     * @return Resp
     * @date 2018/11/24 13:44
     */
    @GET("api/padInitialize")
    Observable<Resp<InitializeInfo>> initialize();

    /**
     * 分拣数量完成
     *
     * @param sortingDetail 分拣物品
     * @return Observable<Resp>
     * @date 2018/11/9 18:06
     */
    @POST("api/padSorting")
    Observable<Resp> padSorting(@Body SortingDetail sortingDetail);

    /**
     * 修改分拣数量
     *
     * @param updateSortingReq
     * @return Observable<Resp>
     * @date 2018/11/26 12:03
     */
    @PUT("api/padSorting")
    Observable<Resp> updateSorting(@Body UpdateSortingReq updateSortingReq);

    /**
     * 获取分拣列表
     *
     * @param sortingID
     * @return Observable<Resp>
     * @date 2018/11/30 12:13
     */
    @GET("api/padSorting/{sortingId}")
    Observable<Resp<List<SortingDetail>>> getSortingList(@Path("sortingId") int sortingID);
}
