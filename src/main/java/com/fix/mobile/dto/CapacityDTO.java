package com.fix.mobile.dto;

import com.fix.mobile.entity.Product;
import com.fix.mobile.entity.Product;

import java.util.List;

public class CapacityDTO extends AbstractDTO<Integer> {
    private Integer idCapacity;
    private String name;
    private List<Product> products;

    public CapacityDTO() {
    }

    public void setIdCapacity(Integer idCapacity) {
        this.idCapacity = idCapacity;
    }

    public Integer getIdCapacity() {
        return this.idCapacity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setProducts(java.util.List<Product> products) {
        this.products = products;
    }

    public java.util.List<Product> getProducts() {
        return this.products;
    }
}