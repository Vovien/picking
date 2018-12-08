package com.holderzone.android.holderpick.screen.dialog.count;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.holderzone.android.holderpick.screen.R;
import com.holderzone.android.holderpick.screen.dialog.base.BaseDialogFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改对话框
 *
 * @author www
 * @date 2018/11/24 19:23.
 */

public class ChangeCountDialogFragment extends BaseDialogFragment<ChangeCountDialogFragment.ChangeCountDialogListener> {
    /**
     * 修改实拣数
     */
    @BindView(R.id.tv_change_picking)
    TextView mTvChangePicking;
    /**
     * 修改实拣数
     */
    @BindView(R.id.tv_change_packing)
    TextView mTvChangePacking;

    private ChangeCountDialogListener mCountDialogListener;

    public static ChangeCountDialogFragment newInstance() {
        ChangeCountDialogFragment fragment = new ChangeCountDialogFragment();
        return fragment;
    }

    public interface ChangeCountDialogListener {

        /**
         * 分拣
         *
         * @date 2018/11/24 19:34
         */
        void negative();

        /**
         * 装箱
         *
         * @date 2018/11/24 19:34
         */
        void positive();
    }

    @Override
    protected void parseArgumentExtra(Bundle args) {

    }

    @Override
    protected void setAttributesOnceCreate() {
        setStyle(STYLE_NORMAL, R.style.Dialog_NoTitle);
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.dialog_change_count;
    }

    @Override
    protected void setAttributesBefore() {
    }

    @Override
    protected void setAttributesAfter() {
        // 设置可取消
        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(true);
        // 设置宽度为dialog_width*dialog_height，居中显示
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = getResources().getDimensionPixelSize(R.dimen.dialog_count_width);
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);
    }

    @Override
    protected void initView() {

    }


    @OnClick({R.id.tv_change_picking, R.id.tv_change_packing})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_change_picking:
                if (mCountDialogListener != null) {
                    dismiss();
                    mCountDialogListener.negative();
                }

                break;
            case R.id.tv_change_packing:
                if (mCountDialogListener != null) {
                    mCountDialogListener.positive();
                }
                dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void setDialogListener(ChangeCountDialogListener changeCountDialogListener) {
        mCountDialogListener = changeCountDialogListener;
    }

}
