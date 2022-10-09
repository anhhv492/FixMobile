package com.fix.mobile.dto;

import com.fix.mobile.entity.*;

import java.math.BigDecimal;
import java.util.List;

public class OrderDetailDTO extends AbstractDTO<Integer> {
    private Integer idDetail;
    private Integer quantity;
    private BigDecimal price;
    private Boolean status;
    private Order order;
    private Product product;
    private List<ChangeDetail> changeDetails;
    private List<ProductReturn> productReturns;

    public OrderDetailDTO() {
    }

    public void setIdDetail(Integer idDetail) {
        this.idDetail = idDetail;
    }

    public Integer getIdDetail() {
        return this.idDetail;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return this.quantity;
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

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setChangeDetails(java.util.List<ChangeDetail> changeDetails) {
        this.changeDetails = changeDetails;
    }

    public java.util.List<ChangeDetail> getChangeDetails() {
        return this.changeDetails;
    }

    public void setProductReturns(java.util.List<ProductReturn> productReturns) {
        this.productReturns = productReturns;
    }

    public java.util.List<ProductReturn> getProductReturns() {
        return this.productReturns;
    }
}