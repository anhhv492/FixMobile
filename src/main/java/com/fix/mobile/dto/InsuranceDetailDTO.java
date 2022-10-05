package com.fix.mobile.dto;

import com.japan.shop.entity.Account;
import com.japan.shop.entity.Insurance;
import com.japan.shop.entity.Product;

public class InsuranceDetailDTO extends AbstractDTO<Integer> {
    private Integer idDetail;
    private Insurance insurance;
    private Product product;
    private Account account;

    public InsuranceDetailDTO() {
    }

    public void setIdDetail(Integer idDetail) {
        this.idDetail = idDetail;
    }

    public Integer getIdDetail() {
        return this.idDetail;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public Insurance getInsurance() {
        return this.insurance;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return this.account;
    }
}