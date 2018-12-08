package com.holderzone.android.holderpick.screen.data.domain.dto.resp.base;


import java.io.Serializable;

/**
 * 返回基类实体
 *
 * @author www
 * @date 2018/11/9 18:27.
 */

public class Resp<T> implements Serializable {

    /**
     * 状态码
     */
    private int code;

    /**
     * 状态消息
     */
    private String msg;

    /**
     * 数据实体
     */
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
