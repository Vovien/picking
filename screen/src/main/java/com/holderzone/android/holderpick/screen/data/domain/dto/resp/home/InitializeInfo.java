package com.holderzone.android.holderpick.screen.data.domain.dto.resp.home;

import android.os.Parcel;
import android.os.Parcelable;

import com.holderzone.android.holderpick.screen.data.emq.SortingDevice;
import com.holderzone.android.holderpick.screen.data.eneity.Emq;
import com.holderzone.android.holderpick.screen.data.eneity.Enterprise;

import java.io.Serializable;

/**
 * 初始化信息
 *
 * @author www
 * @date 2018/11/20 19:34.
 */

public class InitializeInfo implements Serializable, Parcelable {
    private Emq emq;
    private Enterprise enterprise;
    private String token;
    private SortingDevice sortingDeviceDTO;

    public SortingDevice getSortingDeviceDTO() {
        return sortingDeviceDTO;
    }

    public void setSortingDeviceDTO(SortingDevice sortingDeviceDTO) {
        this.sortingDeviceDTO = sortingDeviceDTO;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Emq getEmq() {
        return emq;
    }

    public void setEmq(Emq emq) {
        this.emq = emq;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public InitializeInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.emq, flags);
        dest.writeParcelable(this.enterprise, flags);
        dest.writeString(this.token);
        dest.writeParcelable(this.sortingDeviceDTO, flags);
    }

    protected InitializeInfo(Parcel in) {
        this.emq = in.readParcelable(Emq.class.getClassLoader());
        this.enterprise = in.readParcelable(Enterprise.class.getClassLoader());
        this.token = in.readString();
        this.sortingDeviceDTO = in.readParcelable(SortingDevice.class.getClassLoader());
    }

    public static final Creator<InitializeInfo> CREATOR = new Creator<InitializeInfo>() {
        @Override
        public InitializeInfo createFromParcel(Parcel source) {
            return new InitializeInfo(source);
        }

        @Override
        public InitializeInfo[] newArray(int size) {
            return new InitializeInfo[size];
        }
    };
}
