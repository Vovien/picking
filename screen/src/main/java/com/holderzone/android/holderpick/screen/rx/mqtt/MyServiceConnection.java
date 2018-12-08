package com.holderzone.android.holderpick.screen.rx.mqtt;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 *
 *
 * @author www
 * @date 2018/11/27 17:46.
 */

public class MyServiceConnection implements ServiceConnection {

    private MQttService mqttService;
    private IGetMessageCallBack IGetMessageCallBack;

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        mqttService = ((MQttService.CustomBinder) iBinder).getService();
        mqttService.setIGetMessageCallBack(IGetMessageCallBack);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    public MQttService getMqttService() {
        return mqttService;
    }

    public void setIGetMessageCallBack(IGetMessageCallBack IGetMessageCallBack) {
        this.IGetMessageCallBack = IGetMessageCallBack;
    }
}

