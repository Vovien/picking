package com.holderzone.android.holderpick.screen.ui.packing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SpanUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.holderzone.android.holderpick.screen.R;
import com.holderzone.android.holderpick.screen.adapter.base.RecycleHolder;
import com.holderzone.android.holderpick.screen.adapter.base.RecyclerAdapter;
import com.holderzone.android.holderpick.screen.data.domain.dto.req.UpdateSortingReq;
import com.holderzone.android.holderpick.screen.data.emq.Material;
import com.holderzone.android.holderpick.screen.data.emq.SortingDevice;
import com.holderzone.android.holderpick.screen.data.eneity.SortingDetail;
import com.holderzone.android.holderpick.screen.dialog.count.ChangeCountDialogFragment;
import com.holderzone.android.holderpick.screen.dialog.count.CountDialogFragment;
import com.holderzone.android.holderpick.screen.rx.mqtt.MqttManager;
import com.holderzone.android.holderpick.screen.ui.base.view.activity.BaseActivity;
import com.holderzone.android.holderpick.screen.util.ArithUtil;
import com.holderzone.android.holderpick.screen.util.ToastUtil;
import com.holderzone.android.holderpick.screen.util.helper.DeviceHelper;
import com.holderzone.android.holderpick.screen.util.manager.ActivityManager;
import com.holderzone.android.holderpick.screen.widget.Title;
import com.jakewharton.rxbinding2.view.RxView;
import com.kennyc.view.MultiStateView;
import com.orhanobut.logger.Logger;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 分拣中
 *
 * @author www
 * @date 2018/11/19 17:10.
 */

public class SortingPackingActivity extends BaseActivity<SortingPackingContract.Presenter> implements SortingPackingContract.View {
    /**
     * 标题栏
     */
    @BindView(R.id.title)
    Title mTitle;
    /**
     * 打印装箱码
     */
    @BindView(R.id.btn_print_packCode)
    Button mBtnPrintPackCode;
    /**
     * 设置装箱数量
     */
    @BindView(R.id.btn_setting_print_number)
    Button mBtnSettingPrintNumber;
    /**
     * 部分完成
     */
    @BindView(R.id.btn_part_complete)
    Button mBtnPartComplete;
    /**
     * 全部完成
     */
    @BindView(R.id.btn_all_complete)
    Button mBtnAllComplete;
    /**
     * 应拣提示
     */
    @BindView(R.id.tv_scan_to_picking)
    TextView mTvScanToPicking;
    /**
     * 应拣内容
     */
    @BindView(R.id.ll_should_picking)
    LinearLayout mLlShouldPicking;
    /**
     * 装箱列表
     */
    @BindView(R.id.rv_no_packing)
    RecyclerView mRvNoPacking;
    /**
     * 物品名称
     */
    @BindView(R.id.tv_goods_name)
    TextView mTvGoodsName;
    /**
     * 物品单位
     */
    @BindView(R.id.tv_goods_unit)
    TextView mTvGoodsUnit;
    /**
     * 物品数量
     */
    @BindView(R.id.tv_goods_count)
    TextView mTvGoodsCount;
    /**
     * 物品数量
     */
    @BindView(R.id.iv_goods_clear)
    ImageView mIvGoodsClear;
    /**
     * 超拣
     */
    @BindView(R.id.ll_goods_pass)
    LinearLayout mLlGoodsPass;
    @BindView(R.id.msv_packing)
    MultiStateView msvPacking;
    /**
     * 全选
     */
    @BindView(R.id.iv_all_select)
    ImageView mIvAllSelect;

    /**
     * 全选状态
     * true = 全部选择
     * false = 部分选择或者未选
     */
    boolean isSelectedAll = true;

    /**
     * adapter
     */
    private RecyclerAdapter<SortingDetail> mNoPackingRecyclerAdapter;

    /**
     * 模拟数据
     */
    private List<SortingDetail> mData = new ArrayList<>();
    private double printNum = 1;
    SortingDevice sortingDevice;
    Material mMaterial;
    double firstCount;
    String enterpriseGUID;

    private static final String SORTING_DEVICE = "SORTING_DEVICE";
    private static final String ENTERPRISE_GUID = "ENTERPRISE_GUID";

    public static Intent newIntent(Context context, SortingDevice sortingDevice, String enterpriseGUID) {
        Intent intent = new Intent(context, SortingPackingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(SORTING_DEVICE, sortingDevice);
        bundle.putString(ENTERPRISE_GUID, enterpriseGUID);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void handleBundleExtras(@NonNull Bundle extras) {
        sortingDevice = (SortingDevice) extras.getSerializable(SORTING_DEVICE);
        enterpriseGUID = extras.getString(ENTERPRISE_GUID);
    }

    @Override
    protected void handleSavedInstanceState(@NonNull Bundle savedInstanceState) {

    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_sorting_packing;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        initTitle();
        initList();
        initEvent();
        initTopic();
        checkSelect();
    }

    private void initTitle() {
        mTitle.setTitleText(sortingDevice.getCustomerName());
        mTitle.setMenuText("已装箱");
        mTitle.setOnMenuClickListener(() -> showMessage("正在开发中…"));
        mBtnSettingPrintNumber.setText(getString(R.string.setting_print_number, ArithUtil.stripTrailingZeros(printNum)));
        initEmptyText();
    }

    /**
     * 设置应拣提示颜色与点击事件处理
     *
     * @date 2018/11/20 10:48
     */
    @SuppressLint("CheckResult")
    private void initEmptyText() {
        RxView.clicks(mIvAllSelect).throttleFirst(0, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    if (isSelectedAll) {
                        for (SortingDetail sortingDetail : mData) {
                            sortingDetail.setCheckedInAll(false);
                        }
                        isSelectedAll = false;
                    } else {
                        for (SortingDetail sortingDetail : mData) {
                            sortingDetail.setCheckedInAll(true);
                        }
                        isSelectedAll = true;
                    }
                    mIvAllSelect.setImageResource(isSelectedAll ? R.mipmap.iv_select_press : R.mipmap.iv_select);

                    if (mNoPackingRecyclerAdapter != null) {
                        mNoPackingRecyclerAdapter.notifyDataSetChanged();
                    }
                });

        SpannableString spannableString = new SpannableString(getString(R.string.scan_to_picking));
        spannableString.setSpan(new ForegroundColorSpan
                        (Color.parseColor("#0078d7")), 1, 9,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvScanToPicking.setText(spannableString);
    }

    private void initEvent() {
        EventBus.getDefault().register(this);
    }

    private void initTopic() {
        //订阅扫描topic
        new Thread(() -> MqttManager.getInstance().subscribe("Topic/" +
                enterpriseGUID +
                "/Scan/" + sortingDevice.getSortingID() + "/Material", 2)).start();

        //订阅关闭topic
        new Thread(() -> MqttManager.getInstance().subscribe("Topic/" +
                enterpriseGUID +
                "/Pad/" + DeviceHelper.getInstance().getDeviceID() + "/Release", 2)).start();
    }

    @OnClick({R.id.btn_retry, R.id.btn_part_complete, R.id.btn_all_complete, R.id.iv_goods_clear, R.id.btn_setting_print_number})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_retry:
                mPresenter.requestPackingList(sortingDevice.getSortingID());
                break;
            case R.id.iv_goods_clear:
                mTvGoodsCount.setText("0.0");
                mBtnPartComplete.setEnabled(false);
                mBtnAllComplete.setEnabled(false);
                break;
            case R.id.btn_part_complete:
                mDialogFactory.showCountDialog(sortingDevice.getCustomerName(), "单位：把", Double.valueOf(mTvGoodsCount.getText().toString()), "取消", "提交", false, false, new CountDialogFragment.CountDialogListener() {
                    @Override
                    public void positive(double count) {
                        mPresenter.addSorting(sortingDevice, mMaterial, count);
                    }
                });
                break;
            case R.id.btn_all_complete:
                mPresenter.addSorting(sortingDevice, mMaterial, Double.parseDouble(mTvGoodsCount.getText().toString()));
                break;
            case R.id.btn_setting_print_number:
                mDialogFactory.showCountDialog(getString(R.string.packing_print_number), null, printNum, "取消", "提交", true, true, count -> {
                    mBtnSettingPrintNumber.setText(getString(R.string.setting_print_number, ArithUtil.stripTrailingZeros(count)));
                    printNum = count;
                });
                break;
            default:
                break;
        }
    }

    /**
     * 初始化装箱数据列表
     *
     * @date 2018/11/20 15:52
     */
    private void initList() {
        mNoPackingRecyclerAdapter = new RecyclerAdapter<SortingDetail>(this, mData, R.layout.item_no_packing) {
            @Override
            public void convert(RecycleHolder holder, SortingDetail data, int position) {
                if (data.isCheckedInAll()) {
                    holder.setImageResource(R.id.iv_select, R.mipmap.iv_select_press);
                } else {
                    holder.setImageResource(R.id.iv_select, R.mipmap.iv_select);
                }
                holder.setText(R.id.tv_packing_goods, data.getMaterialName());
                SpanUtils spanUtils = new SpanUtils();
                spanUtils.append(String.valueOf(data.getQuantity())).setUnderline();
                ((TextView) holder.findView(R.id.tv_packing_count)).setText(spanUtils.create());
                holder.setText(R.id.tv_packing_count_unit, getString(R.string.sorting_count_unit, data.getSortingMaterialPackageUnitName()));
                holder.setText(R.id.tv_packing_goods_unit, data.getSortingMaterialPackageName());
                holder.setOnClickListener(R.id.ll_packing_right, o -> {
                    mDialogFactory.showChangeCountDialog(new ChangeCountDialogFragment.ChangeCountDialogListener() {
                        @Override
                        public void negative() {
                            mDialogFactory.showCountDialog(getString(R.string.input_packing_number), null, mData.get(position).getQuantity(), "取消", "提交", true, false, new CountDialogFragment.CountDialogListener() {
                                @Override
                                public void positive(double count) {
                                    UpdateSortingReq updateSortingReq = new UpdateSortingReq();
                                    updateSortingReq.setSortingID(mData.get(position).getSortingID());
                                    updateSortingReq.setCustomerID(mData.get(position).getCustomerID());
                                    updateSortingReq.setSortingMaterialPackageID(mData.get(position).getSortingMaterialPackageID());
                                    updateSortingReq.setOldQuantity(mData.get(position).getQuantity());
                                    updateSortingReq.setNewQuantity(count);
                                    mPresenter.updateSorting(updateSortingReq);
                                }
                            });
                        }

                        @Override
                        public void positive() {
                            showMessage("正在开发中…");
                        }
                    });

                });
                holder.setOnClickListener(R.id.iv_select, o -> {
                    data.setCheckedInAll(!data.isCheckedInAll());
                    checkSelect();
                    mNoPackingRecyclerAdapter.notifyDataSetChanged();
                });

            }
        };
        mRvNoPacking.setAdapter(mNoPackingRecyclerAdapter);
        mRvNoPacking.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.requestPackingList(sortingDevice.getSortingID());
    }

    /**
     * 判断是否全部选中
     */
    private void checkSelect() {
        Set<Boolean> booleanSet = new HashSet<>();
        for (SortingDetail sortingDetail : mData) {
            booleanSet.add(sortingDetail.isCheckedInAll());
            if (sortingDetail.isCheckedInAll()) {
            }
        }
        isSelectedAll = booleanSet.size() == 1 && booleanSet.contains(true);
        mIvAllSelect.setImageResource(isSelectedAll ? R.mipmap.iv_select_press : R.mipmap.iv_select);

    }

    @Nullable
    @Override
    protected SortingPackingContract.Presenter initPresenter() {
        return new SortingPackingPresenter(this);
    }

    @Override
    protected void onSubscribe(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onDispose() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MqttMessage message) {
        try {
            if ("Release".equals(message.toString())) {
                showMessage("解绑成功");
                finish();
                if (EventBus.getDefault().isRegistered(this)) {
                    EventBus.getDefault().unregister(this);
                }
                return;
            }
            Gson gson = new Gson();
            List<Material> materialList = gson.fromJson(message.toString(), new TypeToken<List<Material>>() {
            }.getType());
            for (Material material : materialList) {
                if (sortingDevice.getCustomerID() == material.getCustomerID()) {
                    mLlShouldPicking.setVisibility(View.VISIBLE);
                    mTvScanToPicking.setVisibility(View.GONE);
                    mLlGoodsPass.setVisibility(View.GONE);
                    mTvGoodsUnit.setText(getString(R.string.sorting_count_unit, material.getSortingMaterialPackageUnitName()));
                    if (material.getPlannedQuantity() > 0) {
                        mBtnPartComplete.setEnabled(true);
                        mBtnAllComplete.setEnabled(true);
                        mIvGoodsClear.setEnabled(true);
                        mLlGoodsPass.setVisibility(View.GONE);
                        mTvGoodsCount.setTextColor(ContextCompat.getColor(this, R.color.tv_should_packing_number));
                    } else if (material.getPlannedQuantity() == 0) {
                        mBtnPartComplete.setEnabled(false);
                        mBtnAllComplete.setEnabled(false);
                        mIvGoodsClear.setEnabled(false);
                        mLlGoodsPass.setVisibility(View.GONE);
                        mTvGoodsCount.setTextColor(ContextCompat.getColor(this, R.color.tv_should_packing_number));
                    }
                    if (material.getPlannedQuantity() < 0) {
                        mLlGoodsPass.setVisibility(View.VISIBLE);
                        mTvGoodsCount.setText(String.valueOf(Math.abs(material.getPlannedQuantity())));
                        mTvGoodsCount.setTextColor(ContextCompat.getColor(this, R.color.tv_text_pass));
                        mBtnPartComplete.setEnabled(false);
                        mBtnAllComplete.setEnabled(false);
                        mIvGoodsClear.setEnabled(false);
                    } else {
                        mTvGoodsCount.setText(String.valueOf(material.getPlannedQuantity()));
                    }

                    firstCount = material.getPlannedQuantity();
                    mTvGoodsName.setText(material.getMaterialName());
                    mMaterial = material;
                }
            }
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }

    }

    @Override
    public void padSortingSuccess(SortingDetail sortingDetail) {
        showMessage("已移至待装箱列表");
        firstCount = firstCount - sortingDetail.getQuantity();
        mPresenter.requestPackingList(sortingDevice.getSortingID());
    }

    @Override
    public void updateSortingSuccess() {
        ToastUtil toastUtil = new ToastUtil(getApplicationContext(), R.layout.dialog_change_success, "修改成功");
        toastUtil.show();
        mPresenter.requestPackingList(sortingDevice.getSortingID());
    }

    @Override
    public void updateSortingFail() {
        ToastUtil toastUtil = new ToastUtil(getApplicationContext(), R.layout.dialog_change_success, "修改失败");
        toastUtil.show();
    }

    @Override
    public void getSortingList(List<SortingDetail> detailList) {
        if (detailList.size() != 0) {
            mBtnPrintPackCode.setEnabled(true);
            mBtnSettingPrintNumber.setEnabled(true);
            msvPacking.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            mData.clear();
            for (SortingDetail sortingDetail : detailList) {
                if (sortingDevice.getCustomerID() == sortingDetail.getCustomerID()) {
                    mData.add(sortingDetail);
                    if (mMaterial != null && sortingDetail.getSortingMaterialPackageID() == mMaterial.getSortingMaterialPackageID()) {
                        double pickCount = ArithUtil.sub(sortingDetail.getPlannedQuantity(), sortingDetail.getQuantity());
                        if (pickCount > 0) {
                            mBtnPartComplete.setEnabled(true);
                            mBtnAllComplete.setEnabled(true);
                            mIvGoodsClear.setEnabled(true);
                            mLlGoodsPass.setVisibility(View.GONE);
                            mTvGoodsCount.setTextColor(ContextCompat.getColor(this, R.color.tv_should_packing_number));
                        } else if (pickCount == 0) {
                            mBtnPartComplete.setEnabled(false);
                            mBtnAllComplete.setEnabled(false);
                            mIvGoodsClear.setEnabled(false);
                            mLlGoodsPass.setVisibility(View.GONE);
                            mTvGoodsCount.setTextColor(ContextCompat.getColor(this, R.color.tv_should_packing_number));
                        }
                        if (pickCount < 0) {
                            mLlGoodsPass.setVisibility(View.VISIBLE);
                            mTvGoodsCount.setText(String.valueOf(Math.abs(pickCount)));
                            mTvGoodsCount.setTextColor(ContextCompat.getColor(this, R.color.tv_text_pass));
                            mBtnPartComplete.setEnabled(false);
                            mBtnAllComplete.setEnabled(false);
                            mIvGoodsClear.setEnabled(false);
                        } else {
                            mTvGoodsCount.setText(String.valueOf(pickCount));
                        }
                    }
                }
            }
            mNoPackingRecyclerAdapter.notifyDataSetChanged();
        } else {
            mBtnPrintPackCode.setEnabled(false);
            mBtnSettingPrintNumber.setEnabled(false);
            msvPacking.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    @Override
    public void getSortingListFail() {
        msvPacking.setViewState(MultiStateView.VIEW_STATE_ERROR);
        mBtnPrintPackCode.setEnabled(false);
        mBtnSettingPrintNumber.setEnabled(false);
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
