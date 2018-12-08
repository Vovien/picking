package com.holderzone.android.holderpick.screen.ui.base.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.holderzone.android.holderpick.screen.App;
import com.holderzone.android.holderpick.screen.dialog.DialogFactory;
import com.holderzone.android.holderpick.screen.ui.base.presenter.IPresenter;
import com.holderzone.android.holderpick.screen.ui.base.view.IView;
import com.holderzone.android.holderpick.screen.util.manager.ActivityManager;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 这是App的基类activity、用于抽象相关方法
 *
 * @author www
 * @date 2018/11/8 10:04.
 */

public abstract class BaseActivity<P extends IPresenter> extends RxAppCompatActivity implements IView {
    /**
     * presenter
     */
    protected P mPresenter;

    /**
     * unBinder
     */
    private Unbinder mBinder;

    /**
     * dialogFactory
     */
    protected DialogFactory mDialogFactory;

    /**
     * instanceState
     */
    private Bundle mSavedInstanceState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(this);
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            handleBundleExtras(extras);
        }
        if (null != savedInstanceState) {
            handleSavedInstanceState(savedInstanceState);
        }
        setContentView(getContentViewLayoutId());
        // ButterKnife绑定
        mBinder = ButterKnife.bind(this);
        // 初始化DialogFactory
        mDialogFactory = new DialogFactory(getSupportFragmentManager());
        // 初始化presenter
        mPresenter = initPresenter();
        // 初始化视图，注册事件
        initView(savedInstanceState);
        // 加载数据
        initData(savedInstanceState);
        // 状态赋值，供onSubscribe使用
        mSavedInstanceState = savedInstanceState;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // onResume后才初始化的数据
        onSubscribe(mSavedInstanceState);
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 从activity管理栈移除
        ActivityManager.getInstance().removeActivity(this);

        // ButterKnife解除视图绑定
        if (mBinder != null && mBinder != Unbinder.EMPTY) {
            mBinder.unbind();
        }
        mBinder = null;

        // 控制presenter层释放资源，并清除对presenter层的引用
        if (mPresenter != null) {
            mPresenter.onDestroy();//释放资源
        }
        mPresenter = null;
    }

    /**
     * 处理Extra数据
     *
     * @param extras
     * @date 2018/11/8 10:58
     */
    protected abstract void handleBundleExtras(@NonNull Bundle extras);

    /**
     * 保存Extra数据传回
     *
     * @param savedInstanceState
     * @date 2018/11/8 10:58
     */
    protected abstract void handleSavedInstanceState(@NonNull Bundle savedInstanceState);

    /**
     * 设置内容view
     *
     * @return int
     * @date 2018/11/8 11:00
     */
    @LayoutRes
    protected abstract int getContentViewLayoutId();

    /**
     * 初始化控件
     *
     * @param savedInstanceState
     * @date 2018/11/8 11:02
     */
    protected abstract void initView(@Nullable Bundle savedInstanceState);

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     * @date 2018/11/8 11:03
     */
    protected abstract void initData(@Nullable Bundle savedInstanceState);

    /**
     * 初始化presenter
     *
     * @return P
     * @date 2018/11/8 11:03
     */
    @Nullable
    protected abstract P initPresenter();

    /**
     * activity恢复之后的操作
     *
     * @param savedInstanceState
     * @date 2018/11/8 11:04
     */
    protected abstract void onSubscribe(@Nullable Bundle savedInstanceState);

    @Override
    public void showLoading() {
        mDialogFactory.showProgressDialog(this);
    }

    @Override
    public void hideLoading() {
        mDialogFactory.dismissProgressDialog();
    }

    @Override
    public void showMessage(String message) {
        App.showMessage(message);
    }

    @Override
    public void launchActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    /**
     * 通过activity得到dialogFactory去使用
     *
     * @return DialogFactory
     */
    public DialogFactory getDialogFactory() {
        return mDialogFactory;
    }
}
