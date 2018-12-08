package com.holderzone.android.holderpick.screen.data.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 企业信息表
 *
 * @author www
 * @date 2018/11/27 13:09.
 */
@Entity(nameInDb = "EnterpriseInfo")
public class EnterpriseInfo {

    /**
     * enterpriseGUID : 企业ID
     * enterpriseName : 企业名称
     * token
     */
    @Property(nameInDb = "enterpriseGUID")
    private String enterpriseGUID;
    @Property(nameInDb = "enterpriseName")
    private String enterpriseName;
    @Property(nameInDb = "token")
    private String token;
    @Generated(hash = 1980138127)
    public EnterpriseInfo(String enterpriseGUID, String enterpriseName,
            String token) {
        this.enterpriseGUID = enterpriseGUID;
        this.enterpriseName = enterpriseName;
        this.token = token;
    }
    @Generated(hash = 928631037)
    public EnterpriseInfo() {
    }
    public String getEnterpriseGUID() {
        return this.enterpriseGUID;
    }
    public void setEnterpriseGUID(String enterpriseGUID) {
        this.enterpriseGUID = enterpriseGUID;
    }
    public String getEnterpriseName() {
        return this.enterpriseName;
    }
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
