package com.fix.mobile.dto;

import com.fix.mobile.entity.OrderDetailAccessory;
import com.japan.shop.entity.Account;
import com.japan.shop.entity.OrderDetailAccessory;
import com.japan.shop.entity.OrderDetailProduct;

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
    private List<OrderDetailAccessory> orderDetailAccessories;
    private List<OrderDetailProduct> orderDetailProducts;

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

    public void setOrderDetailAccessories(java.util.List<OrderDetailAccessory> orderDetailAccessories) {
        this.orderDetailAccessories = orderDetailAccessories;
    }

    public java.util.List<OrderDetailAccessory> getOrderDetailAccessories() {
        return this.orderDetailAccessories;
    }

    public void setOrderDetailProducts(java.util.List<OrderDetailProduct> orderDetailProducts) {
        this.orderDetailProducts = orderDetailProducts;
    }

    public java.util.List<OrderDetailProduct> getOrderDetailProducts() {
        return this.orderDetailProducts;
    }
}