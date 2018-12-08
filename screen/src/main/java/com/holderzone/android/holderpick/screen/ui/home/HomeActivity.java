package com.holderzone.android.holderpick.screen.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.holderzone.android.holderpick.screen.R;
import com.holderzone.android.holderpick.screen.data.domain.dto.resp.home.InitializeInfo;
import com.holderzone.android.holderpick.screen.data.emq.SortingDevice;
import com.holderzone.android.holderpick.screen.rx.mqtt.MqttManager;
import com.holderzone.android.holderpick.screen.ui.base.view.activity.BaseActivity;
import com.holderzone.android.holderpick.screen.ui.packing.SortingPackingActivity;
import com.holderzone.android.holderpick.screen.ui.setting.SettingActivity;
import com.holderzone.android.holderpick.screen.util.helper.DeviceHelper;
import com.holderzone.android.holderpick.screen.util.manager.ActivityManager;
import com.holderzone.android.holderpick.screen.widget.Title;
import com.kennyc.view.MultiStateView;
import com.orhanobut.logger.Logger;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主页
 *
 * @author www
 * @date 2018/11/19 14:47.
 */

public class HomeActivity extends BaseActivity<HomeContract.Presenter> implements HomeContract.View {
    /**
     * 标题栏
     */
    @BindView(R.id.title)
    Title mTitle;
    @BindView(R.id.ll_home)
    LinearLayout mLlHome;
    @BindView(R.id.msv_designated)
    MultiStateView msvDesignated;

    /**
     * 获取相关参数
     */
    private String enterpriseGUID;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        return intent;
    }

    @Override
    protected void handleBundleExtras(@NonNull Bundle extras) {

    }

    @Override
    protected void handleSavedInstanceState(@NonNull Bundle savedInstanceState) {

    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_home;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        mTitle.setTitleText("拣货宝");
        mTitle.setOnMenuClickListener(() -> launchActivity(SettingActivity.newIntent(HomeActivity.this)));
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.initialize();
    }

    @Nullable
    @Override
    protected HomeContract.Presenter initPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected void onSubscribe(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onDispose() {

    }

    @Override
    public void initSuccess(InitializeInfo initializeInfo) {
        new Thread(() -> {
            boolean b = MqttManager.getInstance().creatConnect("tcp://" +
                            initializeInfo.getEmq().getIp() + ":" +
                            initializeInfo.getEmq().getPort(),
                    initializeInfo.getEmq().getAccountNo(),
                    initializeInfo.getEmq().getPassword(),
                    DeviceHelper.getInstance().getDeviceID());
            Logger.d("isConnected: " + b);
            enterpriseGUID = initializeInfo.getEnterprise().getEnterpriseGUID();
            if (b) {
                MqttManager.getInstance().subscribe("Topic/" + initializeInfo.getEnterprise().getEnterpriseGUID() + "/Pad/" + DeviceHelper.getInstance().getDeviceID(), 2);
                if (initializeInfo.getSortingDeviceDTO() != null && initializeInfo.getSortingDeviceDTO().getCustomerID() != 0) {
                    launchActivity(SortingPackingActivity.newIntent(HomeActivity.this, initializeInfo.getSortingDeviceDTO(), initializeInfo.getEnterprise().getEnterpriseGUID()));
                    if (EventBus.getDefault().isRegistered(this)) {
                        EventBus.getDefault().unregister(this);
                    }
                }
            } else {
                showMessage("EMQ连接失败");
            }
        }).start();
        msvDesignated.setVisibility(View.VISIBLE);
        msvDesignated.setViewState(MultiStateView.VIEW_STATE_CONTENT);
    }

    @Override
    public void initFail() {
        mTitle.setTitleText("拣货宝");
        msvDesignated.setVisibility(View.VISIBLE);
        msvDesignated.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    @OnClick({R.id.btn_retry})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_retry:
                mPresenter.initialize();
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MqttMessage message) {
        try {
            Gson gson = new Gson();
            SortingDevice sortingDevice = gson.fromJson(message.toString(), SortingDevice.class);

            if (sortingDevice.getDeviceCode() != null && sortingDevice.getDeviceCode().equals(DeviceHelper.getInstance().getDeviceID())) {
                launchActivity(SortingPackingActivity.newIntent(HomeActivity.this, sortingDevice, enterpriseGUID));
                if (EventBus.getDefault().isRegistered(this)) {
                    EventBus.getDefault().unregister(this);
                }
            }
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }

    }

    private long mExitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                showMessage("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                ActivityManager.getInstance().AppExit(this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
