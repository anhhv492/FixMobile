package com.fix.mobile.dto;

import com.fix.mobile.entity.Account;
import com.fix.mobile.entity.OrderDetail;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class OrderDTO extends AbstractDTO<Integer> {
    private Integer idOrder;
    private Date createDate;
    private BigDecimal total;
    private String note;
    private Boolean status;
    private String address;
    private Boolean type;
    private Account account;
    private List<OrderDetail> orderDetails;

    public OrderDTO() {
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public Integer getIdOrder() {
        return this.idOrder;
    }

    public void setCreateDate(java.sql.Date createDate) {
        this.createDate = createDate;
    }

    public java.sql.Date getCreateDate() {
        return this.createDate;
    }

    public void setTotal(java.math.BigDecimal total) {
        this.total = total;
    }

    public java.math.BigDecimal getTotal() {
        return this.total;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return this.note;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Boolean getType() {
        return this.type;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return this.account;
    }


    public void setOrderDetailProducts(java.util.List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public java.util.List<OrderDetail> getOrderDetailProducts() {
        return this.orderDetails;
    }
}