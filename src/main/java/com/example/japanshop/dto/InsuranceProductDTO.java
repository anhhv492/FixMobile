package com.example.japanshop.dto;

import com.example.japanshop.entity.OrderDetailProduct;

import java.sql.Date;

public class InsuranceProductDTO extends AbstractDTO<Integer> {
    private Integer idInsurance;
    private String note;
    private Date createStart;
    private Date createEnd;
    private Boolean status;
    private OrderDetailProduct orderDetailProduct;

    public InsuranceProductDTO() {
    }

    public void setIdInsurance(Integer idInsurance) {
        this.idInsurance = idInsurance;
    }

    public Integer getIdInsurance() {
        return this.idInsurance;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return this.note;
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

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setOrderDetailProduct(OrderDetailProduct orderDetailProduct) {
        this.orderDetailProduct = orderDetailProduct;
    }

    public OrderDetailProduct getOrderDetailProduct() {
        return this.orderDetailProduct;
    }
}