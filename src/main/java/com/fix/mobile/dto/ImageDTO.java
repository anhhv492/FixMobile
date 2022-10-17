package com.fix.mobile.dto;

import com.fix.mobile.entity.Product;

import java.util.List;

public class ImageDTO extends AbstractDTO<Integer> {
    private Integer idImage;
    private String name;
    private List<Product> products;

    public ImageDTO() {
    }

    public void setIdImage(Integer idImage) {
        this.idImage = idImage;
    }

    public Integer getIdImage() {
        return this.idImage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}