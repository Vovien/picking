package com.holderzone.android.holderpick.screen.data.eneity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * class description
 *
 * @author www
 * @date 2018/11/24 17:57.
 */

public class SortingDetail implements Serializable, Parcelable {

    /**
     * sortingDetailID : 分拣明细ID
     * sortingID : 分拣单ID
     * bussinessCode : 分拣单号
     * materialID : 物品ID
     * materialCode : 物品编码
     * materialName : 物品名称
     * customerID : 客户ID
     * customerName : 客户名称
     * sortingMaterialPackageID : 分拣规格ID
     * sortingMaterialPackageName : 分拣规格名称
     * sortingMaterialPackageUnitID : 分拣规格单位
     * sortingMaterialPackageUnitName : 分拣规格名称
     * plannedQuantity : 计划分拣数量
     * quantity : 实际分拣数量
     * packageCode : 装箱编码
     */

    private int sortingDetailID;
    private int sortingID;
    private String bussinessCode;
    private int materialID;
    private String materialCode;
    private String materialName;
    private int customerID;
    private String customerName;
    private int sortingMaterialPackageID;
    private String sortingMaterialPackageName;
    private int sortingMaterialPackageUnitID;
    private String sortingMaterialPackageUnitName;
    private Double plannedQuantity;
    private Double quantity;
    private String packageCode;
    /**
     * 全选状态
     * true = 全部选择
     * false = 部分选择或者未选
     */
    private boolean isCheckedInAll = false;

    public boolean isCheckedInAll() {
        return isCheckedInAll;
    }

    public void setCheckedInAll(boolean checkedInAll) {
        isCheckedInAll = checkedInAll;
    }

    public String getSortingMaterialPackageUnitName() {
        return sortingMaterialPackageUnitName;
    }

    public void setSortingMaterialPackageUnitName(String sortingMaterialPackageUnitName) {
        this.sortingMaterialPackageUnitName = sortingMaterialPackageUnitName;
    }

    public int getSortingDetailID() {
        return sortingDetailID;
    }

    public void setSortingDetailID(int sortingDetailID) {
        this.sortingDetailID = sortingDetailID;
    }

    public int getSortingID() {
        return sortingID;
    }

    public void setSortingID(int sortingID) {
        this.sortingID = sortingID;
    }

    public String getBussinessCode() {
        return bussinessCode;
    }

    public void setBussinessCode(String bussinessCode) {
        this.bussinessCode = bussinessCode;
    }

    public int getMaterialID() {
        return materialID;
    }

    public void setMaterialID(int materialID) {
        this.materialID = materialID;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getSortingMaterialPackageID() {
        return sortingMaterialPackageID;
    }

    public void setSortingMaterialPackageID(int sortingMaterialPackageID) {
        this.sortingMaterialPackageID = sortingMaterialPackageID;
    }

    public String getSortingMaterialPackageName() {
        return sortingMaterialPackageName;
    }

    public void setSortingMaterialPackageName(String sortingMaterialPackageName) {
        this.sortingMaterialPackageName = sortingMaterialPackageName;
    }

    public int getSortingMaterialPackageUnitID() {
        return sortingMaterialPackageUnitID;
    }

    public void setSortingMaterialPackageUnitID(int sortingMaterialPackageUnitID) {
        this.sortingMaterialPackageUnitID = sortingMaterialPackageUnitID;
    }

    public Double getPlannedQuantity() {
        return plannedQuantity;
    }

    public void setPlannedQuantity(Double plannedQuantity) {
        this.plannedQuantity = plannedQuantity;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.sortingDetailID);
        dest.writeInt(this.sortingID);
        dest.writeString(this.bussinessCode);
        dest.writeInt(this.materialID);
        dest.writeString(this.materialCode);
        dest.writeString(this.materialName);
        dest.writeInt(this.customerID);
        dest.writeString(this.customerName);
        dest.writeInt(this.sortingMaterialPackageID);
        dest.writeString(this.sortingMaterialPackageName);
        dest.writeInt(this.sortingMaterialPackageUnitID);
        dest.writeString(this.sortingMaterialPackageUnitName);
        dest.writeValue(this.plannedQuantity);
        dest.writeValue(this.quantity);
        dest.writeString(this.packageCode);
    }

    public SortingDetail() {
    }

    protected SortingDetail(Parcel in) {
        this.sortingDetailID = in.readInt();
        this.sortingID = in.readInt();
        this.bussinessCode = in.readString();
        this.materialID = in.readInt();
        this.materialCode = in.readString();
        this.materialName = in.readString();
        this.customerID = in.readInt();
        this.customerName = in.readString();
        this.sortingMaterialPackageID = in.readInt();
        this.sortingMaterialPackageName = in.readString();
        this.sortingMaterialPackageUnitID = in.readInt();
        this.sortingMaterialPackageUnitName = in.readString();
        this.plannedQuantity = (Double) in.readValue(Double.class.getClassLoader());
        this.quantity = (Double) in.readValue(Double.class.getClassLoader());
        this.packageCode = in.readString();
    }

    public static final Parcelable.Creator<SortingDetail> CREATOR = new Parcelable.Creator<SortingDetail>() {
        @Override
        public SortingDetail createFromParcel(Parcel source) {
            return new SortingDetail(source);
        }

        @Override
        public SortingDetail[] newArray(int size) {
            return new SortingDetail[size];
        }
    };
}
