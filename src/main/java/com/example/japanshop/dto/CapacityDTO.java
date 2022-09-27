package com.example.japanshop.dto;

import com.example.japanshop.entity.Product;

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

    public void setProducts(java.util.List<com.example.japanshop.entity.Product> products) {
        this.products = products;
    }

    public java.util.List<com.example.japanshop.entity.Product> getProducts() {
        return this.products;
    }
}