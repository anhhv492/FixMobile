package com.example.japanshop.dto;

import com.example.japanshop.entity.Product;

import java.util.List;

public class ColorDTO extends AbstractDTO<Integer> {
    private Integer idColor;
    private String name;
    private List<Product> products;

    public ColorDTO() {
    }

    public void setIdColor(Integer idColor) {
        this.idColor = idColor;
    }

    public Integer getIdColor() {
        return this.idColor;
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