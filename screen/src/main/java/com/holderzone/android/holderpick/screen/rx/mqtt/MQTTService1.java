package com.holderzone.android.holderpick.screen.rx.mqtt;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.holderzone.android.holderpick.screen.data.eneity.Emq;
import com.holderzone.android.holderpick.screen.util.helper.DeviceHelper;
import com.orhanobut.logger.Logger;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.EventBus;

/**
 * MQTT长连接服务
 *
 * @author 一口仨馍 联系方式 : yikousamo@gmail.com
 * @version 创建时间：2016/9/16 22:06
 */
public class MQTTService1 extends Service {

    public static final String TAG = MQTTService1.class.getSimpleName();

    private static MqttAndroidClient client;
    private MqttConnectOptions conOpt;

    //    private String host = "tcp://10.0.2.2:61613";
    private String host = "tcp://192.168.1.103:61613";
    private String userName = "admin";
    private String passWord = "password";
    private static String myTopic = "topic";
    private String clientId = "test";
    private Emq emq;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        emq = intent.getParcelableExtra("EMQ");
        init(emq);
        return super.onStartCommand(intent, flags, startId);
    }

    public static void publish(String msg) {
        String topic = myTopic;
        Integer qos = 0;
        Boolean retained = false;
        try {
            client.publish(topic, msg.getBytes(), qos.intValue(), retained.booleanValue());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void init(Emq emq) {
        // 服务器地址（协议+地址+端口号）
        String uri = host;
        client = new MqttAndroidClient(this, "tcp://" + emq.getIp() + ":" + emq.getPort(), DeviceHelper.getInstance().getDeviceID());
        // 设置MQTT监听并且接受消息
        client.setCallback(mqttCallback);

        conOpt = new MqttConnectOptions();
        // 清除缓存
        conOpt.setCleanSession(true);
        // 设置超时时间，单位：秒
        conOpt.setConnectionTimeout(10);
        // 心跳包发送间隔，单位：秒
        conOpt.setKeepAliveInterval(20);
        // 用户名
        conOpt.setUserName(userName);
        // 密码
        conOpt.setPassword(passWord.toCharArray());

        // last will message
        boolean doConnect = true;
        String message = "{\"terminal_uid\":\"" + clientId + "\"}";
        String topic = myTopic;
        Integer qos = 2;
        Boolean retained = false;
        if ((!message.equals("")) || (!topic.equals(""))) {
            // 最后的遗嘱
            try {
                conOpt.setWill(topic, message.getBytes(), qos.intValue(), retained.booleanValue());
            } catch (Exception e) {
                Logger.i(TAG, "Exception Occured", e);
                doConnect = false;
                iMqttActionListener.onFailure(null, e);
            }
        }

        if (doConnect) {
            doClientConnection();
        }

    }

    @Override
    public void onDestroy() {
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    /**
     * 连接MQTT服务器
     */
    private void doClientConnection() {
        if (!client.isConnected() && isConnectIsNomarl()) {
            try {
                client.connect(conOpt, null, iMqttActionListener);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

    }

    // MQTT是否连接成功
    private IMqttActionListener iMqttActionListener = new IMqttActionListener() {

        @Override
        public void onSuccess(IMqttToken arg0) {
            Logger.i("连接成功 ");
            try {
                // 订阅myTopic话题
                client.subscribe("Topic/" + "f74e3165-d196-4d4e-8c74-73ff3cca86c0" + "/Pad/" + DeviceHelper.getInstance().getDeviceID(), 1);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(IMqttToken arg0, Throwable arg1) {
            arg1.printStackTrace();
            // 连接失败，重连
        }
    };

    // MQTT监听并且接受消息
    private MqttCallback mqttCallback = new MqttCallback() {

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {

            String str1 = new String(message.getPayload());
//            MQTTMessage msg = new MQTTMessage();
//            msg.setMessage(str1);
            EventBus.getDefault().post(str1);
            String str2 = topic + ";qos:" + message.getQos() + ";retained:" + message.isRetained();
            Logger.i("messageArrived:" + str1);
            Logger.i(str2);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken arg0) {

        }

        @Override
        public void connectionLost(Throwable arg0) {
            // 失去连接，重连
        }
    };

    /**
     * 判断网络是否连接
     */
    private boolean isConnectIsNomarl() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            String name = info.getTypeName();
            Logger.i("MQTT当前网络名称：" + name);
            return true;
        } else {
            Logger.i("MQTT 没有可用网络");
            return false;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
