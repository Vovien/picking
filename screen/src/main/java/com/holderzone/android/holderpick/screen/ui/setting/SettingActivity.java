package com.holderzone.android.holderpick.screen.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.holderzone.android.holderpick.screen.R;
import com.holderzone.android.holderpick.screen.ui.base.presenter.IPresenter;
import com.holderzone.android.holderpick.screen.ui.base.view.activity.BaseActivity;
import com.holderzone.android.holderpick.screen.ui.setting.print.PrintSettingFragment;
import com.holderzone.android.holderpick.screen.ui.setting.version.VersionUpdateFragment;
import com.holderzone.android.holderpick.screen.widget.Title;

import java.util.List;

import butterknife.BindView;

/**
 * 设置页面（包含版本更新和打印机设置）
 *
 * @author www
 * @date 2018/12/4 16:23.
 */

public class SettingActivity extends BaseActivity {
    /**
     * 标题栏
     */
    @BindView(R.id.title)
    Title mTitle;
    /**
     * 组件
     */
    @BindView(R.id.radioGroup_setting)
    RadioGroup mRadioGroupSetting;
    @BindView(R.id.radioButton_update)
    RadioButton mRbUpdate;
    @BindView(R.id.radioButton_print)
    RadioButton mRbPrint;

    /**
     * 版本更新fragment
     */
    private VersionUpdateFragment mVersionUpdateFragment;
    /**
     * 打印设置fragment
     */
    private PrintSettingFragment mPrintSettingFragment;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
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
        return R.layout.activity_setting;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        initTitle();
        initRadioGroup();
    }

    private void initTitle() {
        mTitle.setTitleText("设置");
        mTitle.setOnReturnClickListener(this::finishActivity);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }

    private void initRadioGroup() {
        mRbUpdate.setChecked(true);
        changeFragment(0);
        mRadioGroupSetting.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radioButton_update:
                    //业务设置
                    if (getVisibleFragment() != mVersionUpdateFragment) {
                        changeFragment(0);
                    }
                    mRbUpdate.setChecked(true);
                    break;
                case R.id.radioButton_print:
                    if (getVisibleFragment() != mPrintSettingFragment) {
                        changeFragment(1);
                    }
                    mRbPrint.setChecked(true);
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * 点击item改变显示Fragment
     */
    private void changeFragment(int code) {
        switch (code) {
            case 0:
                //版本更新
                mVersionUpdateFragment = VersionUpdateFragment.getInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_setting, mVersionUpdateFragment)
                        .commit();
                break;
            case 1:
                //打印管理
                mPrintSettingFragment = PrintSettingFragment.getInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_setting, mPrintSettingFragment)
                        .commit();
                break;
            default:
        }
    }

    @Nullable
    @Override
    protected IPresenter initPresenter() {
        return null;
    }

    @Override
    protected void onSubscribe(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onDispose() {

    }

    /**
     * 获取当前显示的Fragment
     */
    private Fragment getVisibleFragment() {
        FragmentManager manager = getSupportFragmentManager();
        List<Fragment> fragmentList = manager.getFragments();
        for (Fragment fragment : fragmentList) {
            if (fragment != null && fragment.isVisible()) {
                return fragment;
            }
        }
        return null;
    }
}
