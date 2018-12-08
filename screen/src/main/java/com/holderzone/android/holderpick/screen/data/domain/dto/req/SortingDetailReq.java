package com.holderzone.android.holderpick.screen.data.domain.dto.req;

import java.io.Serializable;

/**
 * 分拣详情
 *
 * @author www
 * @date 2018/11/24 20:20.
 */

public class SortingDetailReq implements Serializable {

    /**
     * sortingID : 分拣单ID
     * customerID : 客户ID
     * sortingMaterialPackageID : 分拣规格ID
     * oldQuantity : 分拣数量(修改前)
     * newQuantity : 0
     */

    private int sortingID;
    private int customerID;
    private int sortingMaterialPackageID;
    private int oldQuantity;
    private int newQuantity;

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

    public int getOldQuantity() {
        return oldQuantity;
    }

    public void setOldQuantity(int oldQuantity) {
        this.oldQuantity = oldQuantity;
    }

    public int getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(int newQuantity) {
        this.newQuantity = newQuantity;
    }
}
