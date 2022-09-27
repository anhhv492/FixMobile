package com.japan.shop.dto;

import com.japan.shop.entity.Product;

import java.util.List;

public class CategoryDTO extends AbstractDTO<Integer> {
    private Integer idCategory;
    private Boolean type;
    private String name;
    private List<Product> products;

    public CategoryDTO() {
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }

    public Integer getIdCategory() {
        return this.idCategory;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Boolean getType() {
        return this.type;
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