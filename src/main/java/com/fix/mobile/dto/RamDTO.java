package com.fix.mobile.dto;

import com.fix.mobile.entity.Product;
import com.fix.mobile.entity.Product;

import java.util.List;

public class RamDTO extends AbstractDTO<Integer> {
    private Integer idRam;
    private String name;
    private List<Product> products;

    public RamDTO() {
    }

    public void setIdRam(Integer idRam) {
        this.idRam = idRam;
    }

    public Integer getIdRam() {
        return this.idRam;
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