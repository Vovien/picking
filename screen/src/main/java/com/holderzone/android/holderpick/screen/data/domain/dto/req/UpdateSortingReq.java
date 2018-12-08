package com.holderzone.android.holderpick.screen.data.domain.dto.req;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 更新分拣信息
 *
 * @author www
 * @date 2018/11/26 11:58.
 */

public class UpdateSortingReq implements Serializable, Parcelable {

    public double getOldQuantity() {
        return oldQuantity;
    }

    public void setOldQuantity(double oldQuantity) {
        this.oldQuantity = oldQuantity;
    }

    public double getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(double newQuantity) {
        this.newQuantity = newQuantity;
    }

    /**
     * sortingID : 分拣单ID
     * customerID : 客户ID
     * sortingMaterialPackageID : 分拣规格ID
     * oldQuantity : 分拣数量(修改前)
     * newQuantity : 分拣数量(修改后)
     */

    private int sortingID;
    private int customerID;
    private int sortingMaterialPackageID;
    private double oldQuantity;
    private double newQuantity;

    public int getSortingID() {
        return sortingID;
    }

    public void setSortingID(int sortingID) {
        this.sortingID = sortingID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getSortingMaterialPackageID() {
        return sortingMaterialPackageID;
    }

    public void setSortingMaterialPackageID(int sortingMaterialPackageID) {
        this.sortingMaterialPackageID = sortingMaterialPackageID;
    }


    public UpdateSortingReq() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.sortingID);
        dest.writeInt(this.customerID);
        dest.writeInt(this.sortingMaterialPackageID);
        dest.writeDouble(this.oldQuantity);
        dest.writeDouble(this.newQuantity);
    }

    protected UpdateSortingReq(Parcel in) {
        this.sortingID = in.readInt();
        this.customerID = in.readInt();
        this.sortingMaterialPackageID = in.readInt();
        this.oldQuantity = in.readDouble();
        this.newQuantity = in.readDouble();
    }

    public static final Creator<UpdateSortingReq> CREATOR = new Creator<UpdateSortingReq>() {
        @Override
        public UpdateSortingReq createFromParcel(Parcel source) {
            return new UpdateSortingReq(source);
        }

        @Override
        public UpdateSortingReq[] newArray(int size) {
            return new UpdateSortingReq[size];
        }
    };
}
