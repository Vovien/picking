package com.holderzone.android.holderpick.screen.data.emq;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 分拣设备信息
 *
 * @author www
 * @date 2018/11/23 17:04.
 */

public class SortingDevice implements Serializable, Parcelable {

    /**
     * DeviceID ：设备ID
     * deviceCode : 设备序列号
     * deviceNumber : 屏显设备编码
     * customerID : 客户ID
     * customerName : 分拣匹配客户名称
     * sortingID : 分拣单ID
     */
    @SerializedName(value = "deviceID", alternate = {"DeviceID"})
    private String DeviceID;
    @SerializedName(value = "deviceCode", alternate = {"DeviceCode"})
    private String DeviceCode;
    @SerializedName(value = "deviceNumber", alternate = {"DeviceNumber"})
    private String DeviceNumber;
    @SerializedName(value = "customerID", alternate = {"CustomerID"})
    private int CustomerID;
    @SerializedName(value = "customerName", alternate = {"CustomerName"})
    private String CustomerName;
    @SerializedName(value = "sortingID", alternate = {"SortingID"})
    private int SortingID;

    public String getDeviceCode() {
        return DeviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        DeviceCode = deviceCode;
    }

    public String getDeviceNumber() {
        return DeviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        DeviceNumber = deviceNumber;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public int getSortingID() {
        return SortingID;
    }

    public void setSortingID(int sortingID) {
        SortingID = sortingID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.DeviceCode);
        dest.writeString(this.DeviceNumber);
        dest.writeInt(this.CustomerID);
        dest.writeString(this.CustomerName);
        dest.writeInt(this.SortingID);
    }

    public SortingDevice() {
    }

    protected SortingDevice(Parcel in) {
        this.DeviceCode = in.readString();
        this.DeviceNumber = in.readString();
        this.CustomerID = in.readInt();
        this.CustomerName = in.readString();
        this.SortingID = in.readInt();
    }

    public static final Parcelable.Creator<SortingDevice> CREATOR = new Parcelable.Creator<SortingDevice>() {
        @Override
        public SortingDevice createFromParcel(Parcel source) {
            return new SortingDevice(source);
        }

        @Override
        public SortingDevice[] newArray(int size) {
            return new SortingDevice[size];
        }
    };
}
