package com.holderzone.android.holderpick.screen.util.helper;

import com.holderzone.android.holderpick.screen.data.domain.dto.resp.base.Resp;

/**
 * ApiNote辅助类
 *
 * @author www
 * @date 2018/11/12 18:41.
 */

public class ApiNoteHelper {
    /**
     * 判断业务是否成功
     */
    public static boolean checkBusiness(Resp resp) {
        Object Data = resp.getData();
        if (Data == null) {
            return false;
        }
        Integer Code = resp.getCode();
        if (Code == null) {
            return false;
        }
        if (0 != Code) {
            return false;
        }
        return true;
    }

    /**
     * 获取错误提示信息
     */
    public static String obtainErrorMsg(Resp resp) {
        String msg = resp.getMsg();
        if (msg == null) {
            return "msg为空";
        }

        Integer code = resp.getCode();
        String message;
        switch (code) {
            case 1:
                message = resp.getMsg();
                break;
            default:
                message = resp.getMsg();
                break;
        }
        return message + "(" + code + ")";
    }
}
