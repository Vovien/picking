package com.holderzone.android.holderpick.screen.ui.setting.version;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.holderzone.android.holderpick.screen.R;
import com.holderzone.android.holderpick.screen.ui.base.view.fragment.BaseFragment;

/**
 * 版本更新
 *
 * @author www
 * @date 2018/12/4 18:14.
 */

public class VersionUpdateFragment extends BaseFragment<VersionUpdateContract.Presenter> implements VersionUpdateContract.View {

    public static VersionUpdateFragment getInstance() {
        return new VersionUpdateFragment();
    }

    @Override
    protected void handleBundleExtras(@NonNull Bundle extras) {

    }

    @Override
    protected void handleSavedInstanceState(@NonNull Bundle savedInstanceState) {

    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_version;
    }

    @Override
    protected VersionUpdateContract.Presenter initPresenter() {
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
