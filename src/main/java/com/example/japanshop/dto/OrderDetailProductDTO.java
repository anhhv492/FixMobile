package com.example.japanshop.dto;

import com.example.japanshop.entity.*;

import java.math.BigDecimal;
import java.util.List;

public class OrderDetailProductDTO extends AbstractDTO<Integer> {
    private Integer idDetail;
    private Integer quantity;
    private BigDecimal price;
    private Boolean status;
    private Order order;
    private Product product;
    private List<ChangeDetail> changeDetails;
    private List<InsuranceProduct> insuranceProducts;
    private List<ProductReturn> productReturns;

    public OrderDetailProductDTO() {
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

    public void setChangeDetails(java.util.List<com.example.japanshop.entity.ChangeDetail> changeDetails) {
        this.changeDetails = changeDetails;
    }

    public java.util.List<com.example.japanshop.entity.ChangeDetail> getChangeDetails() {
        return this.changeDetails;
    }

    public void setInsuranceProducts(java.util.List<com.example.japanshop.entity.InsuranceProduct> insuranceProducts) {
        this.insuranceProducts = insuranceProducts;
    }

    public java.util.List<com.example.japanshop.entity.InsuranceProduct> getInsuranceProducts() {
        return this.insuranceProducts;
    }

    public void setProductReturns(java.util.List<com.example.japanshop.entity.ProductReturn> productReturns) {
        this.productReturns = productReturns;
    }

    public java.util.List<com.example.japanshop.entity.ProductReturn> getProductReturns() {
        return this.productReturns;
    }
}