package com.holderzone.android.holderpick.screen.data.eneity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Emq实体数据
 *
 * @author www
 * @date 2018/11/20 19:16.
 */

public class Emq implements Serializable, Parcelable {

    /**
     * ip : Emq的ip地址
     * port : 端口
     * accountNo : 帐号
     * password : 密码
     */

    private String ip;
    private int port;
    private String accountNo;
    private String password;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ip);
        dest.writeInt(this.port);
        dest.writeString(this.accountNo);
        dest.writeString(this.password);
    }

    public Emq() {
    }

    protected Emq(Parcel in) {
        this.ip = in.readString();
        this.port = in.readInt();
        this.accountNo = in.readString();
        this.password = in.readString();
    }

    public static final Creator<Emq> CREATOR = new Creator<Emq>() {
        @Override
        public Emq createFromParcel(Parcel source) {
            return new Emq(source);
        }

        @Override
        public Emq[] newArray(int size) {
            return new Emq[size];
        }
    };
}
