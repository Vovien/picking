package com.holderzone.android.holderpick.screen.data.eneity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * class description
 *
 * @author www
 * @date 2018/11/24 13:39.
 */

public class Enterprise implements Serializable, Parcelable {

    /**
     * enterpriseGUID : 企业ID
     * enterpriseName : 企业名称
     */

    private String enterpriseGUID;
    private String enterpriseName;

    public String getEnterpriseGUID() {
        return enterpriseGUID;
    }

    public void setEnterpriseGUID(String enterpriseGUID) {
        this.enterpriseGUID = enterpriseGUID;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.enterpriseGUID);
        dest.writeString(this.enterpriseName);
    }

    public Enterprise() {
    }

    protected Enterprise(Parcel in) {
        this.enterpriseGUID = in.readString();
        this.enterpriseName = in.readString();
    }

    public static final Parcelable.Creator<Enterprise> CREATOR = new Parcelable.Creator<Enterprise>() {
        @Override
        public Enterprise createFromParcel(Parcel source) {
            return new Enterprise(source);
        }

        @Override
        public Enterprise[] newArray(int size) {
            return new Enterprise[size];
        }
    };
}
