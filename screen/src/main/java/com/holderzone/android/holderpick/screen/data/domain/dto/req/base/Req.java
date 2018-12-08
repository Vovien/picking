package com.holderzone.android.holderpick.screen.data.domain.dto.req.base;

import java.io.Serializable;

/**
 * 请求实体基类
 *
 * @author www
 * @date 2018/11/12 9:14.
 */

public class Req implements Serializable {

    /**
     * 设备类型int
     * 1 = pc端
     * 2 = pda端（默认）
     */
    private int Source = 2;

    public int getSource() {
        return Source;
    }

    public void setSource(int source) {
        Source = source;
    }
}
