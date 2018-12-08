package com.holderzone.android.holderpick.screen.util.helper;

import java.lang.reflect.Method;


/**
 * 设备辅助类、用于查看设备序列号
 *
 * @author www
 * @date 2018/11/1 10:26.
 */

public class DeviceHelper {

    private volatile static DeviceHelper sInstance;

    public static DeviceHelper getInstance() {
        if (sInstance == null) {
            synchronized (DeviceHelper.class) {
                if (sInstance == null) {
                    sInstance = new DeviceHelper();
                }
            }
        }
        return sInstance;
    }

    /**
     * 设备ID
     */
    private String mDeviceID;

    /**
     * 登录时间
     */
    private long mLoginTime;

    public String getDeviceID() {
        if (mDeviceID == null) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method get = c.getMethod("get", String.class);
                mDeviceID = (String) get.invoke(c, "ro.serialno");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "sb77777777";
    }
}
