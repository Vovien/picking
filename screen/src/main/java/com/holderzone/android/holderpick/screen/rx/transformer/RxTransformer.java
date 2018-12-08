package com.holderzone.android.holderpick.screen.rx.transformer;

import com.holderzone.android.holderpick.screen.ui.base.view.IView;
import com.holderzone.android.holderpick.screen.ui.base.view.activity.BaseActivity;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava生命周期管理，界面进度条管理，线程切换之直接辅助类
 *
 * @author www
 * @date 2018/11/9 17:20.
 */

public class RxTransformer {


    /**
     * rx主进程异步加载进度条
     *
     * @param view
     * @return <T>
     * @date 2018/11/13 9:18
     */
    public static <T> ObservableTransformer<T, T> applyTransformer(IView view) {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> view.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnDispose(view::onDispose)
                .compose(RxTransformer.<T>bindUntilEventDestroy(view))
                .doOnTerminate(view::hideLoading);
    }

    public static <T> LifecycleTransformer<T> bindUntilEventDestroy(IView view) {
        if (view instanceof BaseActivity) {
            return ((BaseActivity) view).<T>bindUntilEvent(ActivityEvent.DESTROY);
        } else {
            throw new IllegalArgumentException("view isn't activity or fragment");
        }
    }


    /**
     * rx主进程异步带参数加载进度条
     *
     * @param view        视图
     * @param showLoading 显示对话框
     * @param hideLoading 隐藏对话框
     * @return <T>
     * @date 2018/11/13 9:19
     */
    public static <T> ObservableTransformer<T, T> applyTransformer(IView view, boolean showLoading, boolean hideLoading) {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (showLoading) {
                        view.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnDispose(view::onDispose)
                .compose(RxTransformer.<T>bindUntilEventDestroy(view))
                .doOnTerminate(() -> {
                    if (hideLoading) {
                        view.hideLoading();
                    }
                });
    }

    public static <T> LifecycleTransformer<T> bindToLifecycle(IView view) {
        if (view instanceof BaseActivity) {
            return ((BaseActivity) view).<T>bindToLifecycle();
        } else {
            throw new IllegalArgumentException("view isn't activity or fragment");
        }
    }

}
