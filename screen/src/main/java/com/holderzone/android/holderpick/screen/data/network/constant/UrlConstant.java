package com.holderzone.android.holderpick.screen.data.network.constant;

/**
 * 地址参数
 *
 * @author www
 * @date 2018/11/9 11:21.
 */

public class UrlConstant {

    /**
     * 请求地址协议
     */
    public static final String URL_PROTOCOL = "http";

    /**
     * 请求地址ip(荣波)
     */
//    public static final String URL_IP = "holder.f3322.net";

    /**
     * 测试外网
     */
    public static final String URL_IP = "erp-uat.holderzone.cn/picking";

    /**
     * 请求地址端口号
     */
//    public static final String URL_PORT = ":5011";
    public static final String URL_PORT = "";

    /**
     * url地址目录
     */
    public static final String URL_PATH = "api";

    /**
     * pda请求地址
     */
    public static final String PAD_URL = URL_PROTOCOL + "://" + URL_IP
            + URL_PORT + "/";

}
