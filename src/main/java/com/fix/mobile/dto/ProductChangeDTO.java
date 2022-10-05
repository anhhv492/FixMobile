package com.fix.mobile.dto;

import com.fix.mobile.entity.Account;
import com.fix.mobile.entity.ChangeDetail;

import java.math.BigDecimal;
import java.util.List;

public class ProductChangeDTO extends AbstractDTO<Integer> {
    private Integer idChange;
    private Integer imei;
    private Integer dataChange;
    private String note;
    private BigDecimal price;
    private Boolean status;
    private Account account;
    private List<ChangeDetail> changeDetails;

    public ProductChangeDTO() {
    }

    public void setIdChange(Integer idChange) {
        this.idChange = idChange;
    }

    public Integer getIdChange() {
        return this.idChange;
    }

    public void setImei(Integer imei) {
        this.imei = imei;
    }

    public Integer getImei() {
        return this.imei;
    }

    public void setDataChange(Integer dataChange) {
        this.dataChange = dataChange;
    }

    public Integer getDataChange() {
        return this.dataChange;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return this.note;
    }

    public void setPrice(java.math.BigDecimal price) {
        this.price = price;
    }

    public java.math.BigDecimal getPrice() {
        return this.price;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setChangeDetails(java.util.List<ChangeDetail> changeDetails) {
        this.changeDetails = changeDetails;
    }

    public java.util.List<ChangeDetail> getChangeDetails() {
        return this.changeDetails;
    }
}