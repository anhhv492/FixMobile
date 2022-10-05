package com.fix.mobile.dto;

import com.fix.mobile.entity.SaleDetail;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class SaleDTO extends AbstractDTO<Integer> {
    private Integer idSale;
    private String name;
    private String typeSale;
    private Date createStart;
    private Date createEnd;
    private String voucher;
    private BigDecimal valueMin;
    private BigDecimal moneySale;
    private Integer quantityUse;
    private Boolean status;
    private List<SaleDetail> saleDetails;

    public SaleDTO() {
    }

    public void setIdSale(Integer idSale) {
        this.idSale = idSale;
    }

    public Integer getIdSale() {
        return this.idSale;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setTypeSale(String typeSale) {
        this.typeSale = typeSale;
    }

    public String getTypeSale() {
        return this.typeSale;
    }

    public void setCreateStart(java.sql.Date createStart) {
        this.createStart = createStart;
    }

    public java.sql.Date getCreateStart() {
        return this.createStart;
    }

    public void setCreateEnd(java.sql.Date createEnd) {
        this.createEnd = createEnd;
    }

    public java.sql.Date getCreateEnd() {
        return this.createEnd;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public String getVoucher() {
        return this.voucher;
    }

    public void setValueMin(java.math.BigDecimal valueMin) {
        this.valueMin = valueMin;
    }

    public java.math.BigDecimal getValueMin() {
        return this.valueMin;
    }

    public void setMoneySale(java.math.BigDecimal moneySale) {
        this.moneySale = moneySale;
    }

    public java.math.BigDecimal getMoneySale() {
        return this.moneySale;
    }

    public void setQuantityUse(Integer quantityUse) {
        this.quantityUse = quantityUse;
    }

    public Integer getQuantityUse() {
        return this.quantityUse;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setSaleDetails(java.util.List<SaleDetail> saleDetails) {
        this.saleDetails = saleDetails;
    }

    public java.util.List<SaleDetail> getSaleDetails() {
        return this.saleDetails;
    }
}