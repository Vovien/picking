package com.holderzone.android.holderpick.screen.ui.base.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.holderzone.android.holderpick.screen.App;
import com.holderzone.android.holderpick.screen.dialog.DialogFactory;
import com.holderzone.android.holderpick.screen.ui.base.presenter.IPresenter;
import com.holderzone.android.holderpick.screen.ui.base.view.IView;
import com.holderzone.android.holderpick.screen.ui.base.view.activity.BaseActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 这是App的基类fragment、用于抽象相关方法
 *
 * @author www
 * @date 2018/11/15 16:20.
 */

public abstract class BaseFragment<P extends IPresenter> extends RxFragment implements IView {
    /**
     * presenter
     */
    protected P mPresenter;

    /**
     * mBaseActivity
     */
    protected BaseActivity mBaseActivity;

    /**
     * unBinder
     */
    private Unbinder mBinder;

    /**
     * dialogFactory
     */
    protected DialogFactory mDialogFactory;

    /**
     * mSavedInstanceState
     */
    private Bundle mSavedInstanceState;

    @CallSuper
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof BaseActivity) {
            mBaseActivity = (BaseActivity) activity;
        }
    }

    @CallSuper
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        if (null != extras) {
            handleBundleExtras(extras);
        }
        if (savedInstanceState != null) {
            handleSavedInstanceState(savedInstanceState);
        }
    }

    @CallSuper
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        if (getContentViewLayoutId() != 0) {
            view = inflater.inflate(getContentViewLayoutId(), container, false);
        } else {
            view = super.onCreateView(inflater, container, savedInstanceState);
        }
        // ButterKnife绑定
        mBinder = ButterKnife.bind(this, view);
        return view;
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 初始化presenter
        mPresenter = initPresenter();
    }

    @CallSuper
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 初始化DialogFactory
        mDialogFactory = new DialogFactory(getChildFragmentManager());

        // 初始化视图，注册事件
        initView(savedInstanceState);

        // 初始化数据
        initData(savedInstanceState);

        // 状态赋值，供onSubscribe使用
        mSavedInstanceState = savedInstanceState;
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
     * 初始化presenter
     *
     * @return P
     * @date 2018/11/8 11:03
     */
    protected abstract P initPresenter();

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
     * activity恢复之后的操作
     *
     * @param savedInstanceState
     * @date 2018/11/8 11:04
     */
    protected abstract void onSubscribe(@Nullable Bundle savedInstanceState);

    @Override
    public void onResume() {
        super.onResume();
        // onResume后才初始化的数据
        onSubscribe(mSavedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @CallSuper
    @Override
    public void onDetach() {
        super.onDetach();
        mBaseActivity = null;
    }

    @CallSuper
    @Override
    public void onDestroy() {
        super.onDestroy();

        // ButterKnife解除视图绑定
        if (mBinder != null && mBinder != Unbinder.EMPTY) {
            mBinder.unbind();
        }
        mBinder = null;

        // 控制presenter层释放资源，并清除对presenter层的引用
        if (mPresenter != null) {
            mPresenter.onDestroy();//释放资源
            mPresenter = null;
        }
    }

    @Override
    public void launchActivity(Intent intent) {
        if (mBaseActivity != null) {
            mBaseActivity.startActivity(intent);
        } else {
            throw new NullPointerException("mBaseActivity为null");
        }
    }

    @Override
    public void finishActivity() {
        if (mBaseActivity != null) {
            mBaseActivity.finish();
        } else {
            throw new NullPointerException("mBaseActivity为null");
        }
    }

    /**
     * 得到dialogFactory以显示对话框
     *
     * @return
     */
    public DialogFactory getDialogFactory() {
        return mDialogFactory;
    }

    @Override
    public void showLoading() {
        mDialogFactory.showProgressDialog(mBaseActivity);
    }

    @Override
    public void hideLoading() {
        mDialogFactory.dismissProgressDialog();
    }

    @Override
    public void showMessage(String message) {
        App.showMessage(message);
    }
}
