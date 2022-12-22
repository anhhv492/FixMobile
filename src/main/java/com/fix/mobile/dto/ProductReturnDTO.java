package com.fix.mobile.dto;

import com.fix.mobile.entity.OrderDetail;
import com.fix.mobile.entity.Product;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class ProductReturnDTO extends AbstractDTO<Integer> {
    private Integer idReturn;
    private Date dateReturn;
    private BigDecimal price;
    private String note;
    private OrderDetail orderDetail;
    private Product product;

    public ProductReturnDTO() {
    }

    public Integer getIdReturn() {
        return idReturn;
    }

    public void setIdReturn(Integer idReturn) {
        this.idReturn = idReturn;
    }

    public Date getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(Date dateReturn) {
        this.dateReturn = dateReturn;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}