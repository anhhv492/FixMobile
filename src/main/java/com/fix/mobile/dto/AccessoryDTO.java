package com.fix.mobile.dto;

import com.fix.mobile.entity.OrderDetailAccessory;
import com.fix.mobile.entity.Category;
import com.fix.mobile.entity.OrderDetailAccessory;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class AccessoryDTO extends AbstractDTO<Integer> {
    private Integer idAccessory;
    private String name;
    private Integer quantity;
    private Date createDate;
    private String color;
    private BigDecimal price;
    private Boolean status;
    private String image;
    private String note;
    private Category category;
    private List<OrderDetailAccessory> orderDetailAccessories;

    public AccessoryDTO() {
    }

    public void setIdAccessory(Integer idAccessory) {
        this.idAccessory = idAccessory;
    }

    public Integer getIdAccessory() {
        return this.idAccessory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setCreateDate(java.sql.Date createDate) {
        this.createDate = createDate;
    }

    public java.sql.Date getCreateDate() {
        return this.createDate;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
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

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return this.image;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return this.note;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setOrderDetailAccessories(java.util.List<OrderDetailAccessory> orderDetailAccessories) {
        this.orderDetailAccessories = orderDetailAccessories;
    }

    public java.util.List<OrderDetailAccessory> getOrderDetailAccessories() {
        return this.orderDetailAccessories;
    }
}