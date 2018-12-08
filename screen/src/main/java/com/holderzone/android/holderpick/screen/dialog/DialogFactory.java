package com.holderzone.android.holderpick.screen.dialog;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.holderzone.android.holderpick.screen.dialog.count.ChangeCountDialogFragment;
import com.holderzone.android.holderpick.screen.dialog.count.CountDialogFragment;
import com.holderzone.android.holderpick.screen.dialog.progress.ProgressDialog;


/**
 * 一个dialog仓库
 * 程序中每个dialog都从这个库里面取
 *
 * @author www
 * @date 2018/11/8 10:26.
 */

public class DialogFactory {
    /**
     * 对话框的tag
     */
    private static final String COUNT_DIALOG = CountDialogFragment.class.getSimpleName();
    private static final String CHANGE_COUNT_DIALOG = CountDialogFragment.class.getSimpleName();
    /**
     * fragment manager
     */
    private FragmentManager mFragmentManager;

    /**
     * progressDialog
     */
    private ProgressDialog mProgressDialog;

    public DialogFactory(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    /**
     * 显示进度对话框
     */
    public void showProgressDialog(Context context) {
        showProgressDialog(context, "加载中");
    }

    /**
     * 显示进度对话框
     */
    public void showProgressDialog(Context context, String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context, msg);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 隐藏进度对话框
     */
    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 修改数量对话框
     */
    public void showCountDialog(String mTitle, String mContent, double count, String mNegative, String mPositive, boolean isHideCotent,boolean isPrint, CountDialogFragment.CountDialogListener listener) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(COUNT_DIALOG);
        if (null != fragment) {
            ft.remove(fragment).commit();
        }
        CountDialogFragment df = CountDialogFragment.newInstance(mTitle, mContent, count, mNegative, mPositive, isHideCotent,isPrint);
        df.setDialogListener(listener);
        df.show(mFragmentManager, COUNT_DIALOG);
        mFragmentManager.executePendingTransactions();
    }

    /**
     * 显示修改数量对话框
     */
    public void showChangeCountDialog(ChangeCountDialogFragment.ChangeCountDialogListener listener) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(CHANGE_COUNT_DIALOG);
        if (null != fragment) {
            ft.remove(fragment).commit();
        }
        ChangeCountDialogFragment df = ChangeCountDialogFragment.newInstance();
        df.setDialogListener(listener);
        df.show(mFragmentManager, CHANGE_COUNT_DIALOG);
        mFragmentManager.executePendingTransactions();
    }
}


