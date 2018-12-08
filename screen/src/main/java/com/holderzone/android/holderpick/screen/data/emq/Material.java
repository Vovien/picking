package com.holderzone.android.holderpick.screen.data.emq;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * class description
 *
 * @author www
 * @date 2018/11/24 16:46.
 */

public class Material implements Serializable, Parcelable {

    /**
     * CustomerID : 客户ID
     * MaterialName : 物品名称
     * PlannedQuantity : 应拣数量
     * SortingMaterialPackageUnitID :规格单位ID
     * sortingMaterialPackageUnitName : 规格单位名称
     * SortingMaterialPackageID : 规格ID
     * SortingMaterialPackageName : 规格Name
     */

    private int CustomerID;
    private String MaterialName;
    private Double PlannedQuantity;
    private int SortingMaterialPackageUnitID;
    private int SortingMaterialPackageID;
    private String SortingMaterialPackageUnitName;
    private String SortingMaterialPackageName;

    public String getSortingMaterialPackageName() {
        return SortingMaterialPackageName;
    }

    public void setSortingMaterialPackageName(String sortingMaterialPackageName) {
        SortingMaterialPackageName = sortingMaterialPackageName;
    }

    public String getSortingMaterialPackageUnitName() {
        return SortingMaterialPackageUnitName;
    }

    public void setSortingMaterialPackageUnitName(String sortingMaterialPackageUnitName) {
        SortingMaterialPackageUnitName = sortingMaterialPackageUnitName;
    }

    public Double getPlannedQuantity() {
        return PlannedQuantity;
    }

    public void setPlannedQuantity(Double plannedQuantity) {
        PlannedQuantity = plannedQuantity;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int CustomerID) {
        this.CustomerID = CustomerID;
    }

    public String getMaterialName() {
        return MaterialName;
    }

    public void setMaterialName(String MaterialName) {
        this.MaterialName = MaterialName;
    }

    public int getSortingMaterialPackageUnitID() {
        return SortingMaterialPackageUnitID;
    }

    public void setSortingMaterialPackageUnitID(int SortingMaterialPackageUnitID) {
        this.SortingMaterialPackageUnitID = SortingMaterialPackageUnitID;
    }

    public int getSortingMaterialPackageID() {
        return SortingMaterialPackageID;
    }

    public void setSortingMaterialPackageID(int SortingMaterialPackageID) {
        this.SortingMaterialPackageID = SortingMaterialPackageID;
    }

    public Material() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.CustomerID);
        dest.writeString(this.MaterialName);
        dest.writeValue(this.PlannedQuantity);
        dest.writeInt(this.SortingMaterialPackageUnitID);
        dest.writeInt(this.SortingMaterialPackageID);
        dest.writeString(this.SortingMaterialPackageUnitName);
        dest.writeString(this.SortingMaterialPackageName);
    }

    protected Material(Parcel in) {
        this.CustomerID = in.readInt();
        this.MaterialName = in.readString();
        this.PlannedQuantity = (Double) in.readValue(Double.class.getClassLoader());
        this.SortingMaterialPackageUnitID = in.readInt();
        this.SortingMaterialPackageID = in.readInt();
        this.SortingMaterialPackageUnitName = in.readString();
        this.SortingMaterialPackageName = in.readString();
    }

    public static final Creator<Material> CREATOR = new Creator<Material>() {
        @Override
        public Material createFromParcel(Parcel source) {
            return new Material(source);
        }

        @Override
        public Material[] newArray(int size) {
            return new Material[size];
        }
    };
}
