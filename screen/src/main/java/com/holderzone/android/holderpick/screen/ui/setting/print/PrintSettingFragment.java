package com.holderzone.android.holderpick.screen.ui.setting.print;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.holderzone.android.holderpick.screen.R;
import com.holderzone.android.holderpick.screen.ui.base.view.fragment.BaseFragment;

/**
 * 打印配置页面
 *
 * @author www
 * @date 2018/12/5 10:41.
 */

public class PrintSettingFragment extends BaseFragment<PrintSettingContract.Presenter> implements PrintSettingContract.View {

    public static PrintSettingFragment getInstance() {
        return new PrintSettingFragment();
    }
    @Override
    protected void handleBundleExtras(@NonNull Bundle extras) {

    }

    @Override
    protected void handleSavedInstanceState(@NonNull Bundle savedInstanceState) {

    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_print_setting;
    }

    @Override
    protected PrintSettingContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void onSubscribe(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onDispose() {

    }
}
