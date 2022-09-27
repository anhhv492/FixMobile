package com.example.japanshop.dto;

import com.example.japanshop.entity.Accessory;
import com.example.japanshop.entity.Order;

import java.math.BigDecimal;

public class OrderDetailAccessoryDTO extends AbstractDTO<Integer> {
    private Integer idDetail;
    private Integer quantity;
    private BigDecimal price;
    private Boolean status;
    private Order order;
    private Accessory accessory;

    public OrderDetailAccessoryDTO() {
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

    public void setAccessory(Accessory accessory) {
        this.accessory = accessory;
    }

    public Accessory getAccessory() {
        return this.accessory;
    }
}