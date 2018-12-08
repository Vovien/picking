package com.holderzone.android.holderpick.screen.dialog.count;

import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.holderzone.android.holderpick.screen.R;
import com.holderzone.android.holderpick.screen.dialog.base.BaseDialogFragment;
import com.holderzone.android.holderpick.screen.util.InputFilterMinMax;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改数量对话框（复用度较低）
 *
 * @author www
 * @date 2018/11/14 17:21.
 */

public class CountDialogFragment extends BaseDialogFragment<CountDialogFragment.CountDialogListener> {
    private static final String TITLE = "TITLE";
    private static final String CONTENT = "CONTENT";
    private static final String COUNT = "COUNT";
    private static final String NEGATIVE = "NEGATIVE";
    private static final String POSITIVE = "POSITIVE";
    private static final String HIDE = "HIDE";
    private static final String PRINT = "PRINT";

    private CountDialogListener mCountDialogListener;
    /**
     * 标题
     */
    @BindView(R.id.tv_dialog_count_title)
    TextView mTvTitle;
    /**
     * 内容
     */
    @BindView(R.id.tv_dialog_count_content)
    TextView mTvContent;
    /**
     * button1
     */
    @BindView(R.id.tv_dialog_count_negative)
    Button mBtnCancel;
    /**
     * button2
     */
    @BindView(R.id.tv_dialog_count_positive)
    Button mBtnConfirm;
    /**
     * 数量
     */
    @BindView(R.id.et_dialog_count)
    EditText mEtCount;

    /**
     * 相关参数
     */
    private String title, content, btnNegative, btnPositive;
    private double count;

    private boolean isHideUnit, isPrint;

    public interface CountDialogListener {

        /**
         * positive
         *
         * @param count
         * @date 2018/11/14 17:30
         */
        void positive(double count);
    }

    public static CountDialogFragment newInstance(String mTitle, String mContent, double count, String mNegative, String mPositive, boolean isHide, boolean isPrint) {
        CountDialogFragment fragment = new CountDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, mTitle);
        bundle.putString(CONTENT, mContent);
        bundle.putDouble(COUNT, count);
        bundle.putString(NEGATIVE, mNegative);
        bundle.putString(POSITIVE, mPositive);
        bundle.putBoolean(HIDE, isHide);
        bundle.putBoolean(PRINT, isPrint);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void parseArgumentExtra(Bundle args) {
        title = args.getString(TITLE);
        content = args.getString(CONTENT);
        count = args.getDouble(COUNT, 0);
        btnNegative = args.getString(NEGATIVE);
        btnPositive = args.getString(POSITIVE);
        isHideUnit = args.getBoolean(HIDE);
        isPrint = args.getBoolean(PRINT);
    }

    @Override
    protected void setAttributesOnceCreate() {
        setStyle(STYLE_NORMAL, R.style.Dialog_NoTitle);
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.dialog_fragment_count;
    }

    @Override
    protected void setAttributesBefore() {

    }

    @Override
    protected void setAttributesAfter() {
        // 设置不可取消
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
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
        if (isPrint) {
            mEtCount.setText(String.valueOf((int) count));
            mEtCount.setKeyListener(DigitsKeyListener.getInstance("123456789"));
        } else {
            mEtCount.setText(String.valueOf(count));
        }
        mTvTitle.setText(title);
        mTvContent.setText(content);
        mBtnCancel.setText(btnNegative);
        mBtnConfirm.setText(btnPositive);
        if (isHideUnit) {
            mTvContent.setVisibility(View.GONE);
        } else {
            InputFilterMinMax.setRegion(mEtCount, 0, count);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void setDialogListener(CountDialogListener countDialogListener) {
        mCountDialogListener = countDialogListener;
    }


    @OnClick({R.id.tv_dialog_count_negative, R.id.tv_dialog_count_positive, R.id.et_dialog_count, R.id.iv_dialog_count_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_dialog_count:

                break;
            case R.id.iv_dialog_count_clear:
                mEtCount.setText("");
                break;
            case R.id.tv_dialog_count_negative:
                if (mCountDialogListener != null) {
                    dismiss();
                }
                break;
            case R.id.tv_dialog_count_positive:
                if (mCountDialogListener != null) {
                    try {
                        mCountDialogListener.positive(Double.valueOf(mEtCount.getText().toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dismiss();
                }
                break;
            default:
                break;
        }
    }

}
