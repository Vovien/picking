package com.holderzone.android.holderpick.screen.exception.network;


import com.holderzone.android.holderpick.screen.data.domain.dto.resp.base.Resp;
import com.holderzone.android.holderpick.screen.util.helper.ApiNoteHelper;

/**
 * API异常处理类
 *
 * @author www
 * @date 2018/11/12 18:47.
 */

public class ApiException extends RuntimeException {
    private int Code = Integer.MIN_VALUE;

    public ApiException(Resp resp) {
        super(ApiNoteHelper.obtainErrorMsg(resp));
    }

    public int getCode() {
        return Code;
    }

}
